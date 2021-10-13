package `in`.practical.coustomnotification.stories.ui


import `in`.practical.coustomnotification.R
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

class ImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        val imageView: ImageView = findViewById(R.id.imageViewData)

        val url: String = intent.getStringExtra("url")!!
        Picasso.get()
            .load(url)
            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    Toast.makeText(
                        this@ImageActivity,
                        "Image loaded from the internet",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onError(e: Exception?) {
                    Toast.makeText(this@ImageActivity, e?.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                    e?.printStackTrace()
                }
            })
    }


}
