package com.prateekcode.githubbrowser.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.prateekcode.githubbrowser.R
import com.prateekcode.githubbrowser.adapter.CommitAdapter
import com.prateekcode.githubbrowser.databinding.FragmentCommitBinding
import com.prateekcode.githubbrowser.db.RepoDatabase
import com.prateekcode.githubbrowser.db.Repodao
import com.prateekcode.githubbrowser.util.Utils
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

        if (Utils.isConnected(context!!)){
            //Initializing the database
            commitAdapter = CommitAdapter()
            repodao = RepoDatabase.getDatabase(context!!).repoDao()
            binding.commitRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.commitRecyclerView.adapter = commitAdapter
            binding.commitProgressBar.visibility = View.VISIBLE
            val factory = ApiViewModelFactory(repodao)
            viewModel = ViewModelProvider(this, factory).get(ApiViewModel::class.java)
            viewModel!!.getCommitMessage(
                ownerName, repoName, shaKey
            )
            viewModel!!.commitMessageResponse.observe(viewLifecycleOwner, { response ->
                if (response.isSuccessful) {
                    binding.commitProgressBar.visibility = View.GONE
                    commitAdapter.setData(response.body()!!)
                }
            })
            binding.commitMaterialToolbar.subtitle = branchName
        }else{
            Snackbar.make(activity!!.findViewById(android.R.id.content), "You're not connected to Internet", Snackbar.LENGTH_INDEFINITE)
                .setAction("Setting"){
                    startActivity(Intent(Settings.ACTION_SETTINGS))
                }
                .show()
        }


        return binding.root
    }

}