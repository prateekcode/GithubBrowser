package com.prateekcode.githubbrowser.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.prateekcode.githubbrowser.R
import com.prateekcode.githubbrowser.databinding.FragmentDetailBinding
import com.prateekcode.githubbrowser.db.RepoDatabase
import com.prateekcode.githubbrowser.db.Repodao
import com.prateekcode.githubbrowser.viewmodel.ApiViewModel
import com.prateekcode.githubbrowser.viewmodel.ApiViewModelFactory


class DetailFragment(repoName: String, description: String, htmUrl: String, ownerId: String) : Fragment() {

    lateinit var binding: FragmentDetailBinding
    val repositoryName = repoName
    val description = description
    val htmlUrl = htmUrl
    val ownerId = ownerId
    var viewModel: ApiViewModel? =null
    lateinit var repodao: Repodao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.detailTitle.text = repositoryName
        binding.detailDescription.text = description

        //Initializing the database
        repodao = RepoDatabase.getDatabase(context!!).repoDao()

        binding.detailMaterialToolbar.setNavigationOnClickListener {
            fragmentManager!!.popBackStack()
        }

        binding.branchesIv.setOnClickListener {
            replaceFragment(CommitFragment("master"))
        }

        issueCounter()
        binding.detailMaterialToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.delete_repo_btn -> {
                    replaceFragment(CommitFragment("master"))
                    true
                }
                R.id.open_repo_btn -> {
                    try {
                        val uri: Uri = Uri.parse("googlechrome://navigate?url=$htmlUrl")
                        val i = Intent(Intent.ACTION_VIEW, uri)
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(i)
                    } catch (e: ActivityNotFoundException) {
                        Log.d("TAG", "onCreate: $e")
                    }
                    true
                }
                else -> false
            }
        }

        return binding.root
    }

    private fun issueCounter(){
        val factory = ApiViewModelFactory(repodao)
        viewModel = ViewModelProvider(this, factory).get(ApiViewModel::class.java)
        viewModel!!.getOpenIssue(ownerId, repositoryName, "open")
        viewModel!!.openIssueResponse.observe(viewLifecycleOwner, {response->
            if (response.isSuccessful){
                if (response.body()!!.isNotEmpty()){
                    binding.issueTv.text = "ISSUES(${response!!.body()!!.size})"
                }
            }
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}