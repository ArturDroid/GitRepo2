package com.ardroid.gitrepo.dataSources.remote


import com.ardroid.gitrepo.dataSources.objects.repos.Repos
import com.ardroid.gitrepo.dataSources.objects.user.User
import com.ardroid.gitrepo.dataSources.objects.user.UserResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/users")
    fun searchUser(
        @Query("q") query: String ): Single<UserResponse>
//        @Query("page") page: Int,
//        @Query("per_page") perPage: Int


    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Single<User>

    @GET("users/{username}/repos")
    fun getRepos(@Path("username") username: String): Single<List<Repos>>


    companion object Factory {
        lateinit var apiInstance: GithubApi
        fun getInstance(): GithubApi {
            if (!::apiInstance.isInitialized) {
                apiInstance = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.github.com/")
                    .build()
                    .create(GithubApi::class.java)
            }
            return apiInstance
        }
    }
}