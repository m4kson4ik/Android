package com.olympia.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.olympia.MainEntertainment
import com.olympia.R
import com.olympia.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
       // val viewModelUsers : UsersViewModel by viewModels()
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()
        binding.BGoBackOnSignUp.setOnClickListener { controller.navigate(R.id.profileFragment) }
        binding.BSaveOnSignUp.setOnClickListener {
            val intent = Intent((activity as AppCompatActivity), MainEntertainment::class.java)
            startActivity(intent)
        }
        binding.BSaveOnSignUp.setOnClickListener{
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpFragment()
    }
}