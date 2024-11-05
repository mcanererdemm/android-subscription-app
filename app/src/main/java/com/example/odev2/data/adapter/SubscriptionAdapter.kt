import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.odev2.R
import com.example.odev2.data.model.Subscription
import com.google.android.material.button.MaterialButton

class SubscriptionAdapter(
    private var subscriptions: List<Subscription>,
    private val onClick: (Subscription) -> Unit
) : RecyclerView.Adapter<SubscriptionAdapter.SubscriptionViewHolder>() {

    inner class SubscriptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(subscription: Subscription) {
            itemView.findViewById<TextView>(R.id.tv_name_itemSubscription).text = subscription.name
            itemView.findViewById<TextView>(R.id.tv_price_itemSubscription).text =
                "₺${subscription.price}"

            val imageView = itemView.findViewById<ImageView>(R.id.iv_itemSubscription)
            try {
                imageView.setImageResource(subscription.image)
            } catch (e: Exception) {
                imageView.setImageResource(R.drawable.default_background)
            }

//            itemView.setOnClickListener {
//                onClick(subscription)
//            }
            itemView.findViewById<MaterialButton>(R.id.btn_delete_homeFragment).setOnClickListener {
                subscriptions = subscriptions.filter { it.id != subscription.id }
                notifyDataSetChanged()
                onClick(subscription)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_subscription, parent, false)
        return SubscriptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubscriptionViewHolder, position: Int) {
        holder.bind(subscriptions[position])
    }

    override fun getItemCount(): Int = subscriptions.size

    fun updateSubscriptions(newSubscriptions: List<Subscription>) {
        subscriptions = newSubscriptions
        notifyDataSetChanged()
    }
}
