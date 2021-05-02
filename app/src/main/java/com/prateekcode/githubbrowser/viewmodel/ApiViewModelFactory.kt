package com.prateekcode.githubbrowser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prateekcode.githubbrowser.db.Repodao
import com.prateekcode.githubbrowser.repository.Repository

class ApiViewModelFactory(repodao: Repodao):ViewModelProvider.Factory {
    private val repository: Repository = Repository(repodao)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ApiViewModel(repository) as T
    }
}