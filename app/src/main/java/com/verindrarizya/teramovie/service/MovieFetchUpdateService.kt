package com.verindrarizya.teramovie.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.verindrarizya.teramovie.data.repository.MovieRepository
import com.verindrarizya.teramovie.util.BroadcastConstant
import com.verindrarizya.teramovie.util.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieFetchUpdateService : Service() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    @Inject
    lateinit var movieRepository: MovieRepository

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scope.launch {
            val result = movieRepository.fetchMovies()
            if (result is Result.Success) {
                LocalBroadcastManager.getInstance(this@MovieFetchUpdateService)
                    .sendBroadcast(Intent(BroadcastConstant.FETCH_MOVIE_UPDATED))
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    companion object {
        fun scheduleRepeatingEveryMinute(context: Context) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val movieFetchUpdateServiceIntent = Intent(context, MovieFetchUpdateService::class.java)

            val pendingIntent = PendingIntent.getService(
                context,
                0,
                movieFetchUpdateServiceIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val initialDelay = System.currentTimeMillis() + 60_000

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                initialDelay,
                60_000,
                pendingIntent
            )
        }
    }
}