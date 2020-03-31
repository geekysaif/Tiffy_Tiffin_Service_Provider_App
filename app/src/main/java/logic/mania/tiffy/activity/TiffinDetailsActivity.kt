package logic.mania.tiffy.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

import logic.mania.tiffy.adapter.BreakFastAdapter
import logic.mania.tiffy.util.ActivityBase
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.todkars.shimmer.ShimmerRecyclerView
import kotlinx.android.synthetic.main.activity_tiffin_details.*
import logic.mania.tiffy.R
import org.json.JSONArray
import org.json.JSONObject

class TiffinDetailsActivity : ActivityBase() {

    private lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>
    private var url: String = ""

    private var recyclerViewbreakfast: ShimmerRecyclerView? = null
    private var recyclerViewlunch: ShimmerRecyclerView? = null
    private var recyclerViewdinner: ShimmerRecyclerView? = null

    private var bottomSheetBehavior: LinearLayout? = null

    private var brkfastcount: Int = 0
    var breakfast_count: TextView? = null

    private var lunchcount: Int = 0
    var lunch_count: TextView? = null

    private var dinnercount: Int = 0
    var dinner_count: TextView? = null

    private var totalcount: Int = 0
    var total_count: TextView? = null

    private var mobile_number: String = "+91 8840133***"

    private var tiffy_service_provider_id: String = ""


    val jsonObject_basic_details = JSONObject()
    val jsonObject_breakfast = JSONObject()
    val jsonObject_lunch = JSONObject()
    val jsonObject_dinner = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiffin_details)

        bottomSheetBehavior = findViewById(R.id.bottom_sheet)
        recyclerViewbreakfast = findViewById(R.id.recyclerView_breakfast)
        recyclerViewlunch = findViewById(R.id.recyclerView_lunch)
        recyclerViewdinner = findViewById(R.id.recyclerView_dinner)
        total_count = findViewById(R.id.total_count)



        /*show image*/
        url = intent.getStringExtra("tif_img")
        Glide.with(applicationContext!!)
            .load(url)
            //  .circleCrop()
            .error(R.drawable.logo)
            .diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.logo)
            .into(header)

        breakfast_count = findViewById<TextView>(R.id.breakfast_count)
        breakfast_count!!.text = brkfastcount.toString()

        lunch_count = findViewById<TextView>(R.id.lunch_count)
        lunch_count!!.text = lunchcount.toString()

        dinner_count = findViewById<TextView>(R.id.dinner_count)
        dinner_count!!.text = dinnercount.toString()

        get_breakfast()

        /*menu button*/
        findViewById<LinearLayout>(R.id.btn_menu).setOnClickListener {
            if (bottom_sheet.visibility == View.VISIBLE) {
                bottom_sheet.visibility = View.GONE

                Glide.with(applicationContext!!)
                    .load(R.drawable.ic_menu)
                    //  .circleCrop()
                    .error(R.drawable.ic_menu)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.ic_menu)
                    .into(img_menu)
            } else {
                Glide.with(applicationContext!!)
                    .load(R.drawable.ic_cancel)
                    //  .circleCrop()
                    .error(R.drawable.ic_cancel)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.ic_cancel)
                    .into(img_menu)
                bottom_sheet.visibility = View.VISIBLE
            }
        }

        /*call button*/
        findViewById<LinearLayout>(R.id.btn_call).setOnClickListener {
            toasty_error("Call is on going...")
            checkPermission()
        }

        /*hide menu button*/
        findViewById<ImageView>(R.id.btn_cancel).setOnClickListener {

            bottom_sheet.visibility = View.GONE
            Glide.with(applicationContext!!)
                .load(R.drawable.ic_menu)
                //  .circleCrop()
                .error(R.drawable.ic_menu)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_menu)
                .into(img_menu)
        }

        /*back button*/
        findViewById<ImageView>(R.id.btn_back).setOnClickListener {

            finish()
        }

        /*breakfast_counters*/
        findViewById<TextView>(R.id.breakfast_decrease).setOnClickListener {

            if (brkfastcount == 0) {
                toasty_error("Minimum number of item order is 0.")
            } else {
                brkfastcount--
                breakfast_count!!.text = brkfastcount.toString()

                totalcount = brkfastcount + dinnercount + lunchcount
                total_count!!.text = "" + "Total Items:  " + totalcount.toString()
                jsonObject_breakfast.put("brkfastcount", brkfastcount)
                jsonObject_breakfast.put("brkfast_amount", "60")

                toasty_success("" + brkfastcount + "  " + "Breakfast Added")
            }
        }
        findViewById<TextView>(R.id.breakfast_increase).setOnClickListener {
            brkfastcount++
            breakfast_count!!.text = brkfastcount.toString()
            totalcount = brkfastcount + dinnercount + lunchcount
            total_count!!.text = "Total Items:  $totalcount"
            jsonObject_breakfast.put("brkfastcount", brkfastcount)
            jsonObject_breakfast.put("brkfast_amount", "60")
            toasty_success("" + brkfastcount + "  " + "Breakfast Added")
        }

        /*lunch_counters*/
        findViewById<TextView>(R.id.lunch_decrease).setOnClickListener {

            if (lunchcount == 0) {

                toasty_error("Minimum number of item order is 0.")
            } else {
                lunchcount--
                lunch_count!!.text = lunchcount.toString()
                totalcount = brkfastcount + dinnercount + lunchcount
                total_count!!.text = "" + "Total Items:  " + totalcount.toString()
                jsonObject_lunch.put("lunchcount", lunchcount)
                jsonObject_lunch.put("lunch_amount", "70")

                toasty_success("" + lunchcount + "  " + "Lunch Added")
            }
        }
        findViewById<TextView>(R.id.lunch_increase).setOnClickListener {
            lunchcount++
            lunch_count!!.text = lunchcount.toString()
            totalcount = brkfastcount + dinnercount + lunchcount
            total_count!!.text = "" + "Total Items:  " + totalcount.toString()
            jsonObject_lunch.put("lunchcount", lunchcount)
            jsonObject_lunch.put("lunch_amount", "70")

            toasty_success("" + lunchcount + "  " + "Lunch Added")
        }

        /*dinner_counters*/
        findViewById<TextView>(R.id.dinner_decrease).setOnClickListener {

            if (dinnercount == 0) {

                toasty_error("Minimum number of item order is 0.")
            } else {
                dinnercount--
                dinner_count!!.text = dinnercount.toString()
                totalcount = brkfastcount + lunchcount + dinnercount
                total_count!!.text = "" + "Total Items:  " + totalcount.toString()
                jsonObject_dinner.put("dinnercount", dinnercount)
                jsonObject_dinner.put("dinner_amount", "80")

                toasty_success("" + dinnercount + "  " + "Dinner Added")
            }
        }
        findViewById<TextView>(R.id.dinner_increase).setOnClickListener {
            dinnercount++
            dinner_count!!.text = dinnercount.toString()
            totalcount = brkfastcount + lunchcount + dinnercount
            total_count!!.text = "" + "Total Items:  " + totalcount.toString()
            jsonObject_dinner.put("dinnercount", dinnercount)
            jsonObject_dinner.put("dinner_amount", "80")

            toasty_success("" + dinnercount + "  " + "Dinner Added")
        }


        /*visibility breakfast menu*/
        findViewById<TextView>(R.id.txt_breakfast).setOnClickListener {

            if (lyr_brkfast.visibility == View.VISIBLE) {
                lyr_brkfast.visibility = View.GONE
            } else {
                lyr_brkfast.visibility = View.VISIBLE
                lyr_lunch.visibility = View.GONE
                lyr_dinner.visibility = View.GONE
            }
        }
        /*visibility lunch menu*/
        findViewById<TextView>(R.id.txt_lunch).setOnClickListener {

            if (lyr_lunch.visibility == View.VISIBLE) {
                lyr_lunch.visibility = View.GONE
            } else {
                lyr_lunch.visibility = View.VISIBLE
                lyr_brkfast.visibility = View.GONE
                lyr_dinner.visibility = View.GONE

            }
        }
        /*visibility lunch dinner*/
        findViewById<TextView>(R.id.txt_dinner).setOnClickListener {

            if (lyr_dinner.visibility == View.VISIBLE) {
                lyr_dinner.visibility = View.GONE
            } else {
                lyr_dinner.visibility = View.VISIBLE
                lyr_brkfast.visibility = View.GONE
                lyr_lunch.visibility = View.GONE
            }
        }


        /*txt_add_cart*/
        findViewById<TextView>(R.id.txt_add_cart).setOnClickListener {
            add_to_cart()
        }
    }

    /*add to cart*/
    fun add_to_cart() {
        tiffy_service_provider_id = "5"
        jsonObject_basic_details.put("tiffy_service_provider_id", tiffy_service_provider_id)
        jsonObject_basic_details.put("tiffy_service_provider_name", "XYZ Tiffin Services")
        jsonObject_basic_details.put("tiffy_service_provider_contact", "8840133***")
        jsonObject_basic_details.put(
            "customer_address",
            "D-1167, Block-D, Indira Nagar, Lucknow, Uttar Pradesh, India"
        )
        jsonObject_basic_details.put("tiffy_user_name", "Mohd Saif Alam")
        jsonObject_basic_details.put("tiffy_user_contact", "9793612***")
        jsonObject_basic_details.put("totalcount", totalcount)


        val final_jsonArray = JSONArray()
        final_jsonArray.put(0, jsonObject_basic_details)
        final_jsonArray.put(1, jsonObject_breakfast)
        final_jsonArray.put(2, jsonObject_lunch)
        final_jsonArray.put(3, jsonObject_dinner)

        if (totalcount == 0) {
            toasty_error("Please first add items in cart")
            return
        } else {
            val intent = Intent(applicationContext, CartActivity::class.java)
            intent.putExtra("final_jsonArray", "" + final_jsonArray)
            startActivity(intent)
        }

    }

    /*method for get list */
    fun get_breakfast() {

        recyclerViewbreakfast!!.showShimmer()
        val jsonArray = JSONArray()

        jsonObject_breakfast.put("brkfastcount", "0")
        jsonObject_breakfast.put("brkfast_amount", "60")


        val jsonObject1 = JSONObject()
        jsonObject1.put("name", "Poori Sbji")
        jsonObject1.put("day", "Monday")
        jsonObject1.put(
            "tif_img",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSDnO3fbH1X4w29Gc8fGpB8_KXOth9aeZ5FvyYPp3MGdC7DTmEW"
        )

        val jsonObject2 = JSONObject()
        jsonObject2.put("name", "Cholley Bathure")
        jsonObject2.put("day", "Tuesday")
        jsonObject2.put(
            "tif_img",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSckEB9iCXpia56C0GwdFqI6P8Li6GegCsF6tAZYmbN1J-LsC_h"
        )


        val jsonObject3 = JSONObject()
        jsonObject3.put("name", "Aalu Paratha")
        jsonObject3.put("day", "Wednesday")
        jsonObject3.put(
            "tif_img",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQlzKFYsHu9v-i4V3Eh1oLIaWMHiJAjdA-DZpSi232Hx2Sbc13C"
        )


        val jsonObject4 = JSONObject()
        jsonObject4.put("name", "Poha")
        jsonObject4.put("day", "Thursday")
        jsonObject4.put(
            "tif_img",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRyinu4XDvJPVbzprIi_2L3TtyOofweHKDuEaZSYrTFNSTRqyQ6"
        )


        val jsonObject5 = JSONObject()
        jsonObject5.put("name", "Gobhi Paratha")
        jsonObject5.put("day", "Friday")
        jsonObject5.put(
            "tif_img",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSzGiSdOOxdEQyaSMQTAMBLiaUULWw-oeGw3tWZi2HW6pAJcuNa"
        )

        val jsonObject6 = JSONObject()
        jsonObject6.put("name", "Kheer Poori")
        jsonObject6.put("day", "Saturday")
        jsonObject6.put(
            "tif_img",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTS4TfeRYPTTGfoQ5ASYonmGR9rZkhlRT1hvGnn-AXiQxWaPCfh"
        )

        jsonArray.put(jsonObject1)
        jsonArray.put(jsonObject2)
        jsonArray.put(jsonObject3)
        jsonArray.put(jsonObject4)
        jsonArray.put(jsonObject5)
        jsonArray.put(jsonObject6)

        recyclerViewbreakfast!!.adapter =
            BreakFastAdapter(
                jsonArray,
                applicationContext
            )
        recyclerViewbreakfast!!.layoutManager = LinearLayoutManager(applicationContext)
        recyclerViewbreakfast!!.hideShimmer()
        get_lunch()
    }

    /*method for get list */
    fun get_lunch() {

        recyclerViewlunch!!.showShimmer()
        val jsonArray = JSONArray()

        jsonObject_lunch.put("lunchcount", "0")
        jsonObject_lunch.put("lunch_amount", "70")


        val jsonObject1 = JSONObject()
        jsonObject1.put("name", "Daal/Chawal/Roti/Seasonal Sabji/Papad/Salad")
        jsonObject1.put("day", "Monday")
        jsonObject1.put(
            "tif_img",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSDnO3fbH1X4w29Gc8fGpB8_KXOth9aeZ5FvyYPp3MGdC7DTmEW"
        )

        val jsonObject2 = JSONObject()
        jsonObject2.put("name", "Daal/Chawal/Roti/Seasonal Sabji/Papad/Curd")
        jsonObject2.put("day", "Tuesday")
        jsonObject2.put(
            "tif_img",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSckEB9iCXpia56C0GwdFqI6P8Li6GegCsF6tAZYmbN1J-LsC_h"
        )


        val jsonObject3 = JSONObject()
        jsonObject3.put("name", "Daal/Chawal/Roti/Seasonal Sabji/Papad/Salad")
        jsonObject3.put("day", "Wednesday")
        jsonObject3.put(
            "tif_img",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQlzKFYsHu9v-i4V3Eh1oLIaWMHiJAjdA-DZpSi232Hx2Sbc13C"
        )


        val jsonObject4 = JSONObject()
        jsonObject4.put("name", "Daal/Fried Rice/Roti/Rajma/Papad/Salad")
        jsonObject4.put("day", "Thursday")
        jsonObject4.put(
            "tif_img",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRyinu4XDvJPVbzprIi_2L3TtyOofweHKDuEaZSYrTFNSTRqyQ6"
        )


        val jsonObject5 = JSONObject()
        jsonObject5.put("name", "Daal/Pulav/Roti/Chicken/Papad/Salad")
        jsonObject5.put("day", "Friday")
        jsonObject5.put(
            "tif_img",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSzGiSdOOxdEQyaSMQTAMBLiaUULWw-oeGw3tWZi2HW6pAJcuNa"
        )

        val jsonObject6 = JSONObject()
        jsonObject6.put("name", "Daal/Chawal/Roti/Seasonal Sabji/Papad/Salad")
        jsonObject6.put("day", "Saturday")
        jsonObject6.put(
            "tif_img",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTS4TfeRYPTTGfoQ5ASYonmGR9rZkhlRT1hvGnn-AXiQxWaPCfh"
        )

        jsonArray.put(jsonObject1)
        jsonArray.put(jsonObject2)
        jsonArray.put(jsonObject3)
        jsonArray.put(jsonObject4)
        jsonArray.put(jsonObject5)
        jsonArray.put(jsonObject6)

        recyclerViewlunch!!.adapter =
            BreakFastAdapter(
                jsonArray,
                applicationContext
            )
        recyclerViewlunch!!.layoutManager = LinearLayoutManager(applicationContext)

        recyclerViewlunch!!.hideShimmer()
        get_dinner()
    }

    /*method for get list */
    fun get_dinner() {

        recyclerViewdinner!!.showShimmer()
        val jsonArray = JSONArray()

        jsonObject_dinner.put("dinnercount", "0")
        jsonObject_dinner.put("dinner_amount", "80")

        val jsonObject1 = JSONObject()
        jsonObject1.put("name", "Daal/Chawal/Roti/Seasonal Sabji/Papad/Salad")
        jsonObject1.put("day", "Monday")
        jsonObject1.put(
            "tif_img",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSDnO3fbH1X4w29Gc8fGpB8_KXOth9aeZ5FvyYPp3MGdC7DTmEW"
        )

        val jsonObject2 = JSONObject()
        jsonObject2.put("name", "Daal/Chawal/Roti/Seasonal Sabji/Papad/Curd")
        jsonObject2.put("day", "Tuesday")
        jsonObject2.put(
            "tif_img",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSckEB9iCXpia56C0GwdFqI6P8Li6GegCsF6tAZYmbN1J-LsC_h"
        )


        val jsonObject3 = JSONObject()
        jsonObject3.put("name", "Daal/Chawal/Roti/Seasonal Sabji/Papad/Salad")
        jsonObject3.put("day", "Wednesday")
        jsonObject3.put(
            "tif_img",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQlzKFYsHu9v-i4V3Eh1oLIaWMHiJAjdA-DZpSi232Hx2Sbc13C"
        )


        val jsonObject4 = JSONObject()
        jsonObject4.put("name", "Daal/Fried Rice/Roti/Rajma/Papad/Salad")
        jsonObject4.put("day", "Thursday")
        jsonObject4.put(
            "tif_img",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRyinu4XDvJPVbzprIi_2L3TtyOofweHKDuEaZSYrTFNSTRqyQ6"
        )


        val jsonObject5 = JSONObject()
        jsonObject5.put("name", "Daal/Pulav/Roti/Chicken/Papad/Salad")
        jsonObject5.put("day", "Friday")
        jsonObject5.put(
            "tif_img",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSzGiSdOOxdEQyaSMQTAMBLiaUULWw-oeGw3tWZi2HW6pAJcuNa"
        )

        val jsonObject6 = JSONObject()
        jsonObject6.put("name", "Daal/Chawal/Roti/Seasonal Sabji/Papad/Salad")
        jsonObject6.put("day", "Saturday")
        jsonObject6.put(
            "tif_img",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTS4TfeRYPTTGfoQ5ASYonmGR9rZkhlRT1hvGnn-AXiQxWaPCfh"
        )

        jsonArray.put(jsonObject1)
        jsonArray.put(jsonObject2)
        jsonArray.put(jsonObject3)
        jsonArray.put(jsonObject4)
        jsonArray.put(jsonObject5)
        jsonArray.put(jsonObject6)

        recyclerViewdinner!!.adapter =
            BreakFastAdapter(
                jsonArray,
                applicationContext
            )
        recyclerViewdinner!!.layoutManager = LinearLayoutManager(applicationContext)
        recyclerViewdinner!!.hideShimmer()
    }

    /*call permission*/
    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CALL_PHONE
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42
                )
            }
        } else {
            // Permission has already been granted
            callPhone()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == 42) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted, yay!
                callPhone()
            } else {
                // permission denied, boo! Disable the
                // functionality
            }
            return
        }
    }

    fun callPhone() {

        /*for open directly call
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "8840133749"))*/

        /*for open dialer*/
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile_number))
        startActivity(intent)
    }


}
