package com.prateekcode.githubbrowser.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.prateekcode.githubbrowser.R
import com.prateekcode.githubbrowser.databinding.FragmentAddRepoBinding
import com.prateekcode.githubbrowser.viewmodel.ApiViewModel
import com.prateekcode.githubbrowser.viewmodel.ApiViewModelFactory


class AddRepoFragment : Fragment() {

    lateinit var binding: FragmentAddRepoBinding
    var viewModel: ApiViewModel? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_repo, container, false)

        binding.addMaterialToolbar.setNavigationOnClickListener {
            fragmentManager!!.popBackStack()
        }

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

        return binding.root
    }

    private fun isEmptyTextInput(editText: EditText): Boolean {
        return editText.text.toString().trim().isEmpty()
    }

    private fun findTheRepo(userName:String, repoName:String){
        val factory = ApiViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(ApiViewModel::class.java)
        viewModel!!.githubRepository(userName, repoName)
        viewModel!!.repoResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                Log.d(TAG, "Name of the user: ${response.body()!!.name}")
                Log.d(TAG, "Description of the user: ${response.body()!!.description}")
                Log.d(TAG, "Html Url of the user: ${response.body()!!.html_url}")
            }
        })
    }

    companion object{
        const val TAG = "REPO_FRAGMENT"
    }
}