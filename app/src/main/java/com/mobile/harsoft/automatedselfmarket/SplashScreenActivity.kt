package com.mobile.harsoft.automatedselfmarket

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.mobile.harsoft.automatedselfmarket.util.PreferenceHelper

class SplashScreenActivity : AppCompatActivity() {

    private var preferenceHelper: PreferenceHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        preferenceHelper = PreferenceHelper(this)
        Handler().postDelayed(object : Thread() {
            override fun run() {
                if (preferenceHelper!!.getIntro().equals("no")) {
                    val intent = Intent(baseContext, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    preferenceHelper!!.putIntro("")
                    val intent = Intent(baseContext, IntroSliderActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            }
        }, 3000)
    }
}
