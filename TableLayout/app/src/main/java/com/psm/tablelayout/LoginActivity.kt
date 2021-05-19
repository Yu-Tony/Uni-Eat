package com.psm.tablelayout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginActivity: AppCompatActivity() {

    val EXTRA_TEXT_ADD = "logged"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val button = findViewById<Button>(R.id.btnLogin)
        button.setOnClickListener {
            val returnIntent = Intent()
            returnIntent.putExtra("result", EXTRA_TEXT_ADD)
            setResult(Activity.RESULT_OK, returnIntent)
            finish();
        }
    }

}