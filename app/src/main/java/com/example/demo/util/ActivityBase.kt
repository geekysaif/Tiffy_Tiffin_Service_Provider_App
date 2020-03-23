@file:Suppress("DEPRECATION")

package com.example.demo.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.example.demo.BuildConfig
import com.example.demo.R
import com.wessam.library.LayoutImage
import com.wessam.library.NoInternetLayout

open class ActivityBase : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    /*common method for load success Toast*/
    fun toasty_success(msg: String) {
        val myToast = Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT)
        myToast.setGravity(Gravity.CENTER, 0, 0)
        val view: View
        view = myToast.view
        view.setBackgroundResource(R.drawable.rectangle_green)
        myToast.show()
    }

    /*common method for load error Toast*/
    fun toasty_error(msg: String) {
        val myToast = Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT)
        myToast.setGravity(Gravity.CENTER, 0, 0)
        val view: View
        view = myToast.view
        view.setBackgroundResource(R.drawable.rectangle_red)
        myToast.show()
    }

    /*common method for load full screen*/
    fun full_screen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    /*common method for no_internet_full_screen*/
    fun no_internet_full_screen() {
        let {
            NoInternetLayout.Builder(it, R.layout.fragment_home).setImage(
                    LayoutImage.CLOUD)
                .animate()
        }

    }


    /*common method for load share*/
    fun share_app() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
            var shareMessage = "\nLet me recommend you this application for Uttar Pradesh Judicial Services Association.\n\n"
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: java.lang.Exception) { //e.toString();
        }
    }

    /*common method for load rate*/
    fun rate_app(){
        val uri = android.net.Uri.parse("market://details?id=" + applicationContext.packageName)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + applicationContext.packageName)
                )
            )
        }
    }


    /*common method for check internet connection*/
    fun Context.isConnectedToNetwork(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting ?: false
    }


}
