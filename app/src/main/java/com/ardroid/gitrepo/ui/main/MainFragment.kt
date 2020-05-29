package com.ardroid.gitrepo.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardroid.gitrepo.R
import com.ardroid.gitrepo.dataSources.objects.repos.Repos
import com.ardroid.gitrepo.dataSources.objects.user.User
import com.ardroid.gitrepo.temp.ExampleAdapter
import com.ardroid.gitrepo.temp.ExampleItem
import com.ardroid.gitrepo.temp.RecyclerViewClick
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {
    private val viewModel by viewModels<MainViewModel>()

    companion object {
        const val TAG = "SUPER_TAG"
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.setHasFixedSize(true)
        viewModel.reposLiveData.observe(viewLifecycleOwner, Observer {

            val listReposRecyclerView = generateUserRepos(it)
            recycler_view.adapter = ExampleAdapter(listReposRecyclerView, object :
                RecyclerViewClick {
                override fun onClickOnItemClick(view: View, item: ExampleItem, position: Int) {

//                    Log.d(TAG, "Item: $item\nPosition: $position")
                    val nextFragment = ReposFragment()



//                    activity?.supportFragmentManager?.beginTransaction()
//                        ?.replace(
//                            R.id.container,
//                            ReposFragment::class.java,
//                            bundle,
//                            "findThisFragment"
//                        )
//                        ?.commit()

//                    nextFragment.arguments = bundle
//                    activity?.supportFragmentManager?.beginTransaction()
//                        ?.replace(R.id.container, nextFragment, "findThisFragment")
//                        ?.commit()


                }

            })

        })
        //viewModel.getRepos()

        viewModel.searchUsersLiveData.observe(viewLifecycleOwner, Observer {
            val listExampleItem = generateSearch(it.items)
            recycler_view.adapter = ExampleAdapter(listExampleItem, object : RecyclerViewClick {
                override fun onClickOnItemClick(view: View, item: ExampleItem, position: Int) {
                    Log.d(TAG, "Item: $item\nPosition: $position")
                    val bundle = Bundle().apply {
                        this.putString(TAG, item.username)
                    }
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(
                            R.id.container,
                            ReposFragment::class.java,
                            bundle,
                            "findThisFragment"
                        )
                        ?.commit()


                }

            })


        })

        search_button.setOnClickListener {
            viewModel.userName = edit_text_username.text.toString()

            viewModel.searchUsers()


        }


    }

    private fun generateSearch(listUsers: List<User>): List<ExampleItem> {
        val listItem = ArrayList<ExampleItem>()
        for (i in listUsers.indices) {
            //  val drawable = R.drawable.ic_android
            val url = listUsers[i].avatar_url
            val textUserName = listUsers[i].login
            val textFollowers = "id: " + listUsers[i].id.toString()
            val item = ExampleItem(url, textUserName, textFollowers)
            listItem += item

        }
        return listItem
    }


    private fun generateUserRepos(listRepos: List<Repos>): List<ExampleItem> {
        val list = ArrayList<ExampleItem>()
        for (i in listRepos.indices) {


            val urlAvatar = listRepos[i].owner.avatarUrl
            val text1 = listRepos[i].name
            val text2 = listRepos[i].language
            val item = ExampleItem(urlAvatar, text1, text2)
            list += item
        }
        return list
    }


}
