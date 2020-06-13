package top.ss007.androiddevmemo.databinding

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import top.ss007.androiddevmemo.R

class DataBindingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDataBindingBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_data_binding)

        val viewModel = ViewModelProvider(this).get(DataBindingViewModel::class.java)
        binding.viewModel = viewModel

        binding.btnSeduceGirl.setOnClickListener(View.OnClickListener {  })

    }
}