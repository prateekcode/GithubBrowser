package com.prateekcode.githubbrowser.api

import com.prateekcode.githubbrowser.model.branch.Branch
import com.prateekcode.githubbrowser.model.commit.Commits
import com.prateekcode.githubbrowser.model.issuedir.IssueCollection
import com.prateekcode.githubbrowser.model.repo.Repo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestApi {

    //For Finding the Repository
    @GET("{owner}/{repo}")
    suspend fun getRepository(
        @Path("owner") ownerName:String,
        @Path("repo") repoName:String
    ): Response<Repo>


    //For Finding the Branches
    @GET("{owner}/{repo}/branches")
    suspend fun getBranches(
        @Path("owner") ownerName: String,
        @Path("repo") repoName: String
    ): Response<Branch>


    //For Commit History
    @GET("{owner}/{repo}/commits")
    suspend fun getCommitMessage(
        @Path("owner") ownerName: String,
        @Path("repo") repoName: String,
        @Query("sha") shaKey:String
    ): Response<Commits>


    //For Getting the Open Issues
    @GET("{owner}/{repo}/issues")
    suspend fun getOpenIssues(
        @Path("owner") ownerName: String,
        @Path("repo") repoName: String,
        @Query("state") state:String
    ):Response<List<IssueCollection>>

}