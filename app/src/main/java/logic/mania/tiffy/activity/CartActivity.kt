package logic.mania.tiffy.activity


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.todkars.shimmer.ShimmerRecyclerView
import logic.mania.tiffy.R

import logic.mania.tiffy.util.ActivityBase
import org.json.JSONArray
import org.json.JSONObject

class CartActivity : ActivityBase() {
    private var final_jsnarray: String = ""
    var jsonArray = JSONArray()
    var jsonObject_basic_details = JSONObject()
    var jsonObject_brkfast_details = JSONObject()
    var jsonObject_lunch_details = JSONObject()
    var jsonObject_dinner_details = JSONObject()

    var tiffy_service_provider_name: TextView? = null
    var tiffy_service_provider_contact: TextView? = null
    var tiffy_user_name: TextView? = null
    var edt_user_mobile: EditText? = null
    var u_contact: String = ""

    private var brkfastamount: Int = 0
    private var brkfast_final_amount: Int = 0
    private var brkfastcount: Int = 0
    var breakfast_count: TextView? = null
    var brkfast_amount: TextView? = null

    private var lunchamount: Int = 0
    private var lunch_final_amount: Int = 0
    private var lunchcount: Int = 0
    var lunch_count: TextView? = null
    var lunch_amount: TextView? = null

    private var dinneramount: Int = 0
    private var dinner_final_amount: Int = 0
    private var dinnercount: Int = 0
    var dinner_count: TextView? = null
    var dinner_amount: TextView? = null

    private var totalcount: Int = 0
    var total_count: TextView? = null

    private var total_brkfast_paid_amount: Int = 0
    private var total_lunch_paid_amount: Int = 0
    private var total_dinner_paid_amount: Int = 0

    private var total_paid_amount: Int = 0
    var txt_paid_amount: TextView? = null

    var recyclerView_coupans:ShimmerRecyclerView? =null
    var lyr_cupan:LinearLayout? =null
    var btn_coupans:LinearLayout? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        final_jsnarray = intent.getStringExtra("final_jsonArray")

        init()

        /*order now btn listner*/
        findViewById<TextView>(R.id.btn_order_now).setOnClickListener {
            openMainActivity()
        }

        /*change user order mobile*/
        edt_user_mobile?.setOnClickListener {
            edt_user_mobile?.isEnabled = true
            edt_user_mobile?.requestFocus()
        }


        /*back button*/
        findViewById<ImageView>(R.id.btn_back).setOnClickListener {

            finish()
        }

        /*coupans view cancel button*/
        findViewById<ImageView>(R.id.btn_cancel).setOnClickListener {

            lyr_cupan?.visibility = View.GONE
        }

        /*coupans view  button*/
        findViewById<LinearLayout>(R.id.btn_coupans).setOnClickListener {

            lyr_cupan?.visibility = View.VISIBLE
        }

        /*breakfast_counters*/
        findViewById<TextView>(R.id.breakfast_decrease).setOnClickListener {

            if (brkfastcount == 0) {
                toasty_error("Minimum number of item order is 0.")
            } else {
                brkfastcount--
                breakfast_count!!.text = brkfastcount.toString()

                brkfast_final_amount = brkfastamount * brkfastcount
                brkfast_amount?.text = brkfast_final_amount.toString()

                totalcount = brkfastcount + dinnercount + lunchcount
                total_count!!.text = "" + "Total Items:  " + totalcount.toString()
                toasty_success("" + brkfastcount + "  " + "Breakfast Added")

                total_brkfast_paid_amount=Integer.parseInt(brkfast_amount!!.text.toString())
                total_lunch_paid_amount=Integer.parseInt(lunch_amount!!.text.toString())
                total_dinner_paid_amount=Integer.parseInt(dinner_amount!!.text.toString())

                total_paid_amount =  total_brkfast_paid_amount + total_lunch_paid_amount + total_dinner_paid_amount
                txt_paid_amount!!.text = total_paid_amount.toString()

            }
        }
        findViewById<TextView>(R.id.breakfast_increase).setOnClickListener {
            brkfastcount++
            breakfast_count!!.text = brkfastcount.toString()
            brkfast_final_amount = brkfastamount * brkfastcount
            brkfast_amount?.text = brkfast_final_amount.toString()

            totalcount = brkfastcount + dinnercount + lunchcount
            total_count!!.text = "Total Items:  $totalcount"

            toasty_success("" + brkfastcount + "  " + "Breakfast Added")
            total_brkfast_paid_amount=Integer.parseInt(brkfast_amount!!.text.toString())
            total_lunch_paid_amount=Integer.parseInt(lunch_amount!!.text.toString())
            total_dinner_paid_amount=Integer.parseInt(dinner_amount!!.text.toString())

            total_paid_amount =  total_brkfast_paid_amount + total_lunch_paid_amount + total_dinner_paid_amount
            txt_paid_amount!!.text = total_paid_amount.toString()
        }


        /*lunch_counters*/
        findViewById<TextView>(R.id.lunch_decrease).setOnClickListener {

            if (lunchcount == 0) {
                toasty_error("Minimum number of item order is 0.")
            } else {
                lunchcount--
                lunch_count!!.text = lunchcount.toString()

                lunch_final_amount = lunchamount * lunchcount
                lunch_amount?.text = lunch_final_amount.toString()

                totalcount = brkfastcount + dinnercount + lunchcount
                total_count!!.text = "" + "Total Items:  " + totalcount.toString()
                toasty_success("" + lunchcount + "  " + "Lunch Added")

                total_brkfast_paid_amount=Integer.parseInt(brkfast_amount!!.text.toString())
                total_lunch_paid_amount=Integer.parseInt(lunch_amount!!.text.toString())
                total_dinner_paid_amount=Integer.parseInt(dinner_amount!!.text.toString())

                total_paid_amount =  total_brkfast_paid_amount + total_lunch_paid_amount + total_dinner_paid_amount
                txt_paid_amount!!.text = total_paid_amount.toString()
            }
        }
        findViewById<TextView>(R.id.lunch_increase).setOnClickListener {
            lunchcount++
            lunch_count!!.text = lunchcount.toString()
            lunch_final_amount = lunchamount * lunchcount
            lunch_amount?.text = lunch_final_amount.toString()

            totalcount = brkfastcount + dinnercount + lunchcount
            total_count!!.text = "Total Items:  $totalcount"

            toasty_success("" + lunchcount + "  " + "Lunch Added")

            total_brkfast_paid_amount=Integer.parseInt(brkfast_amount!!.text.toString())
            total_lunch_paid_amount=Integer.parseInt(lunch_amount!!.text.toString())
            total_dinner_paid_amount=Integer.parseInt(dinner_amount!!.text.toString())

            total_paid_amount =  total_brkfast_paid_amount + total_lunch_paid_amount + total_dinner_paid_amount
            txt_paid_amount!!.text = total_paid_amount.toString()
        }


        /*dinner_counters*/
        findViewById<TextView>(R.id.dinner_decrease).setOnClickListener {

            if (dinnercount == 0) {
                toasty_error("Minimum number of item order is 0.")
            } else {
                dinnercount--
                dinner_count!!.text = dinnercount.toString()

                dinner_final_amount = dinneramount * dinnercount
                dinner_amount?.text = dinner_final_amount.toString()

                totalcount = brkfastcount + dinnercount + lunchcount
                total_count!!.text = "" + "Total Items:  " + totalcount.toString()
                toasty_success("" + dinnercount + "  " + "Dinner Added")
                total_brkfast_paid_amount=Integer.parseInt(brkfast_amount!!.text.toString())
                total_lunch_paid_amount=Integer.parseInt(lunch_amount!!.text.toString())
                total_dinner_paid_amount=Integer.parseInt(dinner_amount!!.text.toString())

                total_paid_amount =  total_brkfast_paid_amount + total_lunch_paid_amount + total_dinner_paid_amount
                txt_paid_amount!!.text = total_paid_amount.toString()
            }
        }
        findViewById<TextView>(R.id.dinner_increase).setOnClickListener {
            dinnercount++
            dinner_count!!.text = dinnercount.toString()
            dinner_final_amount = dinneramount * dinnercount
            dinner_amount?.text = dinner_final_amount.toString()

            totalcount = brkfastcount + dinnercount + lunchcount
            total_count!!.text = "Total Items:  $totalcount"

            toasty_success("" + dinnercount + "  " + "Dinner Added")
            total_brkfast_paid_amount=Integer.parseInt(brkfast_amount!!.text.toString())
            total_lunch_paid_amount=Integer.parseInt(lunch_amount!!.text.toString())
            total_dinner_paid_amount=Integer.parseInt(dinner_amount!!.text.toString())

            total_paid_amount =  total_brkfast_paid_amount + total_lunch_paid_amount + total_dinner_paid_amount
            txt_paid_amount!!.text = total_paid_amount.toString()
        }

        set_basic_details(jsonObject_basic_details)
    }

    /*initilization of views*/
    fun init() {
        jsonArray = JSONArray(final_jsnarray)
        jsonObject_basic_details = jsonArray.getJSONObject(0)
        jsonObject_brkfast_details = jsonArray.getJSONObject(1)
        jsonObject_lunch_details = jsonArray.getJSONObject(2)
        jsonObject_dinner_details = jsonArray.getJSONObject(3)

        tiffy_service_provider_contact = findViewById(R.id.tiffy_service_provider_contact)
        tiffy_service_provider_name = findViewById(R.id.tiffy_service_provider_name)
        tiffy_user_name = findViewById(R.id.tiffy_user_name)
        edt_user_mobile = findViewById(R.id.edt_user_mobile)

        total_count = findViewById(R.id.total_count)
        breakfast_count = findViewById(R.id.breakfast_count)
        brkfast_amount = findViewById(R.id.brkfast_amount)

        lunch_count = findViewById(R.id.lunch_count)
        lunch_amount = findViewById(R.id.lunch_amount)

        dinner_count = findViewById(R.id.dinner_count)
        dinner_amount = findViewById(R.id.dinner_amount)

        txt_paid_amount = findViewById(R.id.txt_paid_amount)
        recyclerView_coupans = findViewById(R.id.recyclerView_coupans)
        lyr_cupan = findViewById(R.id.lyr_cupan)
        btn_coupans = findViewById(R.id.btn_coupans)
    }

    /*open main activity*/
    private fun openMainActivity() {
        intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /*set basic detais*/
    fun set_basic_details(jsonObject: JSONObject) {
        u_contact = jsonObject.getString("tiffy_user_contact").toString()
        tiffy_service_provider_contact?.text =
            jsonObject.getString("tiffy_service_provider_contact").toString()
        tiffy_service_provider_name?.text =
            jsonObject.getString("tiffy_service_provider_name").toString()
        tiffy_user_name?.text = jsonObject.getString("tiffy_user_name").toString()
        edt_user_mobile?.text = Editable.Factory.getInstance().newEditable(u_contact)
        total_count!!.text = "" + "Total Items:  " + jsonObject.getString("totalcount").toString()
        set_brkfast_details(jsonObject_brkfast_details)
    }

    /*set brkfast detais*/
    fun set_brkfast_details(jsonObject1: JSONObject) {

        if (jsonObject1.length().equals(0)){
            set_lunch_details(jsonObject_lunch_details)
        }else{
            brkfastamount = jsonObject1.getInt("brkfast_amount")
            brkfastcount = jsonObject1.getInt("brkfastcount")
            breakfast_count?.text = Editable.Factory.getInstance().newEditable(brkfastcount.toString())

            brkfast_final_amount = brkfastamount * brkfastcount
            brkfast_amount?.text = brkfast_final_amount.toString()

            set_lunch_details(jsonObject_lunch_details)
        }

    }

    /*set lunch detais*/
    fun set_lunch_details(jsonObject2: JSONObject) {

        if (jsonObject2.length().equals(0)){
            set_dinner_details(jsonObject_dinner_details)
        }else{
            lunchamount = jsonObject2.getInt("lunch_amount")
            lunchcount = jsonObject2.getInt("lunchcount")
            lunch_count?.text = Editable.Factory.getInstance().newEditable(lunchcount.toString())

            lunch_final_amount = lunchamount * lunchcount
            lunch_amount?.text = lunch_final_amount.toString()
            set_dinner_details(jsonObject_dinner_details)
        }
    }

    /*set dinner detais*/
    fun set_dinner_details(jsonObject3: JSONObject) {

        if (jsonObject3.length().equals(0)){
        }else{
            dinneramount = jsonObject3.getInt("dinner_amount")
            dinnercount = jsonObject3.getInt("dinnercount")
            dinner_count?.text = Editable.Factory.getInstance().newEditable(dinnercount.toString())

            dinner_final_amount = dinneramount * dinnercount
            dinner_amount?.text = dinner_final_amount.toString()

            total_brkfast_paid_amount=Integer.parseInt(brkfast_amount!!.text.toString())
            total_lunch_paid_amount=Integer.parseInt(lunch_amount!!.text.toString())
            total_dinner_paid_amount=Integer.parseInt(dinner_amount!!.text.toString())

            total_paid_amount =  total_brkfast_paid_amount + total_lunch_paid_amount + total_dinner_paid_amount
            txt_paid_amount!!.text = total_paid_amount.toString()
        }


    }

}
