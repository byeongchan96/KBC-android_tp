package com.example.intravel


import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.intravel.adapter.DetaiTabFragmentAdapter


import com.example.intravel.databinding.ActivitySubmainBinding

import com.google.android.material.tabs.TabLayoutMediator



class DetailMainActivity : AppCompatActivity() {

  private lateinit var binding: ActivitySubmainBinding
//  private lateinit var viewPager2Adapter: MyFragmentAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)


    // View binding setup
    binding = ActivitySubmainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tablayout)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.right, systemBars.top, systemBars.bottom)
      insets
    }

    // MyFragmentAdapter 설정
    val viewPager2Adapter = DetaiTabFragmentAdapter(this)
    binding.viewpager2.adapter = viewPager2Adapter

    val tabElement: List<String> = mutableListOf("To-Do", "Memo", "Menu")


    // TabLayout과 ViewPager2 연결
    try {
      TabLayoutMediator(binding.tablayout, binding.viewpager2) { tab, position ->
        val textView = TextView(this@DetailMainActivity)
        textView.text = tabElement[position]
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.setTypeface(textView.typeface, Typeface.BOLD)
        tab.customView = textView
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
      }.attach()
    } catch (e: Exception) {
      Log.e("TabLayoutError", "Error in TabLayoutMediator: ${e.message}")
    }
    // Button event for navigating back to MainActivity
    binding.iconLeft.setOnClickListener {
      navigateToMainActivity()
    }

    // Button event for navigating to MainActivity_memowrite
    binding.iconRight.setOnClickListener {
      navigateToMemoWriteActivity()
    }
  }

  // Navigate to MainActivity
  private fun navigateToMainActivity() {
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
  }

  // Navigate to MainActivity_memowrite
  private fun navigateToMemoWriteActivity() {
    val intent = Intent(this, MainActivity_memowrite::class.java)
    startActivity(intent)
    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
  }
}