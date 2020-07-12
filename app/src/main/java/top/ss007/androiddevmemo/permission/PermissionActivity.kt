package top.ss007.androiddevmemo.permission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import top.ss007.androiddevmemo.R

class PermissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        findViewById<Button>(R.id.btn_open_setting).setOnClickListener {
            if (ContextCompat.checkSelfPermission(MainActivity@ this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                //请求权限

                //上次是否被拒绝
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity@ this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //由于上次被用户拒绝了，这次好好向用户解释一下，为什么需要这个权限
                    AlertDialog.Builder(MainActivity@ this).setMessage("爷，您要想使用这个服务，这个权限是必须的")
                        .setPositiveButton("允许权限") { dialog, which ->
                            ActivityCompat.requestPermissions(MainActivity@ this,
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                110)
                        }
                        .create()
                        .show()
                } else {
                    //如果是首次申请
                    ActivityCompat.requestPermissions(MainActivity@ this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        110)
                }
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Toast.makeText(MainActivity@ this, "requestCode：$requestCode|permissions:${permissions.joinToString(" ")}" +
                "|grantResults:${grantResults.joinToString(" ")}", Toast.LENGTH_LONG).show();
    }
}