package com.prateekcode.githubbrowser.model.commit

data class CommitItem(
    val author: Author,
    val comments_url: String,
    val commit: CommitX,
    val committer: CommitterX,
    val html_url: String,
    val node_id: String,
    val parents: List<Any>,
    val sha: String,
    val url: String
)