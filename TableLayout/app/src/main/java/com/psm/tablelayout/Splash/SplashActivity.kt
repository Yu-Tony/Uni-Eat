package com.psm.tablelayout.Splash

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.psm.tablelayout.MainActivity
import com.psm.tablelayout.R
import kotlinx.android.synthetic.main.splash_screen.*

class SplashActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.splash_screen)
        imageCoffee.alpha=0f
        imageCoffee.animate().setDuration(5000).alpha(1f).withEndAction()
        {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

    }
}