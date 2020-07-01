package com.ardroid.gitrepo.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardroid.gitrepo.R
import com.ardroid.gitrepo.dataSources.objects.repos.Repos
import com.ardroid.gitrepo.dataSources.objects.repos.ReposItem
import com.ardroid.gitrepo.temp.ReposAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.repos_fragment.*

class ReposFragment : Fragment() {
    private val viewModel by viewModels<ReposViewModel>()
    private var userName: String? = null

    companion object {
        const val TAG = "SUPER_TAG"
        fun newInstance() = ReposFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString(TAG)?.let {
            userName = it
            viewModel.userName = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.repos_fragment, container, false)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        username.text = userName

        viewModel.userLiveData.observe(viewLifecycleOwner, Observer {

            Glide.with(context)
                .load(it.avatar_url)
                .into(image_view_user)

        })


        viewModel.reposLiveData.observe(viewLifecycleOwner, Observer {
            val listReposItem = generateRepos(it)
            recycler_view_repos.adapter = ReposAdapter(listReposItem)
            recycler_view_repos.layoutManager = LinearLayoutManager(context)
            recycler_view_repos.setHasFixedSize(true)

        })
        viewModel.getUser()
        viewModel.getRepos()

        button_cancel.setOnClickListener {
         activity?.supportFragmentManager?.popBackStackImmediate()

        }
    }


    private fun generateRepos(list: List<Repos>): List<ReposItem> {
        val listR = ArrayList<ReposItem>()
        for (i in list.indices) {
            val reposName = list[i].name
            val lang = "Lang: " + list[i].language
            val idValue = "ID: " + list[i].id.toString()
            val item = ReposItem(
                reposName,
                lang,
                idValue
            )
            listR += item

        }
        return listR
    }


}

