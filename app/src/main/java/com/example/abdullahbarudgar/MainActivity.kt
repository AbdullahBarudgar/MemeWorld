package com.example.abdullahbarudgar

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {

     private var newurl :String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()

    }
    private fun loadMeme(){

            findViewById<ProgressBar>(R.id.progressBar).visibility =View.VISIBLE
            val queue = Volley.newRequestQueue(this)
             val url = "https://meme-api.herokuapp.com/gimme"

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                     newurl = response.getString("url")
                    Glide.with(this).load(newurl).listener(object : RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            findViewById<ProgressBar>(R.id.progressBar).visibility =View.GONE
                            return false }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            findViewById<ProgressBar>(R.id.progressBar).visibility =View.GONE
                            return false

                        }
                    }).into( findViewById(R.id.memeImageView))

                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "There is error $error", Toast.LENGTH_LONG).show()
                }
            )
            queue.add(jsonObjectRequest)
        }

        fun shareMeme(view : View) {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.setType("text/plain")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "Check out funny new meme : $newurl")
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share it with your friends : ")
            startActivity(Intent.createChooser(sharingIntent, "Share using"))
        }

        fun nextMeme(view : View) {
            loadMeme()
        }
       }
