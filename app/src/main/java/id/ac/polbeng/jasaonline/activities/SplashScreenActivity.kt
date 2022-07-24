package id.ac.polbeng.jasaonline.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import id.ac.polbeng.jasaonline.R
import id.ac.polbeng.jasaonline.helpers.Config

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, Config.SPLASH_TIME_OUT)
    }
}