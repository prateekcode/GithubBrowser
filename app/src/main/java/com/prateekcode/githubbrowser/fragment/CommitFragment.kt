package com.prateekcode.githubbrowser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.prateekcode.githubbrowser.R
import com.prateekcode.githubbrowser.databinding.FragmentCommitBinding

class CommitFragment(branchName:String) : Fragment() {

    lateinit var binding: FragmentCommitBinding
    val branchName = branchName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_commit, container, false)

        binding.commitMaterialToolbar.setNavigationOnClickListener {
            fragmentManager!!.popBackStack()
        }

        binding.commitMaterialToolbar.subtitle = branchName

        return binding.root
    }

}