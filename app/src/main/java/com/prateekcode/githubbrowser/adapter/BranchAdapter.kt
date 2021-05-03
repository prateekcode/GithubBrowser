package com.prateekcode.githubbrowser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.prateekcode.githubbrowser.model.branch.BranchItem
import com.prateekcode.githubbrowser.util.BranchUtil

class BranchAdapter(private val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<BranchAdapter.BranchViewHolder>() {
    var branch = emptyList<BranchItem>()

    inner class BranchViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        init {
            itemView.setOnClickListener {
                onItemClickListener.onClick(adapterPosition)
            }
        }

        fun bind(branch: BranchItem){
            itemView.findViewById<TextView>(android.R.id.text1).text = branch.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchAdapter.BranchViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            android.R.layout.simple_list_item_1,
            parent,
            false
        )
        return BranchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BranchAdapter.BranchViewHolder, position: Int) {
        val branch = branch[position]
        holder.bind(branch)
    }

    override fun getItemCount(): Int= branch.size

    fun setData(branchList: List<BranchItem>){
        val branchDiffUtil = BranchUtil(branch, branchList)
        val branchDiffResult = DiffUtil.calculateDiff(branchDiffUtil)
        this.branch = branchList
        branchDiffResult.dispatchUpdatesTo(this)
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

}