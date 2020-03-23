package com.example.demo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.demo.R
import com.example.demo.util.ActivityBase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : ActivityBase() {

    var etmobile: TextInputEditText? = null
    var etpassword: TextInputEditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Glide.with(applicationContext!!)
            .load(R.drawable.ic_login_graphic)
            .circleCrop()
            .error(R.drawable.logo)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.logo)
            .into(header)

        etmobile = findViewById<TextInputEditText>(R.id.et_mobile)
        etpassword = findViewById<TextInputEditText>(R.id.et_password)
        etmobile?.requestFocus()

        findViewById<LinearLayout>(R.id.btn_signup).setOnClickListener {
            val i = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(i)
            finish()
        }

        findViewById<TextView>(R.id.submit_login).setOnClickListener {

            validation()
        }

        findViewById<ImageView>(R.id.btn_back).setOnClickListener {

            finish()
        }
    }

    fun login(mobile: String, password: String) {
        toasty_success("Login Success...")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun validation() {
        val mob: String = etmobile?.text.toString()
        val pass = etpassword?.text.toString()

        if (mob.isBlank()) {
            etmobile?.requestFocus()
            etmobile?.error = "Mobile can't be null."
            toasty_error("Mobile can't be null")
            return
        }

        if (mob.length < 10) {
            etmobile?.requestFocus()
            etmobile?.error = "Mobile only can be 10 digits."
            toasty_error("Mobile only can be 10 digits.")
            return
        }

        if (pass.isBlank()) {
            etpassword?.requestFocus()
            etpassword?.error = "Password can't be null."
            toasty_error("Password can't be null")
            return
        }
        login(mob, pass)
    }
}
