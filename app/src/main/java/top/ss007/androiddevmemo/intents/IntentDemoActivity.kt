package top.ss007.androiddevmemo.intents

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import top.ss007.androiddevmemo.R
import top.ss007.androiddevmemo.storage.StorageActivity
import top.ss007.androiddevmemo.utils.FileUtils
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class IntentDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent_demo)
        findViewById<Button>(R.id.btn_alarm).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_timer).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_calender).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_capture_img).setOnClickListener(listener)
        findViewById<Button>(R.id.btn_capture_video).setOnClickListener(listener)
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
            R.id.btn_capture_img->{
                ActivityCompat.requestPermissions(
                    this@IntentDemoActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_IMAGE_CAPTURE->{
                if (Activity.RESULT_OK==resultCode){
                    val thumbnail: Bitmap? = data?.getParcelableExtra("data")

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

    private  fun capturePhoto(targetFilename: String) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT,
                FileUtils.getUriForFile(this@IntentDemoActivity,
                    File(this@IntentDemoActivity.getExternalFilesDir(null),targetFilename)))
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }
    }



    companion object{
        val REQUEST_IMAGE_CAPTURE=1
        val PERMISSION_REQUEST_CODE=100
    }

}
