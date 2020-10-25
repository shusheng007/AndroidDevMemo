package top.ss007.androiddevmemo.coroutines

import android.animation.ValueAnimator
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import top.ss007.androiddevmemo.R


class CoroutinesActivity : AppCompatActivity() {

    private lateinit var viewModel: CoroutinesViewModel
    private lateinit var tvWomanName: TextView
    private lateinit var btnSearch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)
        viewModel = ViewModelProvider(this).get(CoroutinesViewModel::class.java)
        tvWomanName = findViewById(R.id.tv_name)
        btnSearch = findViewById(R.id.btn_get_name)

        viewModel.nameLiveData.observe(this) {
            tvWomanName.text = it

            btnSearch.text = "启动天眼系统查询"
        }
        btnSearch.setOnClickListener {
            viewModel.checkTheWomen()
            btnSearch.text = "天眼系统正常查询中..."
            animateImage(findViewById<ImageView>(R.id.img_my_wife))
        }
    }

    fun animateImage(img: ImageView) {
        ValueAnimator.ofInt(255, 100, 255).apply {
            duration = 1500
            startDelay = 0
            repeatCount = 1
            repeatMode = ValueAnimator.REVERSE
            addUpdateListener {
                val fraction: Float = it.animatedFraction //当前动画进度
                img.imageAlpha = it.animatedValue as Int
            }
        }.start()
    }
}