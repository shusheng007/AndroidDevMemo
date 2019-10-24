package top.ss007.androiddevmemo.storage

import android.Manifest
import android.content.pm.PackageManager
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
        findViewById<Button>(R.id.btn_read_sd_card).setOnClickListener(listener)
    }

    private val listener = object : View.OnClickListener {
        override fun onClick(v: View?) {
            when (v?.id) {
                //自由APP自己可以查看
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
                //自由APP自己可以查看，当系统存储不足时会删除，所以需要用户自己维护缓存的size
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
                //所有APP可见，但是需要知道具体的路径，API>19以后不需要写入及读取存储的权限
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
                R.id.btn_write_sd_card->{
                    if (Environment.getExternalStorageState()==Environment.MEDIA_MOUNTED){
                        ActivityCompat.requestPermissions(this@StorageActivity,arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),PERMISSION_REQUEST_CODE)
                    }
                }
            }
            tvResult.text = sb.toString()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_REQUEST_CODE->{
                if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //被废弃了，在API 29以后返回的文件路径不可直接访问了
                    val dirPath=Environment.getExternalStorageDirectory().path
                    val dir =File("$dirPath/ShuSheng007")
                    if (dir.exists().not()){
                        dir.mkdir()
                    }
                    val file = File(dir, SD_CARD_FILE_NAME)
                    writeFile(file)
                    sb.append("sd卡写: $dirPath \n\n")
                    tvResult.text = sb.toString()
                }
            }
        }
    }
    private fun writeFile(file: File) {
//                    openFileOutput()
        FileOutputStream(file, false).use {
            it.write((DEMO_CONTENT+"-"+System.currentTimeMillis()).toByteArray())
        }
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

    companion object {
        private val DEMO_CONTENT = "hello world"
        private val INTERNAL_FILE_NAME = "internal.txt"
        private val INTERNAL_CATCH_FILE_NAME = "internalCatch.txt"
        private val EXTERNAL_FILE_NAME = "external.txt"
        private val EXTERNAL_CATCH_FILE_NAME = "externalCatch.txt"
        private val SD_CARD_FILE_NAME = "sdCard.txt"

        private val PERMISSION_REQUEST_CODE = 100
    }
}
