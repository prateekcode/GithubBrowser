package com.prateekcode.githubbrowser

import com.prateekcode.githubbrowser.api.RetrofitInstance
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    private val instance = RetrofitInstance

    //Finding the Repo
    @Test
    fun getRepo(){
        runBlocking {
            val repository = instance.api.getRepository("prateekcode", "URLShortner")
            println(repository)
            println(repository.body())
        }
    }

    //Testing the Branches
    @Test
    fun getBranches(){
        runBlocking {
            val branches = instance.api.getBranches("prateekcode", "URLShortner")
            println(branches)
            println(branches.body())
        }
    }

    //Testing the Commit Messages
    @Test
    fun getCommitMessage(){
        runBlocking {
            val commitMsg = instance.api.getCommitMessage("prateekcode", "URLShortner", "891c3f3f6e7298b6bf8eaeab416a48da28df4663")
            println(commitMsg)
            println(commitMsg.body())
        }
    }

    //Testing the present open issue
    @Test
    fun getOpenIssue(){
        runBlocking {
            val openIssue = instance.api.getOpenIssues("prateekcode", "URLShortner", "open")
            println(openIssue)
            println(openIssue.body())
        }
    }
}

