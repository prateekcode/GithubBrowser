package com.prateekcode.githubbrowser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prateekcode.githubbrowser.repository.Repository

class ApiViewModelFactory:ViewModelProvider.Factory {
    private val repository: Repository = Repository()
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ApiViewModel(repository) as T
    }
}