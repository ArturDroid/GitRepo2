package com.ardroid.gitrepo.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardroid.gitrepo.dataSources.objects.repos.Repos
import com.ardroid.gitrepo.dataSources.objects.user.User
import com.ardroid.gitrepo.dataSources.objects.user.UserResponse
import com.ardroid.gitrepo.dataSources.repositories.ReposRepository
import com.ardroid.gitrepo.dataSources.repositories.UserRepository
import io.reactivex.disposables.CompositeDisposable
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val reposRepository = ReposRepository()
    private val userRepository = UserRepository()
    val liveData = MutableLiveData<Int>()
    val userLiveData = MutableLiveData<User>()
    val reposLiveData = MutableLiveData<List<Repos>>()
    val searchUsersLiveData = MutableLiveData<UserResponse>()
    lateinit var userName: String


    fun getNumber() {
        liveData.value = Random.nextInt(100, 150)
    }

    fun getUser() {
        compositeDisposable.add(
            userRepository.getUser(userName)
                .subscribe({
                    userLiveData.value = it
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun getRepos() {

        compositeDisposable.add(
            reposRepository.getRepos(userName)
                .subscribe({

                    reposLiveData.value = it

                }, {
                    it.printStackTrace()
                })
        )
    }

    fun searchUsers() {
        compositeDisposable.add(
            userRepository.searchUser(userName).subscribe({
                searchUsersLiveData.value = it
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
