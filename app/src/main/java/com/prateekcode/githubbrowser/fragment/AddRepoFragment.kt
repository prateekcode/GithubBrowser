package com.prateekcode.githubbrowser.fragment


import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.prateekcode.githubbrowser.R
import com.prateekcode.githubbrowser.databinding.FragmentAddRepoBinding
import com.prateekcode.githubbrowser.db.RepoDatabase
import com.prateekcode.githubbrowser.db.Repodao
import com.prateekcode.githubbrowser.db.Repotity
import com.prateekcode.githubbrowser.util.Utils
import com.prateekcode.githubbrowser.viewmodel.ApiViewModel
import com.prateekcode.githubbrowser.viewmodel.ApiViewModelFactory


class AddRepoFragment : Fragment() {

    lateinit var binding: FragmentAddRepoBinding
    var viewModel: ApiViewModel? =null
    lateinit var repodao: Repodao


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_repo, container, false)

        //Initializing the database
        repodao = RepoDatabase.getDatabase(context!!).repoDao()

        binding.addMaterialToolbar.setNavigationOnClickListener {
            fragmentManager!!.popBackStack()
        }

        if (Utils.isConnected(context!!)){
            binding.addRepositoryBtn.setOnClickListener {
                if (isEmptyTextInput(binding.ownerEditText) || isEmptyTextInput(binding.repoEditText)){
                    binding.ownerEditText.error ="Enter Username/Organization"
                    binding.repoEditText.error = "Enter Repo Name"
                    Toast.makeText(context, "Enter correct details", Toast.LENGTH_SHORT).show()
                }else{
                    //Toast.makeText(context, "Repo added", Toast.LENGTH_SHORT).show()
                    findTheRepo(binding.ownerEditText.text.toString(), binding.repoEditText.text.toString())
                }
            }
        }else{
            Snackbar.make(activity!!.findViewById(android.R.id.content), "You're not connected to Internet", Snackbar.LENGTH_INDEFINITE)
                .setAction("Setting"){
                    startActivity(Intent(Settings.ACTION_SETTINGS))
                }
                .show()
        }

        return binding.root
    }

    private fun isEmptyTextInput(editText: EditText): Boolean {
        return editText.text.toString().trim().isEmpty()
    }

    private fun findTheRepo(userName:String, repoName:String){
        val factory = ApiViewModelFactory(repodao)
        viewModel = ViewModelProvider(this, factory).get(ApiViewModel::class.java)
        viewModel!!.githubRepository(userName, repoName)
        viewModel!!.repoResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                if (response.body() != null) {
                    Log.d(TAG, "Name of the user: ${response.body()!!.name}")
                    val repoName = response.body()!!.name
                    var descriptionOfRepo = response.body()!!.description
                    if (descriptionOfRepo == null) {
                        descriptionOfRepo = "Not Found"
                    } else {
                        descriptionOfRepo
                    }
                    val htmlUrl = response.body()!!.html_url
                    Log.d(TAG, "Description of the user: ${response.body()!!.description}")
                    Log.d(TAG, "Html Url of the user: ${response.body()!!.html_url}")
                    val repo = Repotity(repoName, descriptionOfRepo, htmlUrl, userName)
                    viewModel!!.insertTheRepo(repo)
                    fragmentManager!!.popBackStack()
                } else {
                    binding.ownerEditText.text.clear()
                    binding.repoEditText.text.clear()
                    binding.ownerEditText.error = "Enter Correct Username/Organization"
                    binding.repoEditText.error = "Enter Correct Repo Name"
                    Toast.makeText(context, "Owner/Repo not found", Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.ownerEditText.text.clear()
                binding.repoEditText.text.clear()
                binding.ownerEditText.error = "Enter Correct Username/Organization"
                binding.repoEditText.error = "Enter Correct Repo Name"
                Toast.makeText(context, "Owner/Repo not found", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "findTheRepo: Getting the error message ${response.message()}")
                Log.d(TAG, "findTheRepo: Getting the error message ${response.raw()}")
                Log.d(TAG, "findTheRepo: Getting the error message ${response.errorBody()}")
                Log.d(TAG, "findTheRepo: Getting the error message ${response.headers()}")
            }
        })
    }

    companion object{
        const val TAG = "REPO_FRAGMENT"
    }
}