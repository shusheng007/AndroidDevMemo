package top.ss007.viewbinding

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import top.ss007.viewbinding.databinding.ActivityDescriptionBinding
import java.util.*


class DescriptionActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvDescription.text="介绍如何使用view binding功能"

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE) {
            binding.tvDisplayDate?.text=Date().toString()
        }
    }
}
