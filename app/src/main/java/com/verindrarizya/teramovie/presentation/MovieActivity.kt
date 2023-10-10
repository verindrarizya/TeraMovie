package com.verindrarizya.teramovie.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.verindrarizya.teramovie.databinding.ActivityMovieBinding
import com.verindrarizya.teramovie.service.MovieFetchUpdateService
import com.verindrarizya.teramovie.util.BroadcastConstant
import com.verindrarizya.teramovie.util.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {

    private val binding: ActivityMovieBinding by lazy {
        ActivityMovieBinding.inflate(layoutInflater)
    }

    private val viewModel: MovieViewModel by viewModels()

    private val movieAdapter: MovieAdapter = MovieAdapter()

    private val movieFetchUpdateBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            NotificationHelper.createNotification(
                context = this@MovieActivity,
                notificationId = NotificationHelper.NEW_MOVIE_DATA_NOTIFICATION_ID,
                channelId = NotificationHelper.NEW_MOVIE_DATA_CHANNEL_ID,
                channelName = NotificationHelper.NEW_MOVIE_DATA_CHANNEL_NAME,
                channelDescription = NotificationHelper.NEW_MOVIE_DATA_CHANNEL_DESC,
                timeOut = 5_000L
            )
            Snackbar.make(binding.root, "Saved Movies Is Updated", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        LocalBroadcastManager.getInstance(this).registerReceiver(
            movieFetchUpdateBroadcastReceiver,
            IntentFilter(BroadcastConstant.FETCH_MOVIE_UPDATED)
        )

        if (savedInstanceState == null) {
            MovieFetchUpdateService.scheduleRepeatingEveryMinute(this)
        }

        setUpRecyclerView()
        observeUiState()
        observeMessage()
    }

    private fun setUpRecyclerView() {
        with(binding.rvMovie) {
            layoutManager = LinearLayoutManager(this@MovieActivity)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieUiState.collect {
                    showLoadingIndicator(it.isLoading)

                    movieAdapter.submitList(it.movieList)
                }
            }
        }
    }

    private fun observeMessage() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.message.collect {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoadingIndicator(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.rvMovie.isVisible = !isLoading
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(movieFetchUpdateBroadcastReceiver)
        super.onDestroy()
    }
}