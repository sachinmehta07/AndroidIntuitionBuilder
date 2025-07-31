package com.app.kotlinbasicslearn.mvvm.retrofit

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.app.kotlinbasicslearn.R
import com.app.kotlinbasicslearn.coroutines.ProductAdapter
import com.app.kotlinbasicslearn.coroutines.retrofit.CreateProduct
import com.app.kotlinbasicslearn.coroutines.retrofit.Product
import com.app.kotlinbasicslearn.coroutines.retrofit.ProductUpdateRequest
import com.app.kotlinbasicslearn.coroutines.retrofit.RetrofitInstance
import com.app.kotlinbasicslearn.databinding.ActivityDashboard3Binding
import com.app.kotlinbasicslearn.mvvm.retrofit.repository.ProductRepository
import com.app.kotlinbasicslearn.mvvm.retrofit.viewmodel.ProductViewModel
import com.app.kotlinbasicslearn.mvvm.retrofit.viewmodel.ProductViewModelFactory

class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboard3Binding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productViewModel: ProductViewModel
    private var productUpdate: Product? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityDashboard3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val productApiServices = RetrofitInstance.apiService

        val repository = ProductRepository(productApiServices)

        productViewModel = ViewModelProvider(
            this,
            ProductViewModelFactory(repository)
        )[ProductViewModel::class.java]


        productAdapter = ProductAdapter(
            onEdit = { product ->
                onEditProduct(product)
            },
            onDelete = { product ->
                onDelete(product)
            })


        binding.progressBar.visibility = View.VISIBLE

        productViewModel.quotesList.observe(this) {
            binding.progressBar.visibility = View.GONE
            productAdapter.submitList(it)
        }

        binding.productRecyclerView.adapter = productAdapter

        binding.btnSubmit.setOnClickListener {

            if (binding.etTitle.text.toString().isEmpty() || binding.etPrice.text.toString()
                    .isEmpty() || binding.etDescription.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            binding.progressBar.visibility = View.VISIBLE

            if (productUpdate != null) {

                productViewModel.updateProduct(
                    productUpdate!!.id!!,
                    ProductUpdateRequest(
                        title = binding.etTitle.text.toString(),
                        price = binding.etPrice.text.toString().toFloat(),
                        description = binding.etDescription.text.toString(),
                        categoryId = productUpdate?.category?.id, // fallback to null
                        images = listOf("https://placehold.co/600x400")
                    )
                )


            } else {

                productViewModel.fetchLatestCategoryId()

                binding.progressBar.visibility = View.VISIBLE

            }
        }

        productViewModel.latestCategoryId.observe(this) { categoryId ->
            val product = CreateProduct(
                title = binding.etTitle.text.toString(),
                price = binding.etPrice.text.toString().toFloat(),
                description = binding.etDescription.text.toString(),
                categoryId = categoryId,
                images = listOf("https://placehold.co/600x400")
            )
            productViewModel.createProduct(product)
        }

        productViewModel.categoryFetchError.observe(this) { isError ->
            if (isError) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Error fetching latest category ID", Toast.LENGTH_SHORT).show()
            }
        }

        productViewModel.createProductResult.observe(this) { success ->
            if (success) {
                binding.etTitle.setText("")
                binding.etPrice.setText("")
                binding.etDescription.setText("")
                Toast.makeText(this, "Created successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }
            binding.progressBar.visibility = View.GONE
        }

        productViewModel.updateProductResult.observe(this) { success ->

            if (success) {
                productUpdate = null
                binding.btnSubmit.text = getString(R.string.create_product)
                binding.etTitle.setText("")
                binding.etPrice.setText("")
                binding.etDescription.setText("")
                Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }
            binding.progressBar.visibility = View.GONE
        }

        productViewModel.productDeleteResult.observe(this) { success ->
            binding.progressBar.visibility = View.GONE
            if (success) {
                if (productUpdate != null) {
                    binding.etTitle.setText("")
                    binding.etPrice.setText("")
                    binding.etDescription.setText("")

                }
                Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }

        }

    }

    private fun onDelete(product: Product) {
        binding.progressBar.visibility = View.VISIBLE
        productViewModel.deleteProduct(product)
    }

    private fun onEditProduct(product: Product) {
        productUpdate = product
        binding.etTitle.setText(product.title)

        product.price.toString().let {
            binding.etPrice.setText(it)
        }

        binding.etDescription.setText(product.description)

        binding.btnSubmit.text = getString(R.string.update)

        Toast.makeText(this, product.title, Toast.LENGTH_SHORT).show()


    }

}