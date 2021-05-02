package com.prateekcode.githubbrowser.repository

import com.prateekcode.githubbrowser.api.RetrofitInstance
import com.prateekcode.githubbrowser.db.Repodao
import com.prateekcode.githubbrowser.db.Repotity
import com.prateekcode.githubbrowser.model.branch.Branch
import com.prateekcode.githubbrowser.model.commit.Commits
import com.prateekcode.githubbrowser.model.issuedir.IssueCollection
import com.prateekcode.githubbrowser.model.repo.Repo
import retrofit2.Response

class Repository(private val repodao: Repodao) {

    suspend fun getRepo(
        ownerName: String,
        repoName: String
    ): Response<Repo> {
        return RetrofitInstance.api.getRepository(ownerName, repoName)
    }

    suspend fun getBranches(
        ownerName: String,
        repoName: String
    ): Response<Branch> {
        return RetrofitInstance.api.getBranches(ownerName, repoName)
    }

    suspend fun getCommitMessage(
        ownerName: String,
        repoName: String,
        shaKey: String
    ): Response<Commits> {
        return RetrofitInstance.api.getCommitMessage(ownerName, repoName, shaKey)
    }

    suspend fun getOpenIssue(
        ownerName: String,
        repoName: String,
        state: String
    ): Response<List<IssueCollection>> {
        return RetrofitInstance.api.getOpenIssues(ownerName, repoName, state)
    }

    suspend fun insertTheRepo(repotity: Repotity){
        repodao.insertRepo(repotity)
    }

    suspend fun deleteTheRepo(repotity: Repotity){
        repodao.delete(repotity)
    }

    suspend fun deleteEntireDb(){
        repodao.deleteAll()
    }

    suspend fun getAllTheRepo(){
        repodao.getAllRepo()
    }

}