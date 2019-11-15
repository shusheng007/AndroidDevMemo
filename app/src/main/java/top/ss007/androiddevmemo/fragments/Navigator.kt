package top.ss007.androiddevmemo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import top.ss007.androiddevmemo.R

/**
 * Created by Ben.Wang
 *
 * @author Ben.Wang
 * @modifier
 * @createDate 2019/11/6 17:02
 * @description
 */
object Navigator {
    var mFragmentManager: FragmentManager? = null


    fun <T : Fragment> newFragmentInstance(clazz: Class<T>, bundle: Bundle?): T {
        with(clazz.newInstance()) {
            arguments = bundle
            return this
        }
    }

    /**
     * Opens fragment using supplied fragment manager and parameters
     *
     * @param manager        FragmentManager to show the Fragment with
     * @param fragment       Fragment to show
     * @param tag            Tag to use in the FragmentTransaction
     * @param asRoot         If TRUE, backStack will be popped before adding fragment
     * @param popUpStack     If true, pop the top entry from the backStack
     * @param now            If TRUE, commitNow() will be used on the FragmentTransaction instead of commit()
     * @param transactionTag a tag that the transaction will be marked with, so later it can be popped all the way to this transaction
     */
    fun open(
        manager: FragmentManager?,
        containerId: Int,
        fragment: Fragment,
        tag: String,
        asRoot: Boolean,
        popUpStack:Boolean,
        now: Boolean,
        transactionTag: String?
    ): Boolean {
        if (manager != null) {
            if (manager.isStateSaved) {
                return false
            }

            val ft = manager.beginTransaction()

            if (popUpStack){
                //将transactionTag及以上的stackEntry出栈
                manager.popBackStackImmediate(transactionTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }else if (asRoot) {
                //将栈清空
                manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }

            ft.replace(containerId, fragment, tag)
            if (!asRoot) {
                ft.addToBackStack(transactionTag)
            }

            ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)

            if (now) {
                ft.commitNow()
            } else {
                ft.commit()
            }
            return true
        }
        return false
    }

    fun open(fragment: Fragment, asRoot: Boolean, popUpStack: Boolean) {
        open(
            mFragmentManager, R.id.frg_container, fragment,
            fragment::class.java.simpleName, asRoot, popUpStack,
            false,
            "TRAN_${fragment::class.java.simpleName}"
        )
    }

}