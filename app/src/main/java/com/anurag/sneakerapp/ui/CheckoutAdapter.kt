package com.anurag.sneakerapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anurag.sneakerapp.R
import com.anurag.sneakerapp.data.model.Sneaker
import com.anurag.sneakerapp.databinding.ItemCartSneakerBinding
import com.bumptech.glide.Glide

class CheckoutAdapter(private val removeItemCallback: (String) -> Unit) :
    RecyclerView.Adapter<CheckoutViewHolder>() {

    var data: List<Sneaker> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutViewHolder {
        return CheckoutViewHolder.from(parent, removeItemCallback)
    }

    override fun onBindViewHolder(holder: CheckoutViewHolder, position: Int) {
        val sneaker = data[position]
        holder.bind(sneaker)
    }

    override fun getItemCount(): Int = data.size
}

class CheckoutViewHolder(
    private val binding: ItemCartSneakerBinding,
    private val removeCallback: (String) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(sneaker: Sneaker) {
        binding.sneaker = sneaker

        Glide.with(itemView)
            .load(sneaker.media.imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.imageviewSneaker)

        binding.removeItem.setOnClickListener {
            removeCallback(sneaker.id)
        }

        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup, removeCallback: (String) -> Unit): CheckoutViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCartSneakerBinding.inflate(layoutInflater, parent, false)
            return CheckoutViewHolder(binding, removeCallback)
        }
    }
}
