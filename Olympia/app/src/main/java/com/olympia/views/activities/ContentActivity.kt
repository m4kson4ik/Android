package com.olympia.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.olympia.databinding.ActivityContentBinding
import com.olympia.forRecyclerViews.RVItem

class ContentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val item = intent.getSerializableExtra("Cat") as RVItem
        binding.ContentView.setImageResource(item.imageId)
        binding.TitleView.text = item.title
        binding.DescriptionText.text = item.description
        binding.BGoBackOnContent.setOnClickListener { finish() }
    }
}