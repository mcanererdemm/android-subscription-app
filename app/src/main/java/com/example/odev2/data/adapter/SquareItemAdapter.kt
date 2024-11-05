package com.example.odev2.data.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.odev2.R
import com.example.odev2.data.model.Subscription

class SquareItemAdapter(
    private var subscriptionList: List<Subscription>
) : RecyclerView.Adapter<SquareItemAdapter.KareSubViewHolder>() {
    class KareSubViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(subscription: Subscription) {
            itemView.findViewById<ImageView>(R.id.iv_kareItemView)
                .setImageResource(subscription.image.toInt())
            itemView.findViewById<TextView>(R.id.tv_name_kareItemView).text = subscription.name
            itemView.findViewById<TextView>(R.id.tv_price_kareItemView).text = subscription.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KareSubViewHolder {
        val view = View.inflate(parent.context, R.layout.square_item_view, null)
        return KareSubViewHolder(view)
    }

    override fun getItemCount(): Int {
        return subscriptionList.size
    }

    override fun onBindViewHolder(holder: KareSubViewHolder, position: Int) {
        holder.bind(subscriptionList[position])
    }

    fun updateSubscriptions(newSubscriptions: List<Subscription>) {
        subscriptionList = newSubscriptions
        notifyDataSetChanged()
    }
}