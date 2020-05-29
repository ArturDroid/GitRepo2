package com.ardroid.gitrepo.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardroid.gitrepo.dataSources.objects.repos.Repos
import com.ardroid.gitrepo.dataSources.repositories.ReposRepository
import io.reactivex.disposables.CompositeDisposable

class ReposViewModel:ViewModel() {
    private val reposRepository = ReposRepository()
    private val compositeDisposable = CompositeDisposable()
    val reposLiveData = MutableLiveData<List<Repos>>()
    lateinit var userName:String






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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}