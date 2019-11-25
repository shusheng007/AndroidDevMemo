package top.ss007.androiddevmemo.architectureComponents

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import top.ss007.androiddevmemo.R
import top.ss007.viewbinding.DescriptionActivity

class JetpackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack)
        findViewById<Button>(R.id.btn_view_binding).setOnClickListener{
            startActivity(Intent(this,DescriptionActivity::class.java))
        }
    }
}
