package com.olympia

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.olympia.databinding.ActivityMainBinding
import com.olympia.extensions.Info
import com.olympia.model.DateBase.Users
import com.olympia.model.database.IUsers
import com.olympia.model.repositories.IUserRepository
import com.olympia.model.repositories.UserRepository
import com.olympia.viewModels.MyEvent
import com.olympia.viewModels.OlympiaViewModel
import com.olympia.viewModels.useCases.AddUserData
import com.olympia.viewModels.useCases.UsersUseCases
import com.olympia.views.activities.MainEntertainment
import com.olympia.views.activities.SignUp
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel : OlympiaViewModel by viewModels()
    private var listUsers : List<Users> = emptyList()
    private lateinit var launcher : ActivityResultLauncher<Intent>

//    override fun onStart() {
//        super.onStart()
//        val item = listUsers.find { it.autoSign == true }
//        if (item != null)
//        {
//            signIn(item)
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.launch {
            viewModel.stateFlowUsers.collect { list -> listUsers = list }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        //val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        //viewModel = ViewModelProvider(this, factory).get(UsersViewModel::class.java)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        MyEvent.addListener { event -> if(event == 0) recreate() }
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK) {
                binding.UserNameOnLogIn.text.clear()
                binding.PasswordOnLogIn.text.clear()
            }
        }
        lifecycleScope.launch {
            viewModel.stateFlowUsers.collect { list -> listUsers = list }
        }

        binding.BLogInOnMain.setOnClickListener {
            var item = listUsers.find { it.userName == binding.UserNameOnLogIn.text.toString() && it.password == binding.PasswordOnLogIn.text.toString() }
            signIn(item)
        }
        binding.BSignUpOnMain.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            launcher.launch(intent)
        }
    }

    private fun signIn(item : Users?)
    {
        if (item != null)
        {
            Info.USERNAME = item.userName
            Info.PASSWORD = item.password
            Info.BIRTHDAY = item.birthday
            Info.EMAIL = item.email
            Info.SEX = item.autoSign.toString()
            Info.ID = item.idUser.toString()
            launcher.launch(Intent(this, MainEntertainment::class.java))
        }
        else
        {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG)
        }
    }
}