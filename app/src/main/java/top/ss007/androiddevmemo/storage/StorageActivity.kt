package top.ss007.androiddevmemo.storage

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import top.ss007.androiddevmemo.R
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * 为了可以更好的展示相关的技术，所有操作均在UI线程完成，实际开发时需要使用IO线程
 */
class StorageActivity : AppCompatActivity() {
    private lateinit var tvResult: TextView
    private var sb = StringBuilder()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)
        tvResult = findViewById(R.id.tv_result)
        tvResult.setMovementMethod(ScrollingMovementMethod())
        findViewById<Button>(R.id.btn_write_internal).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_write_internal_catch).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_write_external).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_write_external_catch).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_read_internal).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_read_internal_catch).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_read_external).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_read_external_catch).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_write_sd_card).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_save_media_to_gallery).setOnClickListener(listener)
    }

    private val listener = View.OnClickListener { v ->
        when (v?.id) {
            //只有APP自己可以读写
            R.id.btn_write_internal -> {
                val dir = this@StorageActivity.filesDir
                val file = File(dir, INTERNAL_FILE_NAME)
                writeFile(file)
                sb.append("内部写: $dir \n\n")
            }
            R.id.btn_read_internal -> {
                val resultStr =
                    readFile(this@StorageActivity.filesDir.path + "/" + INTERNAL_FILE_NAME)
                sb.append("内部读: $resultStr \n\n")
            }
            //APP自己可以读写，当系统存储不足时会被删除，用户最好自己维护缓存的size
            R.id.btn_write_internal_catch -> {
                val dir = this@StorageActivity.cacheDir
                val file = File(dir, INTERNAL_CATCH_FILE_NAME)
                writeFile(file)
                sb.append("内部缓存写: $dir \n\n")
            }
            R.id.btn_read_internal_catch -> {
                val resultStr =
                    readFile(this@StorageActivity.cacheDir.path + "/" + INTERNAL_CATCH_FILE_NAME)
                sb.append("内部缓存读: $resultStr \n\n")
            }
            //所有APP可见，但是需要知道具体的文件路径，API>19以后不需要写入及读取存储的权限
            R.id.btn_write_external -> {
                //参数null表示没有子目录
                val dir = this@StorageActivity.getExternalFilesDir(null)
                val file = File(dir, EXTERNAL_FILE_NAME)
                writeFile(file)
                sb.append("外部写: $dir \n\n")
            }
            R.id.btn_read_external -> {
                val dir = this@StorageActivity.getExternalFilesDir(null)
                val resultStr =
                    readFile(dir?.path + "/" + EXTERNAL_FILE_NAME)
                sb.append("外部读: $resultStr \n\n")
            }
            R.id.btn_write_external_catch -> {
                val dir = this@StorageActivity.externalCacheDir
                val file = File(dir, EXTERNAL_CATCH_FILE_NAME)
                writeFile(file)
                sb.append("外部缓存写: $dir \n\n")
            }
            R.id.btn_read_external_catch -> {
                val resultStr =
                    readFile(this@StorageActivity.externalCacheDir?.path + "/" + EXTERNAL_CATCH_FILE_NAME)
                sb.append("外部缓存读: $resultStr \n\n")
            }
            //需要写存储权限
            R.id.btn_write_sd_card -> {
                if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                    ActivityCompat.requestPermissions(
                        this@StorageActivity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        PERMISSION_REQUEST_CODE
                    )
                }
            }
            R.id.btn_save_media_to_gallery -> {
                if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                    ActivityCompat.requestPermissions(
                        this@StorageActivity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        PERMISSION_REQUEST_CODE_SAVE_TO_GALLERY
                    )
                }
            }
        }
        tvResult.text = sb.toString()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //被废弃了，在API 29以后返回的文件路径不可直接访问了
                    val dirPath = Environment.getExternalStorageDirectory().path
                    val dir = File("$dirPath/AndroidDevMemo")
                    if (dir.exists().not()) {
                        dir.mkdir()
                    }
                    val file = File(dir, SD_CARD_FILE_NAME)
                    writeFile(file)
                    printResult("sd卡写: $dirPath \n\n")
                }
            }
            PERMISSION_REQUEST_CODE_SAVE_TO_GALLERY->{
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    saveImageOfRaw(R.raw.my_family)
                }
            }
        }
    }

    private fun writeFile(file: File) {
//                    openFileOutput()
        FileOutputStream(file, false).use {
            it.write((DEMO_CONTENT + "-" + System.currentTimeMillis()).toByteArray())
        }
    }

    private fun saveImageOfRaw(resId: Int) {
        val inputStream = resources.openRawResource(resId)
        //路径必须是存储在外部存储的非私有路径下才能在相册里面展示
//        val dir = this@StorageActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES) 不可以
//        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) 可以
        val dirPath = Environment.getExternalStorageDirectory().path
        val dir = File("$dirPath/AndroidDevMemo")
        if (dir.exists().not()) {
            dir.mkdir()
        }

        val file = File(dir, "mySon.jpg")
        val data = ByteArray(inputStream.available())
        inputStream.read(data)
        FileOutputStream(file).use {
            it.write(data)
        }
        MediaScannerConnection.scanFile(this@StorageActivity, arrayOf(file.toString()), null,
            object : MediaScannerConnection.OnScanCompletedListener {
                override fun onScanCompleted(path: String?, uri: Uri?) {
                    printResult("Scanned $path:->uri=$uri\n\n")
                }
            })
    }

    private fun readFile(pathName: String): String {
        return try {
            val file = File(pathName)
            FileInputStream(file).use {
                return String(it.readBytes())
            }
        } catch (e: Exception) {
            ""
        }
    }

    private fun printResult(result: String) {
        sb.append(result)
        tvResult.text = sb.toString()
    }

    companion object {
        private val DEMO_CONTENT = "hello world"
        private val INTERNAL_FILE_NAME = "internal.txt"
        private val INTERNAL_CATCH_FILE_NAME = "internalCatch.txt"
        private val EXTERNAL_FILE_NAME = "external.txt"
        private val EXTERNAL_CATCH_FILE_NAME = "externalCatch.txt"
        private val SD_CARD_FILE_NAME = "sdCard.txt"

        private val PERMISSION_REQUEST_CODE = 100
        private val PERMISSION_REQUEST_CODE_SAVE_TO_GALLERY = 101
    }
}
