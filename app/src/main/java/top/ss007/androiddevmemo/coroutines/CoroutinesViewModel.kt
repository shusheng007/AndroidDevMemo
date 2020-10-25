package top.ss007.androiddevmemo.coroutines

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Copyright (C) 2020 shusheng007
 * 完全享有此软件的著作权，违者必究
 *
 * @author       shusheng007
 * @modifier
 * @createDate   2020/10/24 16:14
 * @version      1.0
 * @description
 */
class CoroutinesViewModel : ViewModel() {

    val nameLiveData = MutableLiveData<String>()

    fun checkTheWomen() {
        viewModelScope.launch(Dispatchers.Main) {
            val name = withContext(Dispatchers.IO) {
                searchFromNet()
            }
            Log.d("coroutine","launch: ${Thread.currentThread().name}")
            nameLiveData.value = name
        }
    }

    //耗时网络请求
    private suspend fun searchFromNet(): String {
        Log.d("coroutine","searchFromNet: ${Thread.currentThread().name}")
        delay(3_000)
        return "ShuSheng007媳妇"
    }
}