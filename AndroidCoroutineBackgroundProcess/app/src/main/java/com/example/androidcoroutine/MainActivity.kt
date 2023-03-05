package com.example.androidcoroutine

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.net.URL

class MainActivity : AppCompatActivity() {
    val url  = "https://picsum.photosdfdfdf/600"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val deffered = CoroutineScope(Dispatchers.IO).async { getImage() }

                val bitMap = deffered.await()
                val progressBar = findViewById<ProgressBar>(R.id.progressBar)
                val imageView = findViewById<ImageView>(R.id.imageView)
                bitMap.let {
                    imageView.setImageBitmap(bitMap)
                    imageView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
                throw Exception("custom error")
            } catch (e: java.lang.Exception) {
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getImage() : Bitmap  {
        val inputStream = URL(url).openStream()
        val bmp = BitmapFactory.decodeStream(inputStream)
        inputStream.close()
        return bmp
    }
}