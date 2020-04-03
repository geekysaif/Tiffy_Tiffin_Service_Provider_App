package logic.mania.tiffy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_list_coupans.view.*
import logic.mania.tiffy.R
import logic.mania.tiffy.activity.CartActivity
import logic.mania.tiffy.activity.TiffinDetailsActivity
import logic.mania.tiffy.util.ActivityBase
import org.json.JSONArray

class CoupansAdapter(val jsonArray: JSONArray, val activity: CartActivity) :
    RecyclerView.Adapter<CoupansAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                activity
            ).inflate(R.layout.item_list_coupans, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder?.txt_coupan_name?.text = jsonArray.getJSONObject(position).getString("name")
        holder?.txt_coupan_details?.text =
            jsonArray.getJSONObject(position).getString("description")


        holder?.txt_coupan_apply.setOnClickListener {
            val prices: Int = Integer.parseInt(jsonArray.getJSONObject(position).getString("price"))
            val c_name: String = jsonArray.getJSONObject(position).getString("name")
            activity.get_coupans_details(prices,c_name)
        }

    }

    override fun getItemCount() = jsonArray.length()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txt_coupan_name = view.txt_coupan_name
        val txt_coupan_apply = view.txt_coupan_apply
        val txt_coupan_details = view.txt_coupan_details
    }



}