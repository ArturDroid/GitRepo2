package com.ardroid.gitrepo.ui.main

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ardroid.gitrepo.R
import com.ardroid.gitrepo.dataSources.repositories.UserRepository
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by activityViewModels<LoginViewModel>()

    companion object {
        const val TAG = "SUPER_TAG"
        const val TOKEN = "dfa4d11ed74afea8badffc6ff38c0c654f9f0904"

        fun newInstance() = LoginFragment()

    }

    private val userRepository = UserRepository()
    private val disposeBag = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        suspend fun testCo(item: Int): Int {
            delay(1000)
            return item + item
        }

        for (i in 0 until 100)
            GlobalScope.launch(Dispatchers.Default) {
                Log.d(TAG, (testCo(i)).toString())
            }

        GlobalScope.launch(Dispatchers.IO) {
            val userResponse =  userRepository.getUserCoroutine("arturdroid")
            Log.d(TAG,userResponse.login)
        }

        val bundle = Bundle()
        button.setOnClickListener {
            var tokenString: String = token.text.toString()
            if (token.text.toString() == "token") tokenString = TOKEN
            disposeBag.add(
                UserRepository().authUser(username.text.toString(), tokenString)
                    .subscribe({
                        Snackbar.make(
                            requireView(),
                            "Welcome",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        Handler().postDelayed(kotlinx.coroutines.Runnable {
                            activity?.supportFragmentManager?.beginTransaction()
                                ?.replace(
                                    R.id.container,
                                    MainFragment::class.java,
                                    bundle,
                                    "findThisFragment"
                                )
                                ?.addToBackStack(null)
                                ?.commit()
                        }, 1500)


                    }, {
                        Snackbar.make(
                            requireView(),
                            "Incorrect username or token",
                            Snackbar.LENGTH_LONG
                        ).show()
                        it.printStackTrace()
                    })
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.clear()
    }
}