package com.ardroid.gitrepo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ardroid.gitrepo.dataSources.data.CheckNetwork
import com.ardroid.gitrepo.dataSources.data.DataContext
import com.ardroid.gitrepo.dataSources.repositories.ReposRepository
import com.ardroid.gitrepo.dataSources.repositories.UserRepository
import com.ardroid.gitrepo.temp.Callback
import com.ardroid.gitrepo.ui.main.MainFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class MainActivity : AppCompatActivity() {
    private val userRepository = UserRepository()
    private val disposeBag = CompositeDisposable()
    private val reposRepository = ReposRepository()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()

            DataContext.setContext(this)



//
//            fun Context.isConnectedToNetwork(): Boolean {
//                val connectivityManager =
//                    this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
//                return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting ?: false
//            }

            if (CheckNetwork.isConnected(this)) {
                    Log.d("SUPER_TAG", "Connected")

                }
            else Log.d("SUPER_TAG", "!Connected")


            val result: Disposable = userRepository.getUser("ArDroid")
                .subscribe({
                    Log.d("SUPER_TAG", "id: ${it.id} login; ${it.login}")

                }, {
                    it.printStackTrace()
                })
            disposeBag.add(result)
            result.dispose()


            if (result.isDisposed) {
                Log.d("TAG", "Dispose")
            }


        }

        val callback = Callback()
        callback.testCallback(4, 5) {
            Log.d("SUPER_TAG", "$it")
            return@testCallback 1
        }


    }

    override fun onDestroy() {
        disposeBag.clear()
        super.onDestroy()
    }

}

