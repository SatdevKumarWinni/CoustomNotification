package `in`.practical.coustomnotification.stories.ui

import `in`.practical.coustomnotification.R
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class VideoActivity : AppCompatActivity() {
    lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        videoView = findViewById(R.id.videoView)

        val url: String = intent.getStringExtra("url")!!
        playVideo(url)


    }


    private fun playVideo(url: String) {
        val uri = Uri.parse(url)

        videoView.setVideoURI(uri)

        videoView.requestFocus()
        videoView.start()

        videoView.setOnPreparedListener {
            Toast.makeText(this, "About to be played", Toast.LENGTH_LONG).show()
        }
    }
}
