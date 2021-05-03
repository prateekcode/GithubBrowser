package com.prateekcode.githubbrowser.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.prateekcode.githubbrowser.R
import com.prateekcode.githubbrowser.adapter.BranchAdapter
import com.prateekcode.githubbrowser.databinding.FragmentDetailBinding
import com.prateekcode.githubbrowser.db.RepoDatabase
import com.prateekcode.githubbrowser.db.Repodao
import com.prateekcode.githubbrowser.db.Repotity
import com.prateekcode.githubbrowser.model.branch.CustomBranch
import com.prateekcode.githubbrowser.util.Utils
import com.prateekcode.githubbrowser.viewmodel.ApiViewModel
import com.prateekcode.githubbrowser.viewmodel.ApiViewModelFactory


class DetailFragment(repoName: String, description: String, htmUrl: String, ownerId: String) :
    Fragment(), BranchAdapter.OnItemClickListener {

    lateinit var binding: FragmentDetailBinding
    val repositoryName = repoName
    val description = description
    val htmlUrl = htmUrl
    val ownerId = ownerId
    var viewModel: ApiViewModel? = null
    lateinit var repodao: Repodao
    val branchAdapter: BranchAdapter by lazy { BranchAdapter(this) }

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
        if (Utils.isConnected(context!!)){
            binding.branchList.adapter = branchAdapter
            binding.branchList.layoutManager = LinearLayoutManager(context)

            val factory = ApiViewModelFactory(repodao)
            viewModel = ViewModelProvider(this, factory).get(ApiViewModel::class.java)
            viewModel!!.getBranches(ownerId, repositoryName)
            viewModel!!.branchResponse.observe(viewLifecycleOwner, { response ->
                if (response.isSuccessful) {
                    Log.d(TAG, "BRANCH LIST ${response.body()}")
                    branchAdapter.setData(response.body()!!)
                }
            })


            issueCounter()
            binding.detailMaterialToolbar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.delete_repo_btn -> {
                        MaterialAlertDialogBuilder(context!!)
                            .setTitle("Are you sure?")
                            .setNegativeButton("Cancel") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .setPositiveButton("Delete") { _, _ ->
                                viewModel!!.deleteTheRepo(
                                    Repotity(
                                        repositoryName,
                                        description,
                                        htmlUrl,
                                        ownerId
                                    )
                                )
                                Toast.makeText(context, "File delete successfully", Toast.LENGTH_SHORT)
                                    .show()
                                fragmentManager!!.popBackStack()
                            }
                            .show()
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
        }else{
            Snackbar.make(activity!!.findViewById(android.R.id.content), "You're not connected to Internet", Snackbar.LENGTH_INDEFINITE)
                .setAction("Setting"){
                    startActivity(Intent(Settings.ACTION_SETTINGS))
                }
                .show()
        }


        return binding.root
    }

    override fun onClick(position: Int) {
        viewModel!!.getBranches(ownerId, repositoryName)
        viewModel!!.branchResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                Log.d(TAG, "BRANCH LIST ${response.body()}")
                replaceFragment(CommitFragment(
                    response.body()!![position].name,
                    ownerId,
                    repositoryName,
                    response.body()!![position].commit.sha
                ))
            }
        })
    }

    private fun issueCounter() {
//        val factory = ApiViewModelFactory(repodao)
//        viewModel = ViewModelProvider(this, factory).get(ApiViewModel::class.java)
        viewModel!!.getOpenIssue(ownerId, repositoryName, "open")
        viewModel!!.openIssueResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                if (response.body()!!.isNotEmpty()) {
                    Log.d(TAG, "issueCounter: ${response.body()}")
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

    companion object {
        const val TAG = "DETAIL_FRAGMENT"
        const val SETTINGS_PACKAGE = "com.android.settings"
    }


}