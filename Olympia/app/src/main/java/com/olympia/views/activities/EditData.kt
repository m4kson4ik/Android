package com.olympia.views.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.olympia.R
import com.olympia.databinding.ActivitySignUpBinding
import com.olympia.viewModels.AccountInf
import com.olympia.extensions.Info
import com.olympia.model.DateBase.Users
import com.olympia.viewModels.OlympiaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditData : AppCompatActivity() {
    private val viewmodel : OlympiaViewModel by viewModels()
    private var itemUsers : List<Users> = emptyList()
    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.launch {
            viewmodel.stateFlowUsers.collect { itemUsers = it}
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            viewmodel.stateFlowUsers.collect { itemUsers = it}
        }
        val itemUser = itemUsers.find { it.idUser == Info.ID.toInt() }
        if (itemUser != null)
        {
            binding.TitleOnSignUp.text = getString(R.string.editing_data)
            binding.UserNameOnSignUp.setText(itemUser.userName)
            binding.PasswordOnSignUp.setText(itemUser.password)
            binding.GenderOnSignUp.setText(itemUser.gender)
            binding.BirthdayOnSignUp.setText(itemUser.birthday)
            binding.EmailOnSignUp.setText(itemUser.email)
            binding.BSaveOnSignUp.setOnClickListener {
                val item = Users(binding.UserNameOnSignUp.text.toString(),
                    binding.GenderOnSignUp.text.toString(), binding.BirthdayOnSignUp.text.toString(),
                    binding.EmailOnSignUp.text.toString(),binding.PasswordOnSignUp.text.toString(), autoSign = false)
                lifecycleScope.launch {
                    viewmodel.editing(item)
                }
                finish()
            }
        }

        binding.BGoBackOnSignUp.setOnClickListener { finish() }
    }
}