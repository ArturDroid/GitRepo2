package com.ardroid.gitrepo.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ardroid.gitrepo.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            message.text = it.toString()
        })

        //viewModel.getNumber()

        viewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            message.text = "id: ${it.id} login: ${it.login}"
        })
        //viewModel.getUser()

        viewModel.reposLiveData.observe(viewLifecycleOwner, Observer {
            message.text = it
        })
        message.setOnClickListener {
        viewModel.getRepos()
        }



    }

}
