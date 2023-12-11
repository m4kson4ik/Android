package com.olympia.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.olympia.R
import com.olympia.databinding.FragmentMyHealthBinding
import com.olympia.forRecyclerViews.RVItem
import com.olympia.forRecyclerViews.adapters.MyHealthRVAdapter
import com.olympia.views.activities.ContentActivity

class MyHealth : Fragment(), MyHealthRVAdapter.Listener {
    private lateinit var binding: FragmentMyHealthBinding
    private val infAdapter = MyHealthRVAdapter(this)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMyHealthBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myHealthItems = arrayOf(RVItem(R.drawable.blood_analysis, getString(R.string.blood_analysis), resources.getStringArray(R.array.forMyHealth)[0]),
            RVItem(R.drawable.weight_analysis, getString(R.string.weight_analysis), resources.getStringArray(R.array.forMyHealth)[1]),
            RVItem(R.drawable.plan, getString(R.string.recommended_meal_plan), resources.getStringArray(R.array.forMyHealth)[2]),
            RVItem(R.drawable.vitamins, getString(R.string.vitamins), resources.getStringArray(R.array.forMyHealth)[3]))
        for (i in myHealthItems) {
            infAdapter.addInf(i)
        }
        binding.RVOnMyHealth.layoutManager = GridLayoutManager(activity as AppCompatActivity, 1)
        binding.RVOnMyHealth.adapter = infAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = MyHealth()
    }

    override fun onClick(healthItem: RVItem) {
        startActivity(Intent((activity as AppCompatActivity), ContentActivity::class.java).apply { putExtra("Cat", healthItem) })
    }
}