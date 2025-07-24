package com.app.kotlinbasicslearn.digitalpassport

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import android.view.View

class SignatureView(context: Context) : View(context) {

    private val paint = Paint().apply {
        color = Color.RED
        strokeWidth = 5f
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
    }

    private val path = Path()
    private lateinit var bitmap: Bitmap
    private lateinit var canvas: Canvas

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, 0f, 0f, null) // ✅ Use null here
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> path.moveTo(x, y)
            MotionEvent.ACTION_MOVE -> path.lineTo(x, y)
            MotionEvent.ACTION_UP -> {
                canvas.drawPath(path, paint) // Draw final line on Bitmap
                path.reset()
            }
        }
        invalidate() // Redraw
        return true
    }


    fun clear() {

        if (::bitmap.isInitialized) {
            path.reset()
            bitmap.eraseColor(Color.TRANSPARENT)
            invalidate()
        }

    }


}