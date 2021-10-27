package com.maungedev.eventtechorganizer.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.maungedev.eventtechorganizer.R
import com.maungedev.eventtechorganizer.constant.PageNameConstant.AUTHENTICATION_PAGE
import com.maungedev.eventtechorganizer.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            observeUID()
        }, 1000L)

    }

    private fun observeUID() {
        if (isUserAlreadyHere()) {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java)).also {
                finishAffinity()
            }
        } else {
            startActivity(Intent(this@SplashActivity, Class.forName(AUTHENTICATION_PAGE))).also {
                finishAffinity()
            }
        }

    }

    private fun isUserAlreadyHere(): Boolean {
        val auth = Firebase.auth
        val uid = auth.currentUser?.uid
        if (uid != null) {
            return true
        }
        return false
    }
}