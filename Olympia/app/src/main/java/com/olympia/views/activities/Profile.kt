package com.olympia.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.olympia.databinding.ActivityProfileBinding
import com.olympia.extensions.Info

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.UserNameOnProfile.text = Info.USERNAME
        binding.PasswordOnProfile.text = Info.PASSWORD
        binding.GenderOnProfile.text = Info.SEX
        binding.BirthdayOnProfile.text = Info.BIRTHDAY
        binding.EmailOnProfile.text = Info.EMAIL
        binding.BGoBackOnProfile.setOnClickListener {
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}