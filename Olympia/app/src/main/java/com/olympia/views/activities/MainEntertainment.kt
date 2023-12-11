package com.olympia.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import com.olympia.R
import com.olympia.databinding.ActivityMainEntertainmentBinding
import com.olympia.extensions.Info
import com.olympia.extensions.openFragment
import com.olympia.model.DateBase.Users
import com.olympia.viewModels.AccountInf
import com.olympia.viewModels.MyEvent
import com.olympia.viewModels.OlympiaViewModel
import com.olympia.views.fragments.HomeFragment
import com.olympia.views.fragments.MyHealth
import com.olympia.views.fragments.Training
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale
@AndroidEntryPoint
class MainEntertainment : AppCompatActivity() {
    //private val vm: AccountInf by viewModels()
    private val viewmodel : OlympiaViewModel by viewModels()
    var list : List<Users> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainEntertainmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            viewmodel.stateFlowUsers.collect { list = it }
        }
        openFragment(R.id.MyHealthFrame, MyHealth.newInstance())
        openFragment(R.id.TrainingFrame, Training.newInstance())
        openFragment(R.id.HomeFrame, HomeFragment.newInstance())
        binding.Navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    if (binding.HomeFrame.visibility == View.GONE){
                        binding.MyHealthFrame.visibility = View.GONE
                        binding.TrainingFrame.visibility = View.GONE
                        binding.HomeFrame.visibility = View.VISIBLE
                    }
                }
                R.id.training -> {
                    if (binding.TrainingFrame.visibility == View.GONE) {
                        binding.MyHealthFrame.visibility = View.GONE
                        binding.HomeFrame.visibility = View.GONE
                        binding.TrainingFrame.visibility = View.VISIBLE
                    }
                }
                R.id.my_health -> {
                    if (binding.MyHealthFrame.visibility == View.GONE){
                        binding.TrainingFrame.visibility = View.GONE
                        binding.HomeFrame.visibility = View.GONE
                        binding.MyHealthFrame.visibility = View.VISIBLE
                    }
                }
                R.id.profile -> binding.MainDL.openDrawer(GravityCompat.END)
            }
            true
        }
        binding.NVMenu.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.personalData -> startActivity(Intent(this, Profile::class.java))
                R.id.editingData -> startActivity(Intent(this, EditData::class.java))
                R.id.changeLanguage -> {
                    val newLocale = if ( resources.configuration.locales[0] == Locale("en")) {
                        Locale("ru")
                    } else {
                        Locale("en")
                    }
                    resources.configuration.setLocale(newLocale)
                    resources.updateConfiguration(resources.configuration, resources.displayMetrics)
                    MyEvent.sendEvent(0)
                    recreate()
                }
                R.id.appInfo -> {/*Тут надо перенести пользователя, к пользовательской документации или информации о приложении*/}


                R.id.exitFromAccount -> {
                    //vm.clearData()
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
            binding.MainDL.closeDrawer(GravityCompat.END)
            true
        }
    }
}