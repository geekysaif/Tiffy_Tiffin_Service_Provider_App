package com.example.demo.activity


import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.demo.R
import com.example.demo.frag.HomeFragment
import com.example.demo.util.ActivityBase
import org.json.JSONArray

class CartActivity : ActivityBase() {
    private var final_jsnarray: String = ""
    var jsonArray=JSONArray()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        final_jsnarray = intent.getStringExtra("final_jsonArray")

       jsonArray= JSONArray(final_jsnarray)
        toasty_success(""+jsonArray)

        findViewById<TextView>(R.id.btn_order_now).setOnClickListener {
            finish()
            val fragment: Fragment = HomeFragment()
            loadFragment(fragment)
        }
    }

    /*common method for load Fragment*/
    private fun loadFragment(fragment: Fragment) {

        val transaction = supportFragmentManager?.beginTransaction()
        transaction?.setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
        transaction?.replace(R.id.container, fragment)
        transaction?.commit()

    }
}
