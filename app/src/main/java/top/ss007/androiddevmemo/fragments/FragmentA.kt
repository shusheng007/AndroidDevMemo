package top.ss007.androiddevmemo.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import top.ss007.androiddevmemo.R

/**
 * A simple [Fragment] subclass.
 */
class FragmentA : Fragment() {
    private lateinit var btnNext:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnNext=view.findViewById(R.id.btn_a_next)
        btnNext.setOnClickListener {
            val fb: FragmentB = Navigator.newFragmentInstance(FragmentB::class.java, Bundle())
            Navigator.open(
                fb,
                false,
                false)
        }
    }

}
