package com.ardroid.gitrepo.dataSources.remote

import com.ardroid.gitrepo.dataSources.objects.user.User
import com.ardroid.gitrepo.dataSources.objects.user.UserResponse
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubCoroutinesApi {
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String):  User

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String): UserResponse

    companion object Factory {
        lateinit var apiInstance: GitHubCoroutinesApi
        fun getInstance(): GitHubCoroutinesApi {
            if (!::apiInstance.isInitialized) {
                apiInstance = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                    .baseUrl("https://api.github.com/")
                    .build()
                    .create(GitHubCoroutinesApi::class.java)
            }
            return apiInstance
        }
    }
}