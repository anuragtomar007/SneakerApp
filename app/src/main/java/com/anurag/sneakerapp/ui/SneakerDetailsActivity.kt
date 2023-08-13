package com.anurag.sneakerapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.anurag.sneakerapp.App
import com.anurag.sneakerapp.R
import com.anurag.sneakerapp.data.model.Sneaker
import com.anurag.sneakerapp.databinding.ActivitySneakerDetailBinding
import com.anurag.sneakerapp.ui.viewmodel.SneakerDetailsViewModel
import com.bumptech.glide.Glide
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class SneakerDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySneakerDetailBinding

    @Inject
    lateinit var viewModel: SneakerDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sneaker_detail)

        (application as App).appComponent.inject(this)

        setUpToolBar()
        setDefaultSelector()
        setClickListeners()
        initData()
        setupObservers()
        setupButtonsForColorAndSize()
    }

    private fun setupObservers() {
        viewModel.sneakerDetails.observe(this) { sneaker ->
            updateUIWithSneakerDetails(sneaker)
        }
    }

    private fun initData() {
        val sneakerId = intent.getStringExtra("sneakerId")
        if (sneakerId != null) {
            viewModel.fetchSneakerDetails(sneakerId)
        }
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

    private fun setClickListeners() {
        binding.addToCart.setOnClickListener {
            val selectedSneaker = viewModel.sneakerDetails.value
            if (selectedSneaker != null) {
                viewModel.addToCart(selectedSneaker)
                val intent = Intent(this, CheckoutActivity::class.java)
                intent.putExtra("sneakerId", selectedSneaker.id)
                startActivity(intent)
            }
        }

        val orangeColor = ContextCompat.getColorStateList(this, R.color.orange)
        val transparentColor = ColorStateList.valueOf(Color.TRANSPARENT)

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

        binding.ivCart.setOnClickListener {
            binding.ivHome.isSelected = false
            binding.ivCart.isSelected = true

            if (binding.ivCart.isSelected) {
                binding.ivCart.backgroundTintList = orangeColor
                binding.ivHome.backgroundTintList = transparentColor
                binding.ivCart.setImageResource(R.drawable.ic_cart_selected)
                val intent = Intent(this, CheckoutActivity::class.java)
                startActivity(intent)
            } else {
                binding.ivCart.backgroundTintList = transparentColor
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateUIWithSneakerDetails(sneaker: Sneaker) {
        var year = "1995"
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd")
            val date: Date? = inputFormat.parse(sneaker.releaseDate)
            val outputFormat = SimpleDateFormat("yyyy")
            year = date?.let { outputFormat.format(it) }.toString()
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val formattedText =
            getString(R.string.brand_title_and_year, sneaker.brand, sneaker.title, year)
        binding.titleTextView.text = formattedText

        binding.priceTextView.text = String.format("$%d", sneaker.retailPrice)
        Glide.with(this)
            .load(sneaker.media.imageUrl)
            .into(binding.ivSneaker)
    }

    private fun setupButtonsForColorAndSize() {

        val sizeTextViews = listOf(
            binding.textViewSize1,
            binding.textViewSize2,
            binding.textViewSize3
        )
        setupTextViewGroupListeners(sizeTextViews)
    }

    fun onTextViewClicked(view: View) {
        val textView = view as TextView
        textView.isSelected = !textView.isSelected

        val textViewList = listOf(
            binding.textViewColor1,
            binding.textViewColor2,
            binding.textViewColor3
        )
        textViewList.filter { it != textView }.forEach { it.isSelected = false }
    }

    private fun setupTextViewGroupListeners(textViews: List<TextView>) {
        textViews.forEach { textView ->
            textView.setOnClickListener {
                textViews.forEach { otherTextView ->
                    if (otherTextView == textView) {
                        otherTextView.setBackgroundResource(R.drawable.background_selected)
                        otherTextView.setTextColor(Color.WHITE)
                    } else {
                        otherTextView.setBackgroundResource(R.drawable.background_unselected)
                        otherTextView.setTextColor(
                            ContextCompat.getColor(
                                this,
                                android.R.color.holo_orange_light
                            )
                        )
                    }
                }
            }
        }
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
