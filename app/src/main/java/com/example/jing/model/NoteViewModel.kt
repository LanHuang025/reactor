package com.example.jing.model

import android.app.Application
import androidx.appsearch.app.SearchResult
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val _errorMessageLiveData = MutableLiveData<String?>()
    val errorMessageLiveData: LiveData<String?> = _errorMessageLiveData
    private val _noteLiveData:MutableLiveData<List<SearchResult>> =
        MutableLiveData(mutableListOf())
    private val noteLiveData:LiveData<List<SearchResult>> =_noteLiveData

   // private val noteAppSearchManager:
}