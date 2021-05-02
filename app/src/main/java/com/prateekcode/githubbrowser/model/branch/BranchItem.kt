package com.prateekcode.githubbrowser.model.branch

data class BranchItem(
    val commit: Commit,
    val name: String,
    val `protected`: Boolean
)