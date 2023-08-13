package com.anurag.sneakerapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anurag.sneakerapp.data.model.Sneaker
import com.anurag.sneakerapp.databinding.ItemSneakerBinding
import com.bumptech.glide.Glide
import java.util.Locale

class SneakerListAdapter(
    private val onItemClickListener: (Sneaker, Int) -> Unit
) : ListAdapter<Sneaker, SneakerListAdapter.ViewHolder>(SneakerDiffCallback()), Filterable {

    private var sneakersFiltered: List<Sneaker> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSneakerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sneaker = getItem(position)
        holder.bind(sneaker)
        holder.itemView.setOnClickListener {
            onItemClickListener(sneaker, position)
        }
    }

    class ViewHolder(
        private val binding: ItemSneakerBinding,
        private val onItemClickListener: (Sneaker, Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(sneaker: Sneaker) {
            binding.sneaker = sneaker

            Glide.with(binding.ivPic)
                .load(sneaker.media.imageUrl)
                .into(binding.ivPic)

            binding.root.setOnClickListener {
                onItemClickListener(sneaker, adapterPosition)
            }

            binding.executePendingBindings()
        }
    }


    private class SneakerDiffCallback : DiffUtil.ItemCallback<Sneaker>() {
        override fun areItemsTheSame(oldItem: Sneaker, newItem: Sneaker): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Sneaker, newItem: Sneaker): Boolean {
            return oldItem == newItem
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint.toString().lowercase(Locale.ROOT)
                val filteredList = if (query.isEmpty()) {
                    currentList
                } else {
                    currentList.filter { sneaker ->
                        sneaker.title.lowercase(Locale.ROOT)
                            .contains(query) || sneaker.brand.lowercase(
                            Locale.ROOT
                        )
                            .contains(query)
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                sneakersFiltered = results?.values as? List<Sneaker> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }
}
