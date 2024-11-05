package com.example.odev2.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.odev2.R
import com.example.odev2.data.model.Subscription

class AddSubAdapter(
    private val subscriptionList: List<Subscription>
) : RecyclerView.Adapter<AddSubAdapter.AddSubViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddSubAdapter.AddSubViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_view_add_subscription, parent, false
        )
        return AddSubViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddSubAdapter.AddSubViewHolder, position: Int) {
        holder.bind(subscriptionList[position])
    }

    override fun getItemCount(): Int {
        return subscriptionList.size
    }


    class AddSubViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(subscription: Subscription) {
            itemView.findViewById<ImageView>(R.id.iv_itemView_addSubscription).setImageResource(
                subscription.image.toInt()
            )
            itemView.findViewById<TextView>(R.id.tv_itemView_addSubscription).text =
                subscription.name
        }
    }
}