package com.psm.tablelayout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class SignUpActivity:AppCompatActivity() {

    val EXTRA_TEXT_ADD = "logged"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        val button = findViewById<Button>(R.id.btnSignUp)
        button.setOnClickListener {

            val returnIntent = Intent()
            returnIntent.putExtra("result", EXTRA_TEXT_ADD)
            setResult(Activity.RESULT_OK, returnIntent)
            finish();


           /* val returnIntent = Intent()
            returnIntent.putExtra("result", "logged")
            setResult(Activity.RESULT_OK, returnIntent)*/

        }
    }
}