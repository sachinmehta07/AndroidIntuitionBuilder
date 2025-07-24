package com.app.kotlinbasicslearn.digitalpassport

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.kotlinbasicslearn.R
import com.app.kotlinbasicslearn.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private lateinit var signatureView: SignatureView

    private val gridRows = 4  // e.g., 4 rows
    private val gridCols = 2  // e.g., 2 columns

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Load your sample image from drawable
        val originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.logo)

        // Create the passport photo grid
        val passportGrid = generateGridBitmap(originalBitmap, gridCols, gridRows)

        passportGrid.let {
            binding.passportGridView.setImageBitmap(it)
        }

    }

    private fun generateGridBitmap(originalBitmap: Bitmap, cols: Int, rows: Int): Bitmap? {
        // Step 1: Downscale the bitmap (adjust as needed)
        val targetSingleWidth = 200
        val targetSingleHeight = 300

        val scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, targetSingleWidth, targetSingleHeight, true)

        // Step 2: Calculate final grid size
        val fullWidth = scaledBitmap.width * cols
        val fullHeight = scaledBitmap.height * rows

        // Safety check for Android's hardware bitmap size limits (~8192px on many devices)
        val maxCanvasSize = 8192

        if (fullWidth > maxCanvasSize || fullHeight > maxCanvasSize) {
            runOnUiThread {
                Toast.makeText(this, "Grid too large! Please reduce grid size.", Toast.LENGTH_SHORT).show()
            }
            return null
        }

        // Step 3: Draw passport grid on canvas
        val resultBitmap = Bitmap.createBitmap(fullWidth, fullHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(resultBitmap)

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val x = col * scaledBitmap.width
                val y = row * scaledBitmap.height
                canvas.drawBitmap(scaledBitmap, x.toFloat(), y.toFloat(), null)
            }
        }

        return resultBitmap
    }


//
//    private fun generateGridBitmap(bitmap: Bitmap, cols: Int, rows: Int): Bitmap {
//        val singleWidth = bitmap.width
//        val singleHeight = bitmap.height
//
//        // Full canvas size = single image size × grid size
//        val fullWidth = singleWidth * cols
//        val fullHeight = singleHeight * rows
//
//        val resultBitmap = Bitmap.createBitmap(fullWidth, fullHeight, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(resultBitmap)
//
//        for (row in 0 until rows) {
//            for (col in 0 until cols) {
//                val x = col * singleWidth
//                val y = row * singleHeight
//                canvas.drawBitmap(bitmap, x.toFloat(), y.toFloat(), null)
//            }
//        }
//
//        return resultBitmap
//    }
}