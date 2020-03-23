package com.example.demo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.demo.R
import com.example.demo.activity.TiffinDetailsActivity
import kotlinx.android.synthetic.main.items.view.*
import org.json.JSONArray
import java.net.URL

class DummyAdapter(val jsonArray: JSONArray, val context: Context?) :
    RecyclerView.Adapter<DummyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.items, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder?.v_name?.text = jsonArray.getJSONObject(position).getString("name")
        holder?.v_price?.text = jsonArray.getJSONObject(position).getString("price")
        holder?.v_rating?.text = jsonArray.getJSONObject(position).getString("rating")
        holder?.v_time?.text = jsonArray.getJSONObject(position).getString("time")

        Glide.with(context!!)
            .load(jsonArray.getJSONObject(position).getString("tif_img"))
          //  .circleCrop()
            .error(R.drawable.logo)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.logo)
            .into(holder?.v_tif_img)




        holder?.v_card_view.setOnClickListener {

            val i = Intent(context, TiffinDetailsActivity::class.java)
            i.putExtra("tif_img",jsonArray.getJSONObject(position).getString("tif_img"))
            context!!.startActivity(i)
        }


    }
    override fun getItemCount() = jsonArray.length()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val v_name = view.name
        val v_price = view.price
        val v_rating = view.rating
        val v_time = view.time
        val v_card_view = view.card_view
        val v_tif_img = view.tif_img

    }
}