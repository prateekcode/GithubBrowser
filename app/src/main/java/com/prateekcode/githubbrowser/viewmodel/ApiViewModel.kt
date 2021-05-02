package com.prateekcode.githubbrowser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prateekcode.githubbrowser.db.Repotity
import com.prateekcode.githubbrowser.model.branch.Branch
import com.prateekcode.githubbrowser.model.commit.Commits
import com.prateekcode.githubbrowser.model.issuedir.IssueCollection
import com.prateekcode.githubbrowser.model.repo.Repo
import com.prateekcode.githubbrowser.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class ApiViewModel(private val repository: Repository) : ViewModel() {

    var repoResponse: MutableLiveData<Response<Repo>> = MutableLiveData()
    fun githubRepository(ownerName: String, repoName: String) {
        viewModelScope.launch {
            val response = repository.getRepo(ownerName, repoName)
            repoResponse.value = response
        }
    }

    var branchResponse: MutableLiveData<Response<Branch>> = MutableLiveData()
    fun getBranches(ownerName: String, repoName: String) {
        viewModelScope.launch {
            val response = repository.getBranches(ownerName, repoName)
            branchResponse.value = response
        }
    }

    var commitMessageResponse: MutableLiveData<Response<Commits>> = MutableLiveData()
    fun getCommitMessage(ownerName: String, repoName: String, shaKey: String) {
        viewModelScope.launch {
            val response = repository.getCommitMessage(ownerName, repoName, shaKey)
            commitMessageResponse.value = response
        }
    }

    var openIssueResponse: MutableLiveData<Response<List<IssueCollection>>> = MutableLiveData()
    fun getOpenIssue(ownerName: String, repoName: String, state: String) {
        viewModelScope.launch {
            val response = repository.getOpenIssue(ownerName, repoName, state)
            openIssueResponse.value = response
        }
    }

    fun getTheRepo() = viewModelScope.launch(Dispatchers.IO) { repository.getAllTheRepo() }
    fun insertTheRepo(repotity: Repotity) = viewModelScope.launch { repository.insertTheRepo(repotity) }
    fun deleteTheRepo(repotity: Repotity) = viewModelScope.launch { repository.deleteTheRepo(repotity) }
    fun deleteEntireDb() = viewModelScope.launch(Dispatchers.IO) { repository.deleteEntireDb() }

}