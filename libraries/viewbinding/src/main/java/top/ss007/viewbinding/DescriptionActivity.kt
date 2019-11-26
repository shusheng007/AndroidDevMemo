package top.ss007.viewbinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import top.ss007.viewbinding.databinding.ActivityDescriptionBinding
import java.text.SimpleDateFormat
import java.util.*


class DescriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvDescription.text = "关关雎鸠,在河之洲.窈窕淑女,君子好逑."
        binding.tvDisplayDate?.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
    }
}
