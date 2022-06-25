 package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_meme_page.*

 class MemePage : AppCompatActivity() {
     private var currentImageurl:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meme_page)

        loadmeme()

        share.setOnClickListener {
            intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "$currentImageurl")
            val chooser = Intent.createChooser(intent, "Share this meme using ...")
            startActivity(chooser)
        }

        next.setOnClickListener {
            loadmeme()
        }
    }
     private fun loadmeme(){
         progressBar.visibility = View.VISIBLE
         // Instantiate the RequestQueue.
         val queue = Volley.newRequestQueue(this)
         val url = "https://meme-api.herokuapp.com/gimme"

        // Request a json response from the provided URL.
         val jsonObjectRequest =
             JsonObjectRequest(Request.Method.GET, url, null, { response ->
               currentImageurl = response.getString("url")

                 Glide.with(this).load(currentImageurl).listener(object: RequestListener<Drawable>{
                     override fun onLoadFailed(
                         e: GlideException?,
                         model: Any?,
                         target: Target<Drawable>?,
                         isFirstResource: Boolean
                     ): Boolean {
                         progressBar.visibility = View.VISIBLE
                         return false
                     }

                     override fun onResourceReady(
                         resource: Drawable?,
                         model: Any?,
                         target: Target<Drawable>?,
                         dataSource: DataSource?,
                         isFirstResource: Boolean
                     ): Boolean {
                         progressBar.visibility = View.VISIBLE
                         return false
                     }
                 }).into(memeImage)
             }, {
                 Toast.makeText(this, "check internet connection ", Toast.LENGTH_SHORT).show()
             })

        // Add the request to the RequestQueue.
         queue.add(jsonObjectRequest)
     }
}