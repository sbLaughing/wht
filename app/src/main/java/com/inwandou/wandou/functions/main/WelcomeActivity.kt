package com.inwandou.wandou.functions.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.inwandou.wandou.R

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this@WelcomeActivity,MainActivity::class.java))
        finish()
    }

}
