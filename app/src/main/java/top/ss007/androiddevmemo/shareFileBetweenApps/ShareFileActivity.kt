package top.ss007.androiddevmemo.shareFileBetweenApps

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import top.ss007.androiddevmemo.R
import top.ss007.androiddevmemo.utils.FileUtils
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream


class ShareFileActivity : AppCompatActivity() {

    private lateinit var ivImage: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_file)
        ivImage = findViewById(R.id.iv_display)
        findViewById<Button>(R.id.btn_share_file).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_get_file_from_other_app).setOnClickListener(listener)
    }

    private val listener = View.OnClickListener { v ->
        when (v?.id) {
            R.id.btn_share_file -> {
                installApk(this@ShareFileActivity)
            }
            R.id.btn_get_file_from_other_app -> {
                val intent = Intent(ACTION_SHARE_FILE).apply {
                    type = "text/plain"
                }
                if (intent.resolveActivity(packageManager) != null) {
                    startActivityForResult(intent, REQUEST_GET_FILE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GET_FILE) {
            data?.data?.also { returnUri ->
                val input = try {
                    contentResolver.openFileDescriptor(returnUri, "r")
                } catch (e: FileNotFoundException) {
                    Log.e("MainActivity", "File not found.")
                    return
                }
                val fd = input?.fileDescriptor
                ivImage.setImageBitmap(BitmapFactory.decodeFileDescriptor(fd))
            }
        }
    }

    private fun installApk(act: Activity) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(
                FileUtils.getUriForFile(act, copyFileToLocalStorage(act)),
                "application/vnd.android.package-archive"
            )
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    }


    private fun copyFileToLocalStorage(act: Activity): File {
        val dir = act.getExternalFilesDir(null)
        val file = File(dir, "appProvider.apk")
        val inputStream = resources.openRawResource(R.raw.app_provider)
        val data = ByteArray(inputStream.available())
        inputStream.read(data)
        FileOutputStream(file).use {
            it.write(data)
        }
        return file
    }

    companion object {
        const val TAG = "ShareFileActivity"
        const val REQUEST_GET_FILE = 1
        const val ACTION_SHARE_FILE = "top.ss007.dev.memo.SHARE"
    }
}
