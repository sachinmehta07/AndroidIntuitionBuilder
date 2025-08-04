package com.app.kotlinbasicslearn.mvvm.production_level

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.kotlinbasicslearn.ProductApplication
import com.app.kotlinbasicslearn.R
import com.app.kotlinbasicslearn.coroutines.ProductAdapter
import com.app.kotlinbasicslearn.coroutines.retrofit.CreateProduct
import com.app.kotlinbasicslearn.coroutines.retrofit.Product
import com.app.kotlinbasicslearn.coroutines.retrofit.ProductUpdateRequest
import com.app.kotlinbasicslearn.databinding.ActivityDashboard4Binding
import com.app.kotlinbasicslearn.mvvm.production_level.utils.Resource
import com.app.kotlinbasicslearn.mvvm.production_level.viewmodels.ProductViewModel
import com.app.kotlinbasicslearn.mvvm.production_level.viewmodels.ProductViewModelFactory


class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboard4Binding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productViewModel: ProductViewModel
    private var productToUpdate: Product? = null
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        binding = ActivityDashboard4Binding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("TAG", "onCreate: ")
        setupViewModel()
        setupRecyclerView()
        setupObservers()
        setupListeners()

    }

    override fun onResume() {
        super.onResume()

    }

    private fun setupViewModel() {
        Log.d("TAG", "setupViewModel: ")

        val repository = (application as ProductApplication).productRepository

//        val apiService = RetrofitInstance.apiService
//        val dao = RoomDb.getDb(this)
        //val repository = ProductRepository(apiService, dao.productDao())

        productViewModel = ViewModelProvider(
            this,
            ProductViewModelFactory(this.applicationContext, repository)
        )[ProductViewModel::class.java]


    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(
            onEdit = { product -> prefillProductFields(product) },
            onDelete = { product -> productViewModel.deleteProduct(product.id!!) }
        )
        binding.productRecyclerView.adapter = productAdapter
    }

    private fun setupObservers() {

        productViewModel.productList.observe(this) { resources ->

            when (resources) {

                is Resource.Loading -> {
                    isLoading = true
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    isLoading = false
                    binding.progressBar.visibility = View.GONE
                    productAdapter.submitList(resources.data)
                }

                is Resource.Error -> {
                    isLoading = false
                    binding.progressBar.visibility = View.GONE
                    showToast(resources.message)
                }

            }

        }

        productViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }



        productViewModel.createSuccess.observe(this) { resources ->

            when (resources) {
                is Resource.Loading -> {
                    showProgressBar(true)
                }

                is Resource.Success -> {
                    showProgressBar(false)
                    clearInputFields()
                    showToast("Created successfully")
                }

                is Resource.Error -> {
                    showToast(resources.message)
                }

            }
        }

        productViewModel.updateSuccess.observe(this) { success ->
            showToast(if (success) "Updated successfully" else "Update failed")
            if (success) {
                productToUpdate = null
                binding.btnSubmit.text = getString(R.string.create_product)
                clearInputFields()
            }
        }

        productViewModel.deleteSuccess.observe(this) { success ->
            showToast(if (success) "Deleted successfully" else "Delete failed")
            if (success) {
                if (productToUpdate != null) clearInputFields()
            }
        }

        productViewModel.latestCategoryId.observe(this) { resources ->

            when (resources) {
                is Resource.Loading -> {
                    showProgressBar(true)
                }

                is Resource.Success -> {
                    val product = buildCreateProductFromInput(resources.data)
                    productViewModel.createProduct(product)
                    showProgressBar(false)
                }

                is Resource.Error -> {
                    showToast(resources.message)
                }
            }


        }

//        productViewModel.categoryError.observe(this) { isError ->
//            if (isError) {
//                showToast("Error fetching latest category ID")
//            }
//        }
    }

    private fun setupListeners() {
        binding.btnSubmit.setOnClickListener {
            if (!productViewModel.isInternetAvailable()) return@setOnClickListener
            if (!validateInputs()) {
                showToast("Please fill in all fields")
                return@setOnClickListener
            }

            if (productToUpdate != null) {
                val updatedProduct = buildUpdateProductFromInput(productToUpdate!!)
                productViewModel.updateProduct(productToUpdate!!.id!!, updatedProduct)
            } else {
                productViewModel.fetchLatestCategoryId()
            }
        }
        binding.productRecyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    val totalItemVisible = layoutManager.itemCount
                    if (productViewModel.isInternetAvailable()) {
                        productViewModel.loadMoreProducts(
                            isLoading,
                            lastVisibleItemPosition,
                            totalItemVisible
                        )
                    } else {
                        Toast.makeText(this@Dashboard, "No internet connection", Toast.LENGTH_SHORT)
                            .show()
                    }


                }
            }
        )
    }

    private fun showProgressBar(show: Boolean) {
        if (show) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun validateInputs(): Boolean {
        return binding.etTitle.text.isNotBlank()
                && binding.etPrice.text.isNotBlank()
                && binding.etDescription.text.isNotBlank()
    }

    private fun clearInputFields() {
        binding.etTitle.text.clear()
        binding.etPrice.text.clear()
        binding.etDescription.text.clear()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun prefillProductFields(product: Product) {
        productToUpdate = product
        binding.etTitle.setText(product.title)
        product.price.toString().let {
            binding.etPrice.setText(it)
        }

        binding.etDescription.setText(product.description)
        binding.btnSubmit.text = getString(R.string.update)
    }

    private fun buildCreateProductFromInput(categoryId: Int?): CreateProduct {
        return CreateProduct(
            title = binding.etTitle.text.toString(),
            price = binding.etPrice.text.toString().toFloat(),
            description = binding.etDescription.text.toString(),
            categoryId = categoryId,
            images = listOf("https://placehold.co/600x400")
        )
    }

    private fun buildUpdateProductFromInput(existing: Product): ProductUpdateRequest {
        return ProductUpdateRequest(
            title = binding.etTitle.text.toString(),
            price = binding.etPrice.text.toString().toFloat(),
            description = binding.etDescription.text.toString(),
            categoryId = existing.category?.id,
            images = listOf("https://placehold.co/600x400")
        )
    }
}