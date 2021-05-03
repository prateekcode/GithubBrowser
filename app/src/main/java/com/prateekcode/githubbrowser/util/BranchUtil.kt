package com.prateekcode.githubbrowser.util

import androidx.recyclerview.widget.DiffUtil
import com.prateekcode.githubbrowser.model.branch.BranchItem

class BranchUtil(private val oldList: List<BranchItem>, private val newList: List<BranchItem>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
                && oldList[oldItemPosition].commit.sha == newList[newItemPosition].commit.sha
    }
}