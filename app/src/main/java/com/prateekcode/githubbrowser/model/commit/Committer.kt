package com.prateekcode.githubbrowser.model.commit

data class Committer(
    val date: String,
    val email: String,
    var name: String
)