package `in`.practical.coustomnotification.stories.ui

import `in`.practical.coustomnotification.R
import `in`.practical.coustomnotification.stories.helper.toPixel
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TextActivity : AppCompatActivity() {
    lateinit var tvTextData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        val url: String = intent.getStringExtra("url")!!

        // show a textview
        val textView = TextView(this)
        textView.text = url
        textView.textSize = 20f.toPixel(this).toFloat()
        textView.gravity = Gravity.CENTER
        textView.setTextColor(Color.parseColor("#000000"))


        tvTextData = findViewById(R.id.tvTextData)
        tvTextData.text = url


    }


}
