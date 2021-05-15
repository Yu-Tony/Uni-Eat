package com.psm.tablelayout.Profile

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.psm.tablelayout.R

class MyEdit:  AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_editar)

        val button = findViewById<Button>(R.id.btnCancelProfile)
        button.setOnClickListener {
            finish();
        }
    }




}