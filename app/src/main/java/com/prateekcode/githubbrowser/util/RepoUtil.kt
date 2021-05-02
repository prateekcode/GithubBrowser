package com.prateekcode.githubbrowser.util

import androidx.recyclerview.widget.DiffUtil
import com.prateekcode.githubbrowser.db.Repotity

class RepoUtil(private val oldList: List<Repotity>, private val newList: List<Repotity>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].repositoryName == newList[newItemPosition].repositoryName
                && oldList[oldItemPosition].descriptionRepo == newList[newItemPosition].descriptionRepo
                && oldList[oldItemPosition].htmlUrl == newList[newItemPosition].htmlUrl
                && oldList[oldItemPosition].ownerName == newList[newItemPosition].ownerName
    }
}