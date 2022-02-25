package com.luka.berry.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.luka.berry.R
import com.luka.berry.databinding.ActivityUsersBinding
import com.luka.berry.model.User
import kotlinx.coroutines.launch

class UsersActivity : AppCompatActivity() {

private lateinit var usersBinding: ActivityUsersBinding;

     private val userViewModel: UserViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        usersBinding = DataBindingUtil.setContentView(this, R.layout.activity_users)
        var arrayAdapter: ArrayAdapter<User>

        lifecycleScope.launch {
            userViewModel.users.observe(this@UsersActivity) {
                arrayAdapter = ArrayAdapter(this@UsersActivity,R.layout.item_user, it)
                usersBinding.userList.adapter = arrayAdapter
            }

        }






    }
}
