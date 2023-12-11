package com.olympia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import com.olympia.databinding.ActivityMainBinding
import com.olympia.databinding.ActivityMainEntertainmentBinding
import com.olympia.extensions.message
import com.olympia.extensions.openFragment
import com.olympia.views.HomeFragment
import com.olympia.views.MyHealth
import com.olympia.views.Training

class MainEntertainment : AppCompatActivity() {
    private lateinit var binding: ActivityMainEntertainmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainEntertainmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFragment(R.id.MyHealthFrame, MyHealth.newInstance())
        openFragment(R.id.TrainingFrame, Training.newInstance())
        openFragment(R.id.HomeFrame, HomeFragment.newInstance())
        binding.Navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    when(binding.HomeFrame.visibility) {
                        View.GONE -> {
                            binding.MyHealthFrame.visibility = View.GONE
                            binding.TrainingFrame.visibility = View.GONE
                            binding.HomeFrame.visibility = View.VISIBLE
                        }
                        else -> message("qwerty")
                    }
                }
                R.id.training -> {
                    when(binding.TrainingFrame.visibility) {
                        View.GONE -> {
                            binding.MyHealthFrame.visibility = View.GONE
                            binding.HomeFrame.visibility = View.GONE
                            binding.TrainingFrame.visibility = View.VISIBLE
                        }
                        else -> message("qwerty")
                    }
                }
                R.id.my_health -> {
                    when(binding.MyHealthFrame.visibility) {
                        View.GONE -> {
                            binding.TrainingFrame.visibility = View.GONE
                            binding.HomeFrame.visibility = View.GONE
                            binding.MyHealthFrame.visibility = View.VISIBLE
                        }
                        else -> message("qwerty")
                    }
                }
                R.id.profile -> binding.MainDL.openDrawer(GravityCompat.END)
            }
            true
        }
        binding.NVMenu.setNavigationItemSelectedListener {
            when(it.itemId){
                else -> message("potom sdelaem")
            }
            binding.MainDL.closeDrawer(GravityCompat.END)
            true
        }
    }
}