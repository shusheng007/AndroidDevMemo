package top.ss007.androiddevmemo.databinding

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

/**
 * Copyright (C) 2020 shusheng007
 * 完全享有此软件的著作权，违者必究
 *
 * @author       shusheng007
 * @modifier
 * @createDate   2020/5/23 11:42
 * @version      1.0
 * @description
 */
class DataBindingViewModel : ViewModel() {

    val action: ObservableField<String> = ObservableField("邀请牛翠花晚上一起生猴子")
    val targetGirl: Girl = Girl().apply {
        name = "牛翠花"
        age = 30
    }


    fun seduceGirl(girl: Girl) {
        targetGirl.let {
            it.name = "上官无雪"
            it.age = 18
        }
        action.set("邀请${girl.age.toString() + "岁的" + girl.name}晚上一起看月亮")
    }

    fun bornSon(girl: Girl) {
        targetGirl.let {
            it.name = "牛翠花"
            it.age = 30
        }
        action.set("邀请${girl.age.toString() + "岁的" + girl.name}晚上一起生猴子")
    }


}