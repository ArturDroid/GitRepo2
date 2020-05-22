package com.ardroid.gitrepo.dataSources.repositories

import com.ardroid.gitrepo.dataSources.objects.user.User
import com.ardroid.gitrepo.dataSources.objects.user.UserResponse
import com.ardroid.gitrepo.dataSources.remote.GithubApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class UserRepository {
    private val gitHubApi = GithubApi.getInstance()
    fun getUser(username: String): Single<User> {
        return gitHubApi.getUser(username)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun searchUser(query: String, page: Int, perPage: Int): Single<UserResponse> {
        return gitHubApi.searchUser(query, page, perPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}
