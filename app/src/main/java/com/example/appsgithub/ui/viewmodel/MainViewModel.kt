package com.example.appsgithub.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appsgithub.response.GetItem
import com.example.appsgithub.response.UserResponse
import com.example.appsgithub.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _user = MutableLiveData<List<GetItem>>()
    val users: LiveData<List<GetItem>> = _user

    private val _link = MutableLiveData<List<GetItem>>()
    val link: LiveData<List<GetItem>> = _link

    private val _ifLoading = MutableLiveData<Boolean>()
    val ifLoading: LiveData<Boolean> = _ifLoading


    companion object{
        private const val TAG = "MainViewModel"
    }

    init {
        getUser()
    }
    fun getUser(query: String = "a"){
        _ifLoading.value = true
        val client = ApiConfig.getApiService().searchUsers(query,ApiConfig.TOKEN)
        client.enqueue(object: Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful){
                    _ifLoading.value = false
                    _user.value = response.body()?.items
                    _link.value = response.body()?.items
                    Log.d(TAG, "${users.value}")
                }else{
                    Log.e(TAG,"onFailure ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _ifLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}