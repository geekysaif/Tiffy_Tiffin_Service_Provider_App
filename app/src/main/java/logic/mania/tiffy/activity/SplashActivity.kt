package logic.mania.tiffy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

import kotlinx.android.synthetic.main.activity_splash.*
import logic.mania.tiffy.R

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long= 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        },SPLASH_TIME_OUT)
    }

    override fun onStop() {
        super.onStop()
        rainview.animationClear()
    }
}
