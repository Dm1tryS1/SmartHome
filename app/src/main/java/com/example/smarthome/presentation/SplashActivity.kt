package com.example.smarthome.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.smarthome.R
import com.example.smarthome.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animation = AnimationUtils.loadAnimation(this, R.anim.anim_launch)
        animation.duration = 2000
        animation.fillAfter = true
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.title.isVisible = true
                YoYo.with(Techniques.BounceInUp).duration(1000).repeat(0).onEnd {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    overridePendingTransition(
                        org.koin.android.R.anim.abc_fade_in,
                        org.koin.android.R.anim.abc_fade_out
                    )
                    this@SplashActivity.finish()
                }.playOn(binding.title)
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })

        binding.icon.startAnimation(animation)
    }
}