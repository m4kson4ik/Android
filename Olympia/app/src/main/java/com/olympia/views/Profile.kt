package com.olympia.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.olympia.R
import com.olympia.databinding.FragmentProfileBinding

class Profile : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        binding.BLogInOnProfile.setOnClickListener { navController.navigate(R.id.logInFragment) }
        binding.BSignUpOnProfile.setOnClickListener { navController.navigate(R.id.signUpFragment) }
    }

    companion object {
        @JvmStatic
        fun newInstance() = Profile()
    }
}