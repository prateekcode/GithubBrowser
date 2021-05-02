package com.prateekcode.githubbrowser.model.commit

data class Verification(
    val payload: Any,
    val reason: String,
    val signature: Any,
    val verified: Boolean
)