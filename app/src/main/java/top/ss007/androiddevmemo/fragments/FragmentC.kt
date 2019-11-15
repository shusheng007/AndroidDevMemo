package top.ss007.androiddevmemo.fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import top.ss007.androiddevmemo.R

class FragmentC : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG,"onCreateView")
        return inflater.inflate(R.layout.fragment_c, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvContent=view.findViewById<TextView>(R.id.tv_c_content)
        tvContent.text="backStackEntryCount: ${Navigator.mFragmentManager?.backStackEntryCount}"
    }

    companion object {
        val TAG ="FragmentC"
    }
}
