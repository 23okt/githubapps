package com.example.appsgithub.retrofit


import com.example.appsgithub.response.DetailResponse
import com.example.appsgithub.response.FollowersResponseItem
import com.example.appsgithub.response.FollowingResponseItem
import com.example.appsgithub.response.GetItem
import com.example.appsgithub.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String,
        @Header("Authorization") token: String,
    ): Call<UserResponse>

    @GET("/users/{username}")
    fun profileUser(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun followers(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): Call<List<FollowersResponseItem>>

    @GET("users/{username}/following")
    fun following(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): Call<List<FollowingResponseItem>>
}