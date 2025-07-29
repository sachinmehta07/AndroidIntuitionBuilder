package com.app.kotlinbasicslearn.coroutines

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.kotlinbasicslearn.R
import com.app.kotlinbasicslearn.coroutines.retrofit.CreateProduct
import com.app.kotlinbasicslearn.coroutines.retrofit.Product
import com.app.kotlinbasicslearn.coroutines.retrofit.ProductUpdateRequest
import com.app.kotlinbasicslearn.coroutines.retrofit.RetrofitInstance
import com.app.kotlinbasicslearn.databinding.ActivityMain5Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private var isLoading = false
    private lateinit var binding: ActivityMain5Binding
    private lateinit var productAdapter: ProductAdapter
    private var productUpdate: Product? = null
    private lateinit var products: MutableList<Product>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityMain5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        productAdapter = ProductAdapter(
            onEdit = { product ->
                onEditProduct(product)
            },
            onDelete = { product ->
                onDeleteProduct(product)
            }
        )

        products = mutableListOf()

        fetchProducts()

        binding.productRecyclerView.adapter = productAdapter

        binding.btnSubmit.setOnClickListener {
            if (binding.etTitle.text.toString().isEmpty() || binding.etPrice.text.toString()
                    .isEmpty() || binding.etDescription.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (productUpdate != null) {

                updateProduct(
                    ProductUpdateRequest(
                        title = binding.etTitle.text.toString(),
                        price = binding.etPrice.text.toString().toFloat(),
                        description = binding.etDescription.text.toString(),
                        categoryId = productUpdate?.category?.id, // fallback to null
                        images = listOf("https://placehold.co/600x400")

                    )
                )

            } else {
                val newProduct = CreateProduct(
                    title = binding.etTitle.text.toString(),
                    price = binding.etPrice.text.toString().toFloat(),
                    description = binding.etDescription.text.toString(),
                    categoryId = 1,
                    images = listOf("https://placehold.co/600x400"),
                )
                createProduct(newProduct)
            }
        }

        binding.productRecyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    //to get scroll position

                    val layManager = recyclerView.layoutManager as LinearLayoutManager

                    //to get the pos of last visible item in screen

                    //findLastCompletelyVisibleItemPosition >> fully item is visible not just bit pixel or peeking from bottom

//                findLastVisibleItemPosition() >> even slightest visibility of item will be counted
                    val lastVisibleItem = layManager.findLastVisibleItemPosition()

                    //simply return the total num of items in rv
                    val totalItemVisible = layManager.itemCount

                    Log.d("Scroll", "lastVisible=$lastVisibleItem, total=$totalItemVisible")

                    if (!isLoading && lastVisibleItem >= totalItemVisible - 5) {
                        isLoading = true
                        loadData()
                    }

                }
            }
        )

    }

    private fun createProduct(product: CreateProduct) {
        lifecycleScope.launch(Dispatchers.IO) {

            val response = RetrofitInstance.apiService.createProduct(product)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    binding.etPrice.setText("")
                    binding.etTitle.setText("")
                    binding.etDescription.setText("")

                    Log.d("TAG", "createProduct: " + response.body())
                    fetchProducts()
                    Toast.makeText(
                        this@MainActivity,
                        response.message().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        response.body().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("TAG", "createProduct: " + response.body())
                    Log.d("TAG", "createProduct: " + response.errorBody().toString())
                    Log.d("TAG", "createProduct: " + response.message())
                }
            }
        }
    }

    private fun updateProduct(product: ProductUpdateRequest) {
        lifecycleScope.launch(Dispatchers.IO) {

            val response = RetrofitInstance.apiService.updateProduct(productUpdate?.id!!, product)
            withContext(Dispatchers.Main) {

                if (response.isSuccessful) {
                    productUpdate = null
                    Log.d("TAG", "updateProduct: " + response.body())
                    Log.d("TAG", "updateProduct: " + response.isSuccessful)
                    Log.d("TAG", "updateProduct: " + response.message().toString())
                    fetchProducts()
                    Toast.makeText(
                        this@MainActivity,
                        response.body().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.btnSubmit.text = getString(R.string.create_product)
                    binding.etTitle.setText("")
                    binding.etPrice.setText("")
                    binding.etDescription.setText("")
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        response.body().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("TAG", "updateProduct: " + response.body().toString())
                    Log.d("TAG", "updateProduct: " + response.errorBody().toString())
                    Log.d("TAG", "updateProduct: " + response.message().toString())
                    Log.d("TAG", "updateProduct: $productUpdate")
                    Log.d("TAG", "updateProduct: ${productUpdate!!.id}")
                    Log.d("TAG", "updateProduct: ${productUpdate!!.categoryId}")
                    Log.d("TAG", "updateProduct ERROR BODY: ${response.errorBody()?.string()}")
                }
            }
        }
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

    private fun onDeleteProduct(product: Product) {
        lifecycleScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.apiService.deleteProduct(product.id!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    products.remove(product)
                    productAdapter.submitList(products.toMutableList())
                    Toast.makeText(this@MainActivity, "Deleted successfully", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        response.message().toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                    Log.d("TAG", "onDeleteProduct: ${response.errorBody().toString()}")
                }

            }

        }
    }

    private fun fetchProducts() {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.IO) {
            //on Bg Thread
            val response = RetrofitInstance.apiService.getProducts()


            Log.d("RetrofitInstance", "fetchProducts: +  " + response.message())
            Log.d("RetrofitInstance", "fetchProducts: +  " + response.isSuccessful)
            Log.d("RetrofitInstance", "fetchProducts: +  " + response.body())
            //on main thread
            try {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    if (response.isSuccessful) {

                        Log.d("RetrofitInstance", "fetchProducts: +  " + response.message())
                        Log.d("RetrofitInstance", "fetchProducts: +  " + response.isSuccessful)
                        Log.d("RetrofitInstance", "fetchProducts: +  " + response.body())
                        binding.txResultStatus.text = response.message().toString()

                        val freshProducts = response.body() as MutableList<Product>
                        products.clear()
                        products.addAll(freshProducts)
                        productAdapter.submitList(products.toMutableList())

                    } else {
                        binding.txResultStatus.text = response.message().toString()
                        Log.d("RetrofitInstance", "fetchProducts: +  " + response.message())
                        Log.d("RetrofitInstance", "fetchProducts: +  " + response.isSuccessful)
                        Log.d("RetrofitInstance", "fetchProducts: +  " + response.body())
                    }
                }
            } catch (e: Exception) {
                Log.d("RetrofitInstance", "fetchProducts: " + e.message)
                binding.txResultStatus.text = e.message
            }
        }

    }

    private fun loadData() {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.apiService.getProducts(20, products.size)
            try {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    if (response.isSuccessful && response.body() != null) {
                        Log.d("isSuccessful", "loadData: " + response.body().toString())
                        Log.d("isSuccessful", "loadData: " + response.message())

                        val newProductLists = response.body()

                        if (newProductLists != null) {
                            if (newProductLists.isEmpty()) {
                                // No more data to load, stop triggering scroll
                                binding.productRecyclerView.clearOnScrollListeners()
                                Log.d("TAG", "loadData: NO MORE DATA")
                                return@withContext
                            }
                        }

//                        val newProducts = products.toMutableList()
                        newProductLists?.let {
                            products.addAll(it)
                            productAdapter.submitList(products.toMutableList()) // triggers DiffUtil
                        }

                        Log.d("Pagination", "Total items before = ${products.size}")
                        Log.d("Pagination", "Fetched = ${newProductLists?.size}")
                        //   productAdapter.submitList(products.toList())

                        isLoading = false
                    } else {
                        Log.d("ERROR", "loadData: " + response.errorBody().toString())
                        Log.d("ERROR", "loadData: " + response.message())
                    }
                }
            } catch (e: Exception) {
                Log.d("TAG", "loadData: " + e.message)
            }

        }
    }

}