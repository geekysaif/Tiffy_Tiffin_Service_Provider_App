package com.example.demo.frag

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.demo.R
import com.example.demo.adapter.DummyAdapter
import com.example.demo.util.ActivityBase
import com.todkars.shimmer.ShimmerRecyclerView
import kotlinx.android.synthetic.main.fragment_favourate.*
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.recyclerView
import org.json.JSONArray
import org.json.JSONObject


class FavourateFragment : Fragment() {
    private var recyclerView: ShimmerRecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (requireActivity().isConnectedToNetwork()) {
            recyclerView = view.findViewById(R.id.recyclerView)
            get_tiffin()


            itemsswipetorefresh_favrt.setColorSchemeColors(Color.WHITE)
            itemsswipetorefresh_favrt.setOnRefreshListener {
                get_tiffin()
                itemsswipetorefresh_favrt.isRefreshing = false
            }
        } else {
            (requireActivity() as ActivityBase).no_internet_full_screen()
            (requireActivity() as ActivityBase).toasty_error(getString(R.string.internet_connection))
        }


    }


    /*method for get list */
    fun get_tiffin() {

        recyclerView!!.showShimmer()
        val jsonArray = JSONArray()

        val jsonObject1 = JSONObject()
        jsonObject1.put("name", "Saif Tiffin Service")
        jsonObject1.put("price", "Rs. 60")
        jsonObject1.put("rating", "4.3*")
        jsonObject1.put("time", "30 mins")
        jsonObject1.put("tif_img", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSDnO3fbH1X4w29Gc8fGpB8_KXOth9aeZ5FvyYPp3MGdC7DTmEW")

        val jsonObject2 = JSONObject()
        jsonObject2.put("name", "Vishal Tiffin Service")
        jsonObject2.put("price", "Rs. 50")
        jsonObject2.put("rating", "4.5*")
        jsonObject2.put("time", "40 mins")
        jsonObject2.put("tif_img", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSckEB9iCXpia56C0GwdFqI6P8Li6GegCsF6tAZYmbN1J-LsC_h")


        val jsonObject3 = JSONObject()
        jsonObject3.put("name", "Yash Tiffin Service")
        jsonObject3.put("price", "Rs. 55")
        jsonObject3.put("rating", "4.1*")
        jsonObject3.put("time", "45 mins")
        jsonObject3.put("tif_img", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQlzKFYsHu9v-i4V3Eh1oLIaWMHiJAjdA-DZpSi232Hx2Sbc13C")


        val jsonObject4 = JSONObject()
        jsonObject4.put("name", "Prashant Tiffin Service")
        jsonObject4.put("price", "Rs. 60")
        jsonObject4.put("rating", "4.3*")
        jsonObject4.put("time", "30 mins")
        jsonObject4.put("tif_img", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRyinu4XDvJPVbzprIi_2L3TtyOofweHKDuEaZSYrTFNSTRqyQ6")


        val jsonObject5 = JSONObject()
        jsonObject5.put("name", "Nitesh Tiffin Service")
        jsonObject5.put("price", "Rs. 60")
        jsonObject5.put("rating", "4.3*")
        jsonObject5.put("time", "30 mins")
        jsonObject5.put("tif_img", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSzGiSdOOxdEQyaSMQTAMBLiaUULWw-oeGw3tWZi2HW6pAJcuNa")

        val jsonObject6 = JSONObject()
        jsonObject6.put("name", "Amar Tiffin Service")
        jsonObject6.put("price", "Rs. 50")
        jsonObject6.put("rating", "4.5*")
        jsonObject6.put("time", "40 mins")
        jsonObject6.put("tif_img", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTS4TfeRYPTTGfoQ5ASYonmGR9rZkhlRT1hvGnn-AXiQxWaPCfh")

        jsonArray.put(jsonObject1)
        jsonArray.put(jsonObject2)
        jsonArray.put(jsonObject3)
        jsonArray.put(jsonObject4)
        jsonArray.put(jsonObject5)
        jsonArray.put(jsonObject6)

        recyclerView!!.adapter =
            DummyAdapter(jsonArray, context)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView!!.hideShimmer()
    }

    /*common method for check internet connection*/
    fun Context.isConnectedToNetwork(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting() ?: false
    }
}
