package com.example.myappusesensors

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.myappusesensors.ui.theme.MyAppUseSensorsTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.nanoseconds
import androidx.activity.viewModels
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.RepeatableSpec
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.myappusesensors.DateBase.Plank
import com.example.myappusesensors.DateBase.PlankDateBase


class MainActivity : ComponentActivity() {
    enum class ResultStatus
    {
        OK,
        Canceled,
    }

    enum class StartStatus
    {
        Start,
        NotStart,
        Wait,
    }

    private val manager by lazy { getSystemService(SENSOR_SERVICE) as SensorManager}

    private val sensor by lazy { manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)}
    var flowX = MutableStateFlow<Pair<Float, ResultStatus>>(0.0F to ResultStatus.Canceled)
    var flowY = MutableStateFlow<Pair<Float, ResultStatus>>(0.0F to ResultStatus.Canceled)
    var flowZ = MutableStateFlow<Pair<Float, ResultStatus>>(0.0F to ResultStatus.Canceled)
    var flowStamp = MutableStateFlow(0L)

    private val listener = object:SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            startTime = System.currentTimeMillis()
            if (event != null) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                var stamp = event.timestamp
                lifecycleScope.launch {
                    if ((z > -10 && z < 10 && y < 6 && y > - 6 && (x < 11 || x > -9)))
                    {
                        flowY.emit(y to ResultStatus.OK)
                        flowZ.emit(z to ResultStatus.OK)
                        flowX.emit(x to ResultStatus.OK)
                        flowStamp.emit(stamp)
                    }
                    else
                    {
                        flowY.emit(y to ResultStatus.Canceled)
                        flowZ.emit(z to ResultStatus.Canceled)
                        flowX.emit(x to ResultStatus.Canceled)
                    }
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }

    private var result = MutableStateFlow<List<Long>>(emptyList())
    private val statusStart = MutableStateFlow(StartStatus.NotStart)
    private var startTime = 0L
    private var start : kotlin.time.Duration? = null

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        val db = Room.databaseBuilder(applicationContext, PlankDateBase::class.java, "planks.db").build()
        val viewModel: PlankViewModels by viewModels { PlankViewModelFactory(PlankRepository(db)) }
        super.onCreate(savedInstanceState)
        manager.registerListener(
            listener,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        setContent {
            MyAppUseSensorsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        val x by flowX.collectAsState()
                        val y by flowY.collectAsState()
                        val z by flowZ.collectAsState()
                        val stamp by flowStamp.collectAsState()
                        val startStatus by statusStart.collectAsState()

                        when(startStatus)
                        {
                            StartStatus.Start -> if (y.second == ResultStatus.OK && x.second == ResultStatus.OK && z.second == ResultStatus.OK && start != null) {
                                Text((stamp.nanoseconds-start!!).toString())
                            }
                            else {
                                if (start != null) {
                                    lifecycleScope.launch {
                                        result.emit(listOf(stamp))
                                        viewModel.createPlank(Plank(start.toString(),(stamp.nanoseconds - start!!).toString()))
                                        statusStart.emit(StartStatus.NotStart)
                                    }
                                    Text("Остановлено!")
                                }
                            }
                            StartStatus.NotStart -> Button(onClick = {
                                lifecycleScope.launch {
                                    statusStart.emit(StartStatus.Wait)
                                }
                            }) {
                                Text("Запустить")
                            }
                            StartStatus.Wait ->
                                if (y.second == ResultStatus.OK && x.second == ResultStatus.OK && z.second == ResultStatus.OK)
                            {
                                lifecycleScope.launch {
                                    statusStart.emit(StartStatus.Start)
                                }
                                start = stamp.nanoseconds
                            }
                        }
                        val res by viewModel.mutableStateFlowPlank.collectAsState(initial = emptyList())
                        LazyColumn()
                        {
                            items(res)
                            {
                                Text(it.endResult)
                                Row()
                                {
                                    Button( onClick = {
                                       // paintingAnimationPlank(it)
                                    }) {
                                        Text("Показать анимацию")
                                    }
                                    Button(onClick = {
                                        lifecycleScope.launch {
                                            viewModel.deletedPlank(it)
                                        }
                                    }) {
                                        Text("Удалить")
                                    }
                                }
                            }
                        }
                        PersonDrawing()
                    }
                }
            }
        }
    }
}

@Composable
fun PersonDrawing() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val bodyColor = Color.Black
        val headRadius = 50f
        val bodyLength = 150f
        val armLength = 60f
        val legLength = 100f

        // Draw head
        drawCircle(
            color = bodyColor,
            radius = headRadius,
            center = Offset(size.width / 2, size.height / 2 - headRadius - 10f)
        )

        // Draw body
        drawLine(
            color = bodyColor,
            start = Offset(size.width / 2, size.height / 2 + headRadius),
            end = Offset(size.width / 2, size.height / 2 + headRadius + bodyLength),
            strokeWidth = 10f
        )

        // Draw arms
        drawLine(
            color = bodyColor,
            start = Offset(size.width / 2, size.height / 2 + headRadius + 20f),
            end = Offset(size.width / 2 - armLength, size.height / 2 + headRadius + 50f),
            strokeWidth = 10f
        )

        drawLine(
            color = bodyColor,
            start = Offset(size.width / 2, size.height / 2 + headRadius + 20f),
            end = Offset(size.width / 2 + armLength, size.height / 2 + headRadius + 50f),
            strokeWidth = 10f
        )

        // Draw legs
        drawLine(
            color = bodyColor,
            start = Offset(size.width / 2, size.height / 2 + headRadius + bodyLength),
            end = Offset(size.width / 2 - legLength, size.height / 2 + headRadius + bodyLength + legLength),
            strokeWidth = 10f
        )

        drawLine(
            color = bodyColor,
            start = Offset(size.width / 2, size.height / 2 + headRadius + bodyLength),
            end = Offset(size.width / 2 + legLength, size.height / 2 + headRadius + bodyLength + legLength),
            strokeWidth = 10f
        )
    }
}


class PlankViewModelFactory(private val repository: PlankRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlankViewModels::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlankViewModels(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}