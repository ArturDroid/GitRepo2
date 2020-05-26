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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView.setImageResource(R.drawable.ic_person_placeholder)

    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        // viewModel = AndroidViewModel(Application()).getApplication()
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


        viewModel.urlAvatarLiveData.observe(viewLifecycleOwner, Observer {
            Glide.with(context)
                .load(it)
                .apply(RequestOptions().placeholder(R.drawable.ic_person_placeholder))
                .apply(RequestOptions().override(200))
                .into(imageView)
        })


        button.setOnClickListener {
            viewModel.userName = editText.text.toString()
            viewModel.getRepos()

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}
