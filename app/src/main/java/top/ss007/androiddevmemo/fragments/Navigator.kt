package top.ss007.androiddevmemo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Created by Ben.Wang
 *
 * @author Ben.Wang
 * @modifier
 * @createDate 2019/11/6 17:02
 * @description
 */
object Navigator {

    fun <T : Fragment> newFragmentInstance( clazz: Class<T>,  bundle: Bundle?): T {
        with(clazz.newInstance()){
            arguments=bundle
            return this
        }
    }

    /**
     * Opens fragment using supplied fragment manager and parameters
     *
     * @param manager        FragmentManager to show the Fragment with
     * @param fragment       Fragment to show
     * @param tag            Tag to use in the FragmentTransaction
     * @param asRoot         If TRUE, backstack will be popped before adding fragment
     * @param now            If TRUE, commitNow() will be used on the FragmentTransaction instead of commit()
     * @param transactionTag a tag that the transaction will be marked with, so later it
     * can be popped all the way to this transaction
     */
    fun open(
        manager: FragmentManager?,
        containerId: Int,
        fragment: Fragment,
        tag: String,
        asRoot: Boolean,
        now: Boolean,
        transactionTag: String?
    ): Boolean {
        if (manager != null) {
            if (manager.isStateSaved) {
                return false
            }

            val ft = manager!!.beginTransaction()

            if (asRoot) {
                manager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE)
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

}