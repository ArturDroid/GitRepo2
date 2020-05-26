package com.ardroid.gitrepo.dataSources.data

import android.content.Context
import com.ardroid.gitrepo.MainActivity

class DataContext : MainActivity() {
    companion object {
        private lateinit var context: Context

        fun setContext(con: Context) {
            context = con
        }

        fun getContext(): Context {
                return context
        }




    }


}