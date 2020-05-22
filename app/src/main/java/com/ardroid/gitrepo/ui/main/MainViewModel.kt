package com.ardroid.gitrepo.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardroid.gitrepo.dataSources.objects.user.User
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
    val reposLiveData = MutableLiveData<String>()


    fun getNumber() {
        liveData.value = Random.nextInt(100, 150)
    }

    fun getUser() {
        compositeDisposable.add(
            userRepository.getUser("ArturDroid")
                .subscribe({
                    Log.d("SUPER_TAG", "id: ${it.id} login; ${it.login}")
                    userLiveData.value = it
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun getRepos() {

        compositeDisposable.add(
            reposRepository.getRepos("ArturDroid")
                .subscribe({
                    val data = it

                    Log.d("SUPER_TAG", data.name)
                    reposLiveData.value = data.name
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
