package com.maungedev.eventtechorganizer.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.maungedev.eventtechorganizer.R
import com.maungedev.eventtechorganizer.constant.PageNameConstant.AUTHENTICATION_PAGE
import com.maungedev.eventtechorganizer.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(mainLooper).postDelayed({
            startActivity(Intent(this@SplashActivity, Class.forName(AUTHENTICATION_PAGE))).also{
                finishAffinity()
            }
        }, 1000)
    }
}