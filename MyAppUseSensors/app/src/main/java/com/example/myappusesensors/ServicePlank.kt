package com.example.myappusesensors

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.security.Provider.Service

class AddBinder : Binder() {
}

class ServicePlank () : android.app.Service()
{
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

}