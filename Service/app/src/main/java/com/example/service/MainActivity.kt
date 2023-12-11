package com.example.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.service.ui.theme.ServiceTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigInteger
import androidx.compose.foundation.lazy.LazyColumn as LazyColumn1

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name = resources.getString(R.string.calculate)
        val description = resources.getString(R.string.ansvers)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID,name,importance)
        channel.description = description
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        setContent {
            ServiceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var binder by rememberSaveable { mutableStateOf<List<AddBinder>>(emptyList()) }
                    var number1 by rememberSaveable { mutableStateOf("") }
                    var id by rememberSaveable {
                        mutableStateOf(0)
                    }
                    if (intent.action == ACTION_SHOW_RESULT) {
                        val result = intent.getStringExtra(MyService.RESULT)
                        Text(resources.getString(R.string.Result)+ "-  $result")
                    } else {
                        Column {
                            TextField(
                                value = number1,
                                onValueChange = { number1 = it },
                                label = { Text(text = resources.getString(R.string.Number1)) })

                            Button(onClick = {
                                if (number1.toIntOrNull() == null || number1.toInt() > 21 || number1.toInt() < 1) {
                                    return@Button
                                }
                                val intent = Intent(
                                    this@MainActivity,
                                    MyService::class.java
                                )
                                intent.action = "$id"
                                id++
                                intent.putExtra(MyService.NUMBER1, number1.toIntOrNull() ?: 0)
                                bindService(intent, object : ServiceConnection {
                                    override fun onServiceConnected(
                                        p0: ComponentName?,
                                        p1: IBinder?
                                    ) {
                                        val bind = p1 as? AddBinder
                                        if (bind != null) {
                                            binder = binder + listOf(bind)
                                        }
                                    }

                                    override fun onServiceDisconnected(p0: ComponentName?) {
                                    }

                                }, Context.BIND_AUTO_CREATE)

                            }) {
                                Text(resources.getString(R.string.calculate))
                            }

                            LazyColumn1 {
                                items(binder) {
                                    val result = it.resultMutableStateFlow.collectAsState()
                                    val text = when (result.value.second) {
                                        AddBinder.ResultStatus.Canceled -> {
                                            resources.getString(R.string.cancel)
                                        }

                                        AddBinder.ResultStatus.Init -> {
                                            resources.getString(R.string.starting)
                                        }

                                        AddBinder.ResultStatus.OK -> {
                                            result.value.first.toString()
                                        }
                                    }

                                    Row()
                                    {
                                        Text(text)
                                        //resultText = result.value.first.toString()
                                        if (result.value.second == AddBinder.ResultStatus.Init) {
                                            Button(onClick = {
                                                it?.cancel()
                                            }) {
                                                Text(resources.getString(R.string.cancel))
                                            }
                                        }
                                    }
                                }

                            }

                        }
                    }
                }
            }
        }
    }

    companion object
    {
        const val NOTIFICATION_CHANNEL_ID = "mathService"
        const val ACTION_SHOW_RESULT = "SHOW_RESULT"
    }
}