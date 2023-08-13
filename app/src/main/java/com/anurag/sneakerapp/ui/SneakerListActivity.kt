package com.anurag.sneakerapp.ui

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.anurag.sneakerapp.App
import com.anurag.sneakerapp.R
import com.anurag.sneakerapp.data.repository.SneakerRepository
import com.anurag.sneakerapp.databinding.ActivitySneakerListBinding
import com.anurag.sneakerapp.db.CartDatabase
import com.anurag.sneakerapp.ui.viewmodel.SneakerViewModel
import com.anurag.sneakerapp.ui.viewmodel.ViewModelFactory
import java.util.Locale
import javax.inject.Inject

class SneakerListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: SneakerViewModel

    private lateinit var binding: ActivitySneakerListBinding
    private lateinit var adapter: SneakerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sneaker_list)

        (application as App).appComponent.inject(this)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setClickListeners()
        setDefaultSelector()
        setupRecyclerView()
        observeSneakers()
        setupSearchView()
    }

    private fun setupRecyclerView() {
        adapter = SneakerListAdapter { selectedSneaker, _ ->
            navigateToSneakerDetails(selectedSneaker.id)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    private fun observeSneakers() {
        viewModel.sneakers.observe(this) { sneakers ->
            adapter.submitList(sneakers)
        }
    }

    private fun navigateToSneakerDetails(sneakerId: String) {
        val intent = Intent(this, SneakerDetailsActivity::class.java)
        intent.putExtra("sneakerId", sneakerId)
        startActivity(intent)
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText?.lowercase(Locale.getDefault()) ?: ""
                if (query.isEmpty()) {
                    viewModel.updateSneakersFiltered(viewModel.getDummyData())
                } else {
                    val filteredSneakers = viewModel.sneakers.value?.filter { sneaker ->
                        sneaker.title.lowercase(Locale.getDefault()).contains(query) || sneaker.brand.lowercase(
                            Locale.getDefault()
                        )
                            .contains(query)
                    } ?: emptyList()

                    viewModel.updateSneakersFiltered(filteredSneakers)
                }
                return true
            }
        })
    }

    private fun setClickListeners() {
        binding.ivSearch.setOnClickListener {
            binding.searchView.visibility = View.VISIBLE
        }

        binding.searchView.setOnCloseListener {
            binding.searchView.visibility = View.GONE
            false
        }

        val orangeColor = ContextCompat.getColorStateList(this, R.color.orange)
        val transparentColor = ColorStateList.valueOf(Color.TRANSPARENT)

        binding.ivCart.setOnClickListener {
            binding.ivCart.isSelected = true
            binding.ivHome.isSelected = false

            if (binding.ivCart.isSelected) {
                binding.ivCart.backgroundTintList = orangeColor
                binding.ivCart.setImageResource(R.drawable.ic_cart_selected)
                binding.ivHome.backgroundTintList = transparentColor
                val intent = Intent(this, CheckoutActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            } else {
                binding.ivCart.backgroundTintList = transparentColor
            }
        }
    }

    private fun setDefaultSelector() {
        binding.ivHome.isSelected = true
        val orangeColor = ContextCompat.getColorStateList(this, R.color.orange)
        val transparentColor = ColorStateList.valueOf(Color.TRANSPARENT)
        binding.ivHome.backgroundTintList = orangeColor
        binding.ivCart.backgroundTintList = transparentColor
        binding.ivHome.setImageResource(R.drawable.ic_home_selected)
        binding.ivCart.setImageResource(R.drawable.ic_cart)
    }

    override fun onResume() {
        super.onResume()
        setDefaultSelector()
    }
}
