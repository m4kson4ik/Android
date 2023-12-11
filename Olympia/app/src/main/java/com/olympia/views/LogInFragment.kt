package com.olympia.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.olympia.MainEntertainment
import com.olympia.R
import com.olympia.databinding.FragmentLogInBinding
import com.olympia.model.DateBase.Users
import com.olympia.model.DateBase.UsersDateBase

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()
        binding.BGoBackOnLogIn.setOnClickListener { controller.navigate(R.id.profileFragment) }
        binding.BSaveOnLogIn.setOnClickListener {
            //vm.autorizationUser(binding.UserNameOnLogIn.text.toString(), binding.PasswordOnLogIn.text.toString())
            val intent = Intent((activity as AppCompatActivity), MainEntertainment::class.java)
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LogInFragment()
    }
}