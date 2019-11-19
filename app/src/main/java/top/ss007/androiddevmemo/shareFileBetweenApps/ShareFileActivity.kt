package top.ss007.androiddevmemo.shareFileBetweenApps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import top.ss007.androiddevmemo.R
import top.ss007.androiddevmemo.utils.FileUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream


class ShareFileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_file)
        findViewById<Button>(R.id.btn_share_file).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_get_file_from_other_app).setOnClickListener(listener)


    }

    private val listener = View.OnClickListener { v ->
        when (v?.id) {
            R.id.btn_share_file -> {

            }
            R.id.btn_get_file_from_other_app -> {
                val intent = Intent(ACTION_SHARE_FILE).apply {
                    type = "text/plain"
                }
                startActivityForResult(intent, REQUEST_GET_FILE)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GET_FILE) {
            // Get the file's content URI from the incoming Intent
            data?.data?.also { returnUri ->
                /*
                 * Try to open the file for "read" access using the
                 * returned URI. If the file isn't found, write to the
                 * error log and return.
                 */
                val inputText  = try {
                    contentResolver.openFileDescriptor(returnUri, "r")
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    Log.e("MainActivity", "File not found.")
                    return
                }

                // Get a regular file descriptor for the file
                val fd = inputText?.fileDescriptor
                val inputStream=FileInputStream(fd)
                val outStream=FileOutputStream(File(this@ShareFileActivity.getExternalFilesDir(null),"temp.txt"))

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

    companion object {
        const val REQUEST_GET_FILE = 1
        const val ACTION_SHARE_FILE = "top.ss007.dev.memo.SHARE"
    }
}
