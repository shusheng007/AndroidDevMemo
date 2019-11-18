package top.ss007.androiddevmemo.utils

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

/**
 * Created by Ben.Wang
 *
 * @author Ben.Wang
 * @modifier
 * @createDate 2019/11/15 17:25
 * @description
 */
object FileUtils {

     fun getUriForFile(context: Context, file:File): Uri {
        if (Build.VERSION.SDK_INT>24){
            return FileProvider.getUriForFile(context,context.packageName+".fileProvider",file)
        }
        return Uri.fromFile(file)
    }

}