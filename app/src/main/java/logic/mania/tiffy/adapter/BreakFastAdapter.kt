package logic.mania.tiffy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.items.view.name
import kotlinx.android.synthetic.main.items.view.tif_img
import kotlinx.android.synthetic.main.items_brkfast.view.*
import logic.mania.tiffy.R
import org.json.JSONArray

class BreakFastAdapter(val jsonArray: JSONArray, val context: Context?) :
    RecyclerView.Adapter<BreakFastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.items_brkfast, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder?.v_name?.text = jsonArray.getJSONObject(position).getString("name")
        holder?.v_day?.text = jsonArray.getJSONObject(position).getString("day")

        Glide.with(context!!)
            .load(jsonArray.getJSONObject(position).getString("tif_img"))
          //  .circleCrop()
            .error(R.drawable.logo)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.logo)
            .into(holder?.v_tif_img)


    }
    override fun getItemCount() = jsonArray.length()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val v_name = view.name
        val v_day = view.day
        val v_tif_img = view.tif_img

    }
}