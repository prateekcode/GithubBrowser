package com.prateekcode.githubbrowser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.prateekcode.githubbrowser.R
import com.prateekcode.githubbrowser.model.commit.CommitItem
import com.prateekcode.githubbrowser.util.CommitUtil
import com.prateekcode.githubbrowser.util.Utils
import kotlinx.android.synthetic.main.commit_single_item.view.*
import java.lang.NullPointerException

class CommitAdapter : RecyclerView.Adapter<CommitAdapter.CommitViewHolder>() {

    var commitList = emptyList<CommitItem>()

    inner class CommitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(commitItem: CommitItem) {
            itemView.date_text_tv.text =
                Utils.getFormattedDateFromTimestamp(Utils.getDateToMilliSeconds(commitItem.commit.committer.date))
            itemView.commit_message_tv.text = commitItem.commit.message
            if (commitItem.commit.committer.name.isNotEmpty()) {
                itemView.user_name_tv.text = commitItem.commit.committer.name
            } else {
                itemView.user_name_tv.text = "Not Available"
            }
            try {
                itemView.avatar_iv.load(commitItem.committer.avatar_url)
            }catch (e: NullPointerException){
                itemView.avatar_iv.load(R.drawable.ic_avatar)
            }
            itemView.sha_key_initial.text = Utils.getSixLetterString(commitItem.sha)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.commit_single_item,
            parent,
            false
        )
        return CommitViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
        val commit = commitList[position]
        holder.bind(commit)
    }

    override fun getItemCount(): Int = commitList.size

    fun setData(commitItem: List<CommitItem>) {
        val commitDiffUtil = CommitUtil(commitList, commitItem)
        val commitDiffResult = DiffUtil.calculateDiff(commitDiffUtil)
        this.commitList = commitItem
        commitDiffResult.dispatchUpdatesTo(this)
    }

}