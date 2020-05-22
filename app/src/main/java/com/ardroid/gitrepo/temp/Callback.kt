package com.ardroid.gitrepo.temp

import android.util.Log


class Callback {
    fun testCallback (a:Int,b:Int,callback:(result:Int) -> Int){
        val sum = a+b
      Log.d("SUPER_TAG","${callback.invoke(sum)}")
    }
}