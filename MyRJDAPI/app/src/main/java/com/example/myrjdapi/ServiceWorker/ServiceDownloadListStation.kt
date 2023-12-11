package com.example.myrjdapi.ServiceWorker

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.myrjdapi.Model.AllStation.Country
import com.example.myrjdapi.Model.AllStation.Station
import com.example.myrjdapi.Repository.TrainRepositoryAPI
import com.example.myrjdapi.Repository.TrainRepositoryBasic
import com.example.myrjdapi.Room.StationDB
import com.example.myrjdapi.ViewModel.TrainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class ServiceDownloadListStation @Inject constructor() : Service() {

    @Inject
    lateinit var viewModel: TrainViewModel

    val scope = CoroutineScope(Dispatchers.Default)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
            scope.launch {
                viewModel.getStation()
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBind(intent: Intent?): IBinder?
    {
        scope.launch {
            viewModel.getStation()
        }
        return null
    }

    companion object
    {
        const val NOTIFICATION_CHANNEL_ID = "ServiceDownloadStation"
    }
}