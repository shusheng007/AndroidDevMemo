package top.ss007.androiddevmemo.fragments

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import top.ss007.androiddevmemo.R

class FragmentDemoActivity : AppCompatActivity() {
    lateinit var fa: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_demo)
        Navigator.mFragmentManager = supportFragmentManager
        if (savedInstanceState == null) {
            Navigator.mFragmentManager?.addOnBackStackChangedListener {
                Log.d(TAG, Navigator.mFragmentManager?.fragments.toString())
            }
            fa = Navigator.newFragmentInstance(FragmentA::class.java, Bundle())
            Navigator.open(
                fa,
                true,
                false
            )
        } else {
            fa = supportFragmentManager.getFragment(savedInstanceState, KEY_LAST_FRAGMENT)?:Navigator.newFragmentInstance(FragmentA::class.java, Bundle())
            Navigator.open(
                fa,
                false,
                true
            )
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val currentFg=supportFragmentManager.fragments[supportFragmentManager.fragments.lastIndex]
        supportFragmentManager.putFragment(outState, KEY_LAST_FRAGMENT, currentFg)
    }

    companion object {
        val TAG = "FragmentDemoActivity"
        val KEY_LAST_FRAGMENT="KEY_LAST_FRAGMENT"
    }
}
