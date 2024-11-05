package com.example.odev2.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.odev2.R
import com.example.odev2.data.model.Subscription


class CategoryAdapter(
    private val categoryList: List<Subscription>,
    private val onclick: (subscription: Subscription) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    inner class CategoryViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(subscription: Subscription) {
            itemView.apply {
                findViewById<ImageView>(R.id.iv_categoryFragment).setImageResource(subscription.image)
                findViewById<TextView>(R.id.tv_name_categoryFragment).text = subscription.name
                findViewById<TextView>(R.id.tv_price_categoryFragment).text = "₺${subscription.price}"
                setOnClickListener {
                    onclick(subscription)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.category_item_view, parent, false
        )
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }
}