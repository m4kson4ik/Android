package com.olympia.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.olympia.databinding.ActivitySignUpBinding
import com.olympia.model.DateBase.Users
import com.olympia.viewModels.OlympiaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    //private val vm: AccountInf by viewModels()
    private val viewModel: OlympiaViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val launcher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK) {
                setResult(RESULT_OK, intent)
                finish()
            }
        }
        binding.BSaveOnSignUp.setOnClickListener {
           val user = Users(
               binding.UserNameOnSignUp.text.toString(),
               binding.GenderOnSignUp.text.toString(),
               binding.BirthdayOnSignUp.text.toString(),
               binding.EmailOnSignUp.text.toString(),
               binding.PasswordOnSignUp.text.toString(),
               autoSign = true
           )

            lifecycleScope.launch {
                viewModel.create(user)
            }
            launcher.launch(Intent(this, MainEntertainment::class.java))
        }
        binding.BGoBackOnSignUp.setOnClickListener {
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}