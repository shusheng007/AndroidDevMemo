package top.ss007.androiddevmemo.shareFileBetweenApps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import top.ss007.androiddevmemo.R
import top.ss007.androiddevmemo.utils.FileUtils
import java.io.File


class ShareFileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_file)
        findViewById<Button>(R.id.btn_share_file).setOnClickListener(listener)
    }

    private val listener = View.OnClickListener { v ->
        when (v?.id) {
            R.id.btn_share_file -> {

            }
        }

    }


    private fun installApk(file: File, act: Activity) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(
                FileUtils.getUriForFile(act, file),
                "application/vnd.android.package-archive"
            )
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    }


}
