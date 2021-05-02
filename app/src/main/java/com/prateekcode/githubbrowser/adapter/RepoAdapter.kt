package com.prateekcode.githubbrowser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.prateekcode.githubbrowser.R
import com.prateekcode.githubbrowser.db.Repotity
import com.prateekcode.githubbrowser.util.RepoUtil
import kotlinx.android.synthetic.main.single_repo_item.view.*

class RepoAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    var repoList = emptyList<Repotity>()

    inner class RepoViewHolder(itemView: View, onItemClickListener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onItemClickListener.onClick(adapterPosition)
            }
            itemView.share_btn.setOnClickListener {
                onItemClickListener.onShareButtonClick(adapterPosition)
            }
        }

        fun bind(repotity: Repotity) {
            itemView.repo_title.text = repotity.repositoryName
            itemView.repo_description.text = repotity.descriptionRepo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoAdapter.RepoViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.single_repo_item, parent, false)
        return RepoViewHolder(itemView, onItemClickListener)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repoList[position]
        holder.bind(repo)
    }

    override fun getItemCount(): Int = repoList.size

    fun setData(repotity: List<Repotity>) {
        val repoDiffUtil = RepoUtil(repoList, repotity)
        val repoUtilResult = DiffUtil.calculateDiff(repoDiffUtil)
        this.repoList = repotity
        repoUtilResult.dispatchUpdatesTo(this)
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
        fun onShareButtonClick(position: Int)
    }

}