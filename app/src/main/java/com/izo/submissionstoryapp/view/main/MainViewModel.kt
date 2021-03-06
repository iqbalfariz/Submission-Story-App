package com.izo.submissionstoryapp.view.main

import android.util.Log
import androidx.lifecycle.*
import com.izo.submissionstoryapp.data.ListStoryItem
import com.izo.submissionstoryapp.data.StoriesResponse
import com.izo.submissionstoryapp.data.local.UserModel
import com.izo.submissionstoryapp.data.local.UserPreference
import com.izo.submissionstoryapp.data.remote.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: UserPreference) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

    fun getStories(auth: String): LiveData<List<ListStoryItem>> {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getStories(auth)
        val listStory = MutableLiveData<List<ListStoryItem>>()
        client.enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _isLoading.value = false
                    listStory.value = responseBody.listStory
                } else {
                    _isLoading.value = false
                    Log.e(MainActivity.TAG, "onFailure1: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(MainActivity.TAG, "onFailure2: ${t.message}")
            }

        })
        return listStory
    }

}