package com.ardroid.gitrepo.dataSources.objects.user


data class UserResponse (val total_count: Int, val incomplete_results: Boolean, val items: List<User>)