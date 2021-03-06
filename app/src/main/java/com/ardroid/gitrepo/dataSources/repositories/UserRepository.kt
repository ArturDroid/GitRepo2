package com.ardroid.gitrepo.dataSources.repositories

import com.ardroid.gitrepo.dataSources.objects.user.User
import com.ardroid.gitrepo.dataSources.objects.user.UserResponse
import com.ardroid.gitrepo.dataSources.remote.GitHubCoroutinesApi
import com.ardroid.gitrepo.dataSources.remote.GithubApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class UserRepository {
    private val gitHubCoroutinesApi:GitHubCoroutinesApi = GitHubCoroutinesApi.getInstance()
    private val gitHubApi = GithubApi.getInstance()
    fun getUser(username: String): Single<User> {
        return gitHubApi.getUser(username)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun searchUser(query: String): Single<UserResponse> {
        return gitHubApi.searchUser(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun authUser(username:String,token:String):Single<User>{
        return gitHubApi.authUser(username,"token $token")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    suspend fun getUserCoroutine(username: String): User {
        return gitHubCoroutinesApi.getUser(username)
    }

    suspend fun searchUserCoroutine(username:String) = gitHubCoroutinesApi.searchUser(username)

}
