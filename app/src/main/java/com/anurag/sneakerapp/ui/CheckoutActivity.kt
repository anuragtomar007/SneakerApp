package com.anurag.sneakerapp.ui

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.anurag.sneakerapp.App
import com.anurag.sneakerapp.R
import com.anurag.sneakerapp.data.model.Sneaker
import com.anurag.sneakerapp.data.repository.SneakerRepository
import com.anurag.sneakerapp.databinding.ActivityCheckoutBinding
import com.anurag.sneakerapp.db.CartDatabase
import com.anurag.sneakerapp.db.CartItem
import com.anurag.sneakerapp.ui.viewmodel.CheckoutViewModel
import com.anurag.sneakerapp.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class CheckoutActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: CheckoutViewModel

    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var checkoutAdapter: CheckoutAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout)

        (application as App).appComponent.inject(this)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setUpToolBar()
        initData()
        setClickListeners()
        setUpRecyclerView()
        setUpObservers()
    }

    private fun initData() {
        val sneakerId = intent.getStringExtra("sneakerId")
        if (sneakerId != null) {
            val selectedSneaker = viewModel.fetchSneakerById(sneakerId)
            lifecycleScope.launch {
                viewModel.addToCart(selectedSneaker)
            }
        }
    }

    private fun setUpObservers() {
        viewModel.cartItemLiveData.observe(this) { cartItems ->
            viewModel.updateTotalPrice()
            updateCartItemAdapter(cartItems)
        }

        viewModel.fetchAllSneakers().observe(this) { cartItems ->
            updateCartItemAdapter(cartItems)
        }
    }

    private fun setUpRecyclerView() {
        checkoutAdapter = CheckoutAdapter { sneakerIdToBeRemoved ->
            viewModel.removeFromCart(sneakerIdToBeRemoved)
        }

        binding.cartRecyclerView.adapter = checkoutAdapter
        val layoutManager = LinearLayoutManager(this)
        binding.cartRecyclerView.layoutManager = layoutManager
    }

    private fun setUpToolBar() {
        val toolbar: Toolbar = findViewById(com.anurag.sneakerapp.R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun updateCartItemAdapter(cartItems: List<CartItem>) {
        val sneakers = cartItems.map { cartItem ->
            Sneaker(
                id = cartItem.id,
                brand = cartItem.brand,
                colorway = cartItem.colorway,
                gender = cartItem.gender,
                media = cartItem.media,
                releaseDate = cartItem.releaseDate,
                retailPrice = cartItem.retailPrice,
                styleId = cartItem.styleId,
                shoe = cartItem.shoe,
                name = cartItem.name,
                title = cartItem.title,
                year = cartItem.year
            )
        }

        checkoutAdapter.data = sneakers
        checkoutAdapter.notifyDataSetChanged()
        val subTotalPrice = findViewById<TextView>(R.id.subTotalPrice)
        subTotalPrice.text = viewModel.totalPrice.value

        val totalPriceValue = findViewById<TextView>(R.id.totalPriceValue)
        totalPriceValue.text = viewModel.totalPriceWithTax.value
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setClickListeners() {
        binding.ivCart.isSelected = true
        val orangeColor = ContextCompat.getColorStateList(this, R.color.orange)
        val transparentColor = ColorStateList.valueOf(Color.TRANSPARENT)
        binding.ivCart.backgroundTintList = orangeColor
        binding.ivHome.backgroundTintList = transparentColor
        binding.ivCart.setImageResource(R.drawable.ic_cart_selected)

        binding.ivHome.setOnClickListener {
            binding.ivHome.isSelected = true
            binding.ivCart.isSelected = false

            if (binding.ivHome.isSelected) {
                binding.ivHome.backgroundTintList = orangeColor
                binding.ivCart.backgroundTintList = transparentColor
                val intent = Intent(this, SneakerListActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
            } else {
                binding.ivHome.backgroundTintList = transparentColor
            }
        }
    }
}
