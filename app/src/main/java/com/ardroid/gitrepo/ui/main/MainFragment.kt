package com.ardroid.gitrepo.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardroid.gitrepo.R
import com.ardroid.gitrepo.dataSources.objects.repos.Repos
import com.ardroid.gitrepo.dataSources.objects.user.User
import com.ardroid.gitrepo.temp.ExampleAdapter
import com.ardroid.gitrepo.temp.ExampleItem
import com.ardroid.gitrepo.temp.RecyclerViewClick
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private val viewModel by activityViewModels<MainViewModel>()


    companion object {
        const val TAG = "SUPER_TAG"
        const val KEY_SEARCH = "KEY_SEARCH"
        fun newInstance() = MainFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
        savedInstanceState?.let {
            edit_text_username.setText(it.getString(KEY_SEARCH, ""))
        }
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.setHasFixedSize(true)


        viewModel.searchUsersLiveData.observe(viewLifecycleOwner, Observer {
            val listExampleItem = convertSearch(it.items)
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
                        ?.addToBackStack(null)
                        ?.commit()


                }

            })


        })

        search_button.setOnClickListener {
            viewModel.userName = edit_text_username.text.toString()
            viewModel.searchUserQuery = edit_text_username.text.toString()
            //viewModel.searchUsers()
            GlobalScope.launch {
                viewModel.searchUsersCoroutine()
                Snackbar.make(requireView(),"Searching done",Snackbar.LENGTH_LONG)
            }
        }
    }

    private fun convertSearch(listUsers: List<User>): List<ExampleItem> {
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


    private fun convertUserRepos(listRepos: List<Repos>): List<ExampleItem> {
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
