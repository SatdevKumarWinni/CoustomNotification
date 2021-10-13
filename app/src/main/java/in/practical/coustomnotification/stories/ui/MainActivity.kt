package `in`.practical.coustomnotification.stories.ui
import `in`.practical.coustomnotification.R
import `in`.practical.coustomnotification.stories.helper.Momentz
import `in`.practical.coustomnotification.stories.helper.MomentzCallback
import `in`.practical.coustomnotification.stories.helper.MomentzView
import `in`.practical.coustomnotification.stories.helper.toPixel
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.progress_story_view.*


class MainActivity : AppCompatActivity(), MomentzCallback {
    private lateinit var listOfViews: List<MomentzView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_story)



        // show a textview
        val textView = TextView(this)
        textView.textSize = 20f.toPixel(this).toFloat()
        textView.gravity = Gravity.CENTER
        textView.setTextColor(Color.parseColor("#000000"))

        //show a customView
        val viedoView = LayoutInflater.from(this).inflate(R.layout.viedo_layout, null)





        //image to be loaded from the internet
        val internetLoadedImageView = ImageView(this)

        //video to be loaded from the internet
        val internetLoadedVideo = VideoView(this)

         listOfViews = listOf(
            MomentzView(textView, 5,"Hello, \n this first text message ","TextView"),
            MomentzView(internetLoadedImageView, 10,"https://i.pinimg.com/564x/14/90/af/1490afa115fe062b12925c594d93a96c.jpg","ImageView"),
            MomentzView(internetLoadedVideo, 60,"https://images.all-free-download.com/footage_preview/mp4/triumphal_arch_paris_traffic_cars_326.mp4","VideoView"),
             MomentzView(internetLoadedImageView, 10,"https://i.pinimg.com/564x/14/90/af/1490afa115fe062b12925c594d93a96c.jpg","ImageView"),
             MomentzView(textView, 5,"Hello, \n this first text message ","TextView")
        )

        Momentz(this, listOfViews, container, this).start()
    }


    override fun onNextCalled(view: View, momentz: Momentz, index: Int) {

        if (view is VideoView) {
            momentz.pause(true)
            playVideo(view, index, momentz)
        } else if ((view is ImageView) && (view.drawable == null)) {
            momentz.pause(true)
            Picasso.get()
                .load(listOfViews[index].resourceUrl)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(view, object : Callback {
                    override fun onSuccess() {
                        momentz.resume()
                        Toast.makeText(this@MainActivity, "Image loaded from the internet", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Exception?) {
                        Toast.makeText(this@MainActivity,e?.localizedMessage,Toast.LENGTH_LONG).show()
                        e?.printStackTrace()
                    }
                })
        }
        else if ((view is TextView)) {
            view.text=listOfViews[index].resourceUrl

        }
    }

    override fun onMoreDetails(index: Int) {
        val intent=  when(listOfViews[index].viewType){
            "VideoView"->{
                Intent(this,VideoActivity::class.java)
            }

            "TextView"->{
                Intent(this,TextActivity::class.java)

            }
            "ImageView"->{
                Intent(this,ImageActivity::class.java)
            }

            else -> {
                Intent(this,VideoActivity::class.java)
            }
        }

        intent.putExtra("url",listOfViews[index].resourceUrl)
        intent.putExtra("type",listOfViews[index].viewType)
        startActivity(intent)
    }

    override fun done() {
        Toast.makeText(this@MainActivity, "Finished!", Toast.LENGTH_LONG).show()
    }

    fun playVideo(videoView: VideoView, index: Int, momentz: Momentz) {
        val uri = Uri.parse(listOfViews[index].resourceUrl)

        videoView.setVideoURI(uri)

        videoView.requestFocus()
        videoView.start()

        videoView.setOnInfoListener(object : MediaPlayer.OnInfoListener {
            override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    // Here the video starts
                    momentz.editDurationAndResume(index, (videoView.duration) / 1000)
                    Toast.makeText(this@MainActivity, "Video loaded from the internet", Toast.LENGTH_LONG).show()
                    return true
                }
                return false
            }
        })
    }


}

