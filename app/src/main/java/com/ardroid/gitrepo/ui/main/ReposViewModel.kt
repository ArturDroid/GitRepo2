package com.ardroid.gitrepo.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardroid.gitrepo.dataSources.objects.repos.Repos
import com.ardroid.gitrepo.dataSources.objects.user.User
import com.ardroid.gitrepo.dataSources.repositories.ReposRepository
import com.ardroid.gitrepo.dataSources.repositories.UserRepository
import io.reactivex.disposables.CompositeDisposable

class ReposViewModel : ViewModel() {
    private val reposRepository = ReposRepository()
    private val userRepository = UserRepository()
    private val compositeDisposable = CompositeDisposable()
    val reposLiveData = MutableLiveData<List<Repos>>()
    val userLiveData = MutableLiveData<User>()
    lateinit var userName: String
    lateinit var searchFieldWord: String


    fun getRepos() {

        compositeDisposable.add(
            reposRepository.getRepos(userName)
                .subscribe({
                    reposLiveData.value = it
                }, {
                    it.printStackTrace()
                }))
    }

    fun getUser() {
        compositeDisposable.add(
            userRepository.getUser(userName).subscribe({
                userLiveData.value = it
            }, {
                it.printStackTrace()
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}