package it.unical.demacs.service

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import it.unical.demacs.service.Constrants.CHANNEL_ID
import it.unical.demacs.service.Constrants.MUSIC_NOTIFICATION_ID
import java.nio.channels.Channel

class MyService : Service() {

    private lateinit var musicPlayer:MediaPlayer

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        initMusic()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotfication()

        return super.onStartCommand(intent, flags, startId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotfication() {
        val notificationIntent = Intent(this, MainActivity::class.java)

         val pendingIntent = PendingIntent.getActivity(
             this, 0, notificationIntent, 0
         )

        val notification = Notification.Builder(this, CHANNEL_ID)
            .setContentText("Music Player")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(MUSIC_NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, "My Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(
                NotificationManager::class.java
            )

            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun initMusic() {
        musicPlayer = MediaPlayer.create(this, R.raw.noemi)
        musicPlayer.isLooping = false
        musicPlayer.setVolume(100F, 100F)

    }
}