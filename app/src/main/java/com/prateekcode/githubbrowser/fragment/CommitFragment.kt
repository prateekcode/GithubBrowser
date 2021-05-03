package com.prateekcode.githubbrowser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.prateekcode.githubbrowser.R
import com.prateekcode.githubbrowser.adapter.CommitAdapter
import com.prateekcode.githubbrowser.databinding.FragmentCommitBinding
import com.prateekcode.githubbrowser.db.RepoDatabase
import com.prateekcode.githubbrowser.db.Repodao
import com.prateekcode.githubbrowser.viewmodel.ApiViewModel
import com.prateekcode.githubbrowser.viewmodel.ApiViewModelFactory

class CommitFragment(branchName: String, ownerName: String, repoName: String, shaKey: String) :
    Fragment() {

    lateinit var binding: FragmentCommitBinding
    private lateinit var commitAdapter: CommitAdapter
    var viewModel: ApiViewModel? = null
    lateinit var repodao: Repodao
    val branchName = branchName
    val ownerName = ownerName
    val repoName = repoName
    val shaKey = shaKey

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_commit, container, false)

        binding.commitMaterialToolbar.setNavigationOnClickListener {
            fragmentManager!!.popBackStack()
        }

        //Initializing the database
        commitAdapter = CommitAdapter()
        repodao = RepoDatabase.getDatabase(context!!).repoDao()
        binding.commitRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.commitRecyclerView.adapter = commitAdapter
        val factory = ApiViewModelFactory(repodao)
        viewModel = ViewModelProvider(this, factory).get(ApiViewModel::class.java)
        viewModel!!.getCommitMessage(
            ownerName, repoName, shaKey
        )
        viewModel!!.commitMessageResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                commitAdapter.setData(response.body()!!)
            }
        })
        binding.commitMaterialToolbar.subtitle = branchName

        return binding.root
    }

}