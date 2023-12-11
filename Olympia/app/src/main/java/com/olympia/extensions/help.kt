package com.olympia.extensions

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.openFragment(holderId: Int, f: Fragment) {
    supportFragmentManager.beginTransaction().replace(holderId, f).commit()
}
fun AppCompatActivity.message(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()