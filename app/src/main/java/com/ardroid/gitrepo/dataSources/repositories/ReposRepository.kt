package com.ardroid.gitrepo.dataSources.repositories

import com.ardroid.gitrepo.dataSources.objects.repos.Repos
import com.ardroid.gitrepo.dataSources.remote.GithubApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ReposRepository {
    private val githubApi = GithubApi.getInstance()

    fun getRepos(username: String): Single<List<Repos>> {
        return githubApi.getRepos(username)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())

    }


}