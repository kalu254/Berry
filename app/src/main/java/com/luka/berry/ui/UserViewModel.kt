package com.luka.berry.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.luka.berry.model.User
import com.luka.berry.persistence.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val  userDao: UserDao ) :  ViewModel() {

     val users = userDao.getUsers().asLiveData()


}
