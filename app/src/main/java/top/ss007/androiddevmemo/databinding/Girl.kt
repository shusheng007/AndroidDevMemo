package top.ss007.androiddevmemo.databinding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import top.ss007.androiddevmemo.BR


/**
 * Copyright (C) 2020 shusheng007
 * 完全享有此软件的著作权，违者必究
 *
 * @author       shusheng007
 * @modifier
 * @createDate   2020/5/23 13:33
 * @version      1.0
 * @description
 */
class Girl : BaseObservable() {

    @get:Bindable
    var name: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    @get:Bindable
    var age: Int = 18
        set(value) {
            field = value
            notifyPropertyChanged(BR.age)
        }
}