package top.ss007.androiddevmemo.fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import top.ss007.androiddevmemo.R

class FragmentDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_demo)

        val fa: FragmentA = Navigator.newFragmentInstance(FragmentA::class.java, Bundle())
        Navigator.open(
            supportFragmentManager,
            R.id.frg_container,
            fa,
            FragmentA::class.java.simpleName,
            true,
            false,
            TAG_TRANSATION_A
            )
    }

    companion object {
        val TAG_TRANSATION_A = "TAG_TRANSATION_A"
    }
}
