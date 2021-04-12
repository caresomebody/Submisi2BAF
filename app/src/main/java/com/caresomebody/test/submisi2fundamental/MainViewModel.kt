package com.caresomebody.test.submisi2fundamental

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val gitUser = MutableLiveData<ArrayList<GitUser>>()


}