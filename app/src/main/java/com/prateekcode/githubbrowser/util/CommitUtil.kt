package com.prateekcode.githubbrowser.util

import androidx.recyclerview.widget.DiffUtil
import com.prateekcode.githubbrowser.model.commit.CommitItem

class CommitUtil(private val oldList: List<CommitItem>, private val newList: List<CommitItem>):DiffUtil.Callback() {
    override fun getOldListSize(): Int =oldList.size

    override fun getNewListSize(): Int =newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return oldList[oldItemPosition].commit.committer.date == newList[newItemPosition].commit.committer.date
               && oldList[oldItemPosition].commit.message == newList[newItemPosition].commit.message
               && oldList[oldItemPosition].commit.committer.name == newList[newItemPosition].commit.committer.name
               && oldList[oldItemPosition].committer.avatar_url == newList[newItemPosition].committer.avatar_url

    }
}