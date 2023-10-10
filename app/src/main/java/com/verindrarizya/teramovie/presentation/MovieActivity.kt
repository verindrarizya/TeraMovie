package com.verindrarizya.teramovie.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.verindrarizya.teramovie.databinding.ActivityMovieBinding
import com.verindrarizya.teramovie.domain.usecase.MovieUseCase
import com.verindrarizya.teramovie.service.MovieFetchUpdateService
import com.verindrarizya.teramovie.util.BroadcastConstant
import com.verindrarizya.teramovie.util.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {

    private val binding: ActivityMovieBinding by lazy {
        ActivityMovieBinding.inflate(layoutInflater)
    }

    private val viewModel: MovieViewModel by viewModels()

    @Inject
    lateinit var movieUseCase: MovieUseCase

    private val movieAdapter: MovieAdapter = MovieAdapter()

    private val movieFetchUpdateBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            NotificationHelper.createNotification(
                context = this@MovieActivity,
                notificationId = NotificationHelper.NEW_MOVIE_DATA_NOTIFICATION_ID,
                channelId = NotificationHelper.NEW_MOVIE_DATA_CHANNEL_ID,
                channelName = NotificationHelper.NEW_MOVIE_DATA_CHANNEL_NAME,
                channelDescription = NotificationHelper.NEW_MOVIE_DATA_CHANNEL_DESC,
                timeOut = 5_000L,
                contentTitle = "New Data Is Available",
                contentText = "Come and see newest movie!"
            )
            Snackbar.make(binding.root, "Saved Movies Is Updated", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        splashScreenCondition(splashScreen)

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

    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

    private fun splashScreenCondition(splashScreen: SplashScreen) {
        splashScreen.setKeepOnScreenCondition { true }

        runBlocking {
            val movieData = movieUseCase.getMovies().first()

            if (!isNetworkConnected() && movieData.isEmpty()) {
                NotificationHelper.createNotification(
                    context = this@MovieActivity,
                    notificationId = NotificationHelper.STATUS_NOTIFICATION_ID,
                    contentTitle = "Warning About Network & Movie Data",
                    contentText = "Network not connected & movie data is empty",
                    channelId = NotificationHelper.STATUS_CHANNEL_ID,
                    channelName = NotificationHelper.STATUS_CHANNEL_NAME,
                    channelDescription = NotificationHelper.STATUS_CHANNEL_DESC,
                    timeOut = 5_000
                )
            }

            splashScreen.setKeepOnScreenCondition { false }
        }
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(movieFetchUpdateBroadcastReceiver)
        super.onDestroy()
    }
}