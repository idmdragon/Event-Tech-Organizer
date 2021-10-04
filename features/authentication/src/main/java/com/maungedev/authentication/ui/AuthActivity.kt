package com.maungedev.authentication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maungedev.authentication.R
import com.maungedev.authentication.databinding.ActivityAuthBinding
import com.maungedev.authentication.ui.register.RegisterFragment

class AuthActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFragment()
    }

    private fun setFragment() {
        val mFragmentManager = supportFragmentManager
        val mRegisterFragment = RegisterFragment()
        val fragment = mFragmentManager.findFragmentByTag(RegisterFragment::class.java.simpleName)

        if (fragment !is RegisterFragment) {
            mFragmentManager
                .beginTransaction()
                .add(R.id.frame_container, mRegisterFragment, RegisterFragment::class.java.simpleName)
                .commit()
        }
    }
}