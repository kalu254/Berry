package com.luka.berry.ui

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Build.VERSION_CODES.O
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.luka.berry.R
import com.luka.berry.adapter.UnsplashPhotoAdapter
import com.luka.berry.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels<MainViewModel>()

    val CHANNEL_ID: String = "channelID";
    val CHANNEL_NAME: String = "channelName";

    val NOTIFICATION_ID = 0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val adapter = UnsplashPhotoAdapter()

        binding.apply {
            recyclerView.setHasFixedSize(true);
            recyclerView.adapter = adapter


        }

        lifecycleScope.launch {
            mainViewModel.photos.observe(this@MainActivity) {
                adapter.submitData(lifecycle, it)
            }

        }
        createNotificationChannel()

        val intent = Intent(this,MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Awesome notification")
            .setContentText("This should be the notification content")
            .setSmallIcon(R.drawable.ic_error)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()


        val notificationManager = NotificationManagerCompat.from(this)

        binding.floatingActionButton.setOnClickListener {
            notificationManager.notify(NOTIFICATION_ID, notification)
        }




    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}
