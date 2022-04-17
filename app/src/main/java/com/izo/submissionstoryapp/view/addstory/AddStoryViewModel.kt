package com.izo.submissionstoryapp.view.addstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.izo.submissionstoryapp.data.local.UserModel
import com.izo.submissionstoryapp.data.local.UserPreference

class AddStoryViewModel(private val pref: UserPreference) : ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

}