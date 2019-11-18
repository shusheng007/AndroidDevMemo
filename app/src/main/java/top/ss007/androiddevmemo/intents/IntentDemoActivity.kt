package top.ss007.androiddevmemo.intents

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import top.ss007.androiddevmemo.R
import top.ss007.androiddevmemo.utils.FileUtils
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class IntentDemoActivity : AppCompatActivity() {
    private var currentPhotoPath = ""
    private lateinit var imageView:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent_demo)
        imageView=findViewById(R.id.imageView)
        findViewById<Button>(R.id.btn_alarm).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_timer).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_calender).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_capture_img).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_get_content).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_open_map).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_dial).setOnClickListener(listener)
    }

    val listener = View.OnClickListener { v ->
        when (v?.id) {
            R.id.btn_alarm -> {
                createAlarm("go home", 15, 26)
            }
            R.id.btn_timer -> {
                createTimer("meeting with shopEx", 10)
            }
            R.id.btn_calender -> {
                val startTime = System.currentTimeMillis() + 1000 * 60
                addEventToCalender(
                    "test calender", "tian jin LJZ",
                    startTime, startTime + 1000 * 60 * 10
                )
            }
            R.id.btn_capture_img -> {
                ActivityCompat.requestPermissions(
                    this@IntentDemoActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
            }
            R.id.btn_get_content->{
                selectContent("image/*")
            }
            R.id.btn_open_map->{
                //本例为天津的百度地图坐标
                Uri.parse("geo:39.140371,117.21729?z=12").run {
                    showMap(this)
                }
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                if (Activity.RESULT_OK == resultCode) {
                    //如果启动相机时设置了putExtra(MediaStore.EXTRA_OUTPUT, fileUri),在mix2 中data为null
                    val thumbnail: Bitmap? = data?.getParcelableExtra("data")
                    imageView.setImageURI(Uri.fromFile(File(currentPhotoPath)))
                }
            }
            REQUEST_IMAGE_GET->{
                if (Activity.RESULT_OK == resultCode) {
                    val thumbnail: Bitmap? = data?.getParcelableExtra("data")
                    val fullPhotoUri: Uri?= data?.data
                    imageView.setImageURI(fullPhotoUri)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val filename: String = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA)
                        .format(Date()).toString() + ".jpg"
                    capturePhoto(filename)
                }
            }
        }
    }

    private fun createAlarm(message: String, hour: Int, minutes: Int) {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_HOUR, hour)
            putExtra(AlarmClock.EXTRA_MINUTES, minutes)
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_SKIP_UI, false)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun createTimer(message: String, seconds: Int) {
        val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_LENGTH, seconds)
            putExtra(AlarmClock.EXTRA_SKIP_UI, false)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun addEventToCalender(title: String, location: String, begin: Long, end: Long) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, title)
            putExtra(CalendarContract.Events.EVENT_LOCATION, location)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end)
            putExtra(
                CalendarContract.Events.DESCRIPTION,
                "this is a test for and event to calender"
            )
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    @Throws(IOException::class)
    private fun capturePhoto(targetFilename: String) {
        val fileDic=File("${this@IntentDemoActivity.externalCacheDir?.path}/images/112")
        if (fileDic.exists().not()){
            fileDic.mkdir()
        }
        val file = File(fileDic,targetFilename).apply {
            currentPhotoPath = absolutePath
        }
        val fileUri = FileUtils.getUriForFile(this@IntentDemoActivity, file)
        Log.d(TAG, "filePath:${file.absolutePath} | fileUri:${fileUri.toString()}")

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun selectContent(miniType:String){
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = miniType
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET)
        }
    }

    private fun showMap(geoLocation: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = geoLocation
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
    companion object {
        val TAG = "IntentDemoActivity"
        val REQUEST_IMAGE_CAPTURE = 1
        val REQUEST_IMAGE_GET = 2
        val PERMISSION_REQUEST_CODE = 100
    }

}
