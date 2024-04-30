package com.example.aston_intensiv_final_project.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.airbnb.lottie.LottieAnimationView
import com.example.aston_intensiv_final_project.R
import java.util.Timer
import kotlin.concurrent.schedule

class LottieAnimationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie_animation)
        splashScreen.setOnExitAnimationListener { vp ->
            val lottieView = findViewById<LottieAnimationView>(R.id.animationView)
            lottieView.enableMergePathsForKitKatAndAbove(true)

            lottieView.post {
                vp.view.alpha = 0f
                vp.iconView.alpha = 0f
                lottieView!!.playAnimation()
            }
        }

        Timer().schedule(100) {
            val intent = Intent(this@LottieAnimationActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}