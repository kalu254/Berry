package com.luka.berry.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.luka.berry.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UnsplashRepository) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)


    fun searchPhoto(query: String) {
        currentQuery.value = query
    }

    val photos = currentQuery.switchMap {
        repository.getSearchResults(it).cachedIn(viewModelScope)
    }


    companion object {
        private const val DEFAULT_QUERY = "berries";
    }
}
