package com.olympia.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.olympia.databinding.FragmentTrainingBinding

class Training : Fragment() {
    private lateinit var binding: FragmentTrainingBinding
    //private val vm: UsersViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTrainingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  vm.getUserData()
      //  binding.text.text = "${vm.userState.value?.id}\n ${vm.userState.value?.userName}\n ${vm.userState.value?.password}\n ${vm.userState.value?.gender}\n ${vm.userState.value?.dateOfBirth}\n ${vm.userState.value?.email}\n ${vm.userState.value?.registerDate}"
    }
    companion object {
        @JvmStatic
        fun newInstance() = Training()
    }
}