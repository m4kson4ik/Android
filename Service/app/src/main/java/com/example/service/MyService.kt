package com.example.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.collectAsState
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import java.math.BigInteger

class AddBinder(num1 : Int) : Binder()
{
    private val scope by lazy { CoroutineScope(Dispatchers.Default) }
    private val scopeForCancel by lazy { CoroutineScope(Dispatchers.Default) }

    enum class ResultStatus
    {
        OK,
        Canceled,
        Init,
    }

    val resultMutableStateFlow = MutableStateFlow<Pair<BigInteger, ResultStatus>> (BigInteger.ZERO to ResultStatus.Init)
    private val resultStateFlow = resultMutableStateFlow.asStateFlow()
    init {
        scope.launch {
            val innerIntent = Intent()
            val fib = mathService().fibonacci(num1.toBigInteger())
            val result = mathService().factorial(fib)
            delay(1000)

            resultMutableStateFlow.emit(result to ResultStatus.OK)
        }
    }

    fun cancel()
    {
        scope.cancel()
        scopeForCancel.launch {
            resultMutableStateFlow.emit(BigInteger.ZERO to ResultStatus.Canceled)
        }
    }
}

class MyService : Service() {
    private var id = 1
    lateinit var resultMain : String
    private val scope by lazy { CoroutineScope(Dispatchers.Default) }
    private val scopeForCancel by lazy { CoroutineScope(Dispatchers.Default) }
    enum class ResultStatus
    {
        OK,
        Canceled,
        Init,
    }

    val resultMutableStateFlow = MutableStateFlow<Pair<BigInteger, ResultStatus>> (BigInteger.ZERO to ResultStatus.Init)
    private val resultStateFlow = resultMutableStateFlow.asStateFlow()

    private fun extracted(num1 : Int) {
        scope.launch {
            val fib = mathService().fibonacci(num1.toBigInteger())
            val result = mathService().factorial(fib)
            val innerIntent = Intent(this@MyService, MainActivity::class.java)
            innerIntent.action = MainActivity.ACTION_SHOW_RESULT
            innerIntent.putExtra(RESULT, result.toString())
            val pendingIntent = PendingIntent.getActivity(this@MyService, 0, innerIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            resultMain = result.toString()
            val builder = NotificationCompat
                .Builder(this@MyService, MainActivity.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(androidx.core.R.drawable.notification_icon_background)
                .setContentTitle(resources.getString(R.string.theresultisreceived))
                .setContentText("$result")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_launcher_background, resources.getString(R.string.showing), pendingIntent)

            val notification = builder.build()
            if (ActivityCompat.checkSelfPermission(
                    this@MyService,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

            }
            NotificationManagerCompat.from(this@MyService).notify(id, notification)
            id++
            resultMutableStateFlow.emit(result to ResultStatus.OK)
        }
    }

    override fun onBind(intent: Intent): IBinder {
        val num1 = intent.getIntExtra(NUMBER1, 0)
        extracted(num1)
        return AddBinder(num1)
    }

    companion object
    {
        const val NUMBER1 = "NUMBER1"
        const val RESULT = "RESULT"
    }
}

class mathService()
{
        fun fibonacci(n: BigInteger): BigInteger {
            if (n == BigInteger.ZERO || n == BigInteger.ONE) {
                return n
            }
            var a = BigInteger.ZERO
            var b = BigInteger.ONE
            var i = BigInteger.valueOf(2)
            while (i.compareTo(n) <= 0) {
                val temp = b
                b = b.add(a)
                a = temp
                i = i.add(BigInteger.ONE)
            }
            return b
        }

        fun factorial(n: BigInteger): BigInteger {
            if (n == BigInteger.ZERO) {
                return BigInteger.ONE
            }
            var result = BigInteger.ONE
            var i = BigInteger.ONE
            while (i.compareTo(n) <= 0) {
                result = result.multiply(i)
                i = i.add(BigInteger.ONE)
            }
            return result
        }
    }
