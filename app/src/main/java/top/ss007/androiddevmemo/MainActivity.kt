package top.ss007.androiddevmemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import top.ss007.androiddevmemo.animation.MotionActivity
import top.ss007.androiddevmemo.architectureComponents.JetpackActivity
import top.ss007.androiddevmemo.databinding.DataBindingActivity
import top.ss007.androiddevmemo.fragments.FragmentDemoActivity
import top.ss007.androiddevmemo.intents.IntentDemoActivity
import top.ss007.androiddevmemo.permission.PermissionActivity
import top.ss007.androiddevmemo.shareFileBetweenApps.ShareFileActivity
import top.ss007.androiddevmemo.storage.StorageActivity

class MainActivity : AppCompatActivity() {
    private lateinit var currentLayoutType: LayoutManagerType
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var dataSet: List<MemoData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.rv_list)
        layoutManager = LinearLayoutManager(this)
        currentLayoutType = LayoutManagerType.LINER_LAYOUT_MANAGER
        setRecyclerViewLayoutManager(currentLayoutType)
        dataSet =
            listOf(
                MemoData(1, "存储", MemoType.STORAGE),
                MemoData(2, "文件分享", MemoType.FILE_SHARE),
                MemoData(3, "Fragment", MemoType.FRAGMENT),
                MemoData(4, "Intent", MemoType.INTENT),
                MemoData(5,"Jetpack",MemoType.ARCHITECTURE),
                MemoData(6,"动画",MemoType.ANIMATION),
                MemoData(7,"DataBinding",MemoType.DATA_BINDING),
                MemoData(8,"权限演示",MemoType.PERMISSION)
            )
        val adapter = CustomAdapter(dataSet)
        adapter.itemClickListener = object : CustomAdapter.OnItemClickListener {
            override fun onItemClicked(view: View, data: MemoData) {
                when(data.type){
                    MemoType.STORAGE->{
                        startActivity(Intent(this@MainActivity,StorageActivity::class.java))
                    }
                    MemoType.FILE_SHARE->{
                         startActivity(Intent(this@MainActivity,ShareFileActivity::class.java))
                    }
                    MemoType.FRAGMENT->{
                        startActivity(Intent(this@MainActivity,FragmentDemoActivity::class.java))
                    }
                    MemoType.INTENT->{
                        startActivity(Intent(this@MainActivity,IntentDemoActivity::class.java))
                    }
                    MemoType.ARCHITECTURE->{
                        startActivity(Intent(this@MainActivity,JetpackActivity::class.java))
                    }
                    MemoType.ANIMATION->{
                        startActivity(Intent(this@MainActivity,MotionActivity::class.java))
                    }
                    MemoType.DATA_BINDING->{
                        startActivity(Intent(this@MainActivity,DataBindingActivity::class.java))
                    }
                    MemoType.PERMISSION->{
                        startActivity(Intent(this@MainActivity,PermissionActivity::class.java))
                    }


                }
//                Toast.makeText(this@MainActivity, data.toString(), Toast.LENGTH_LONG).show()
            }
        }
        recyclerView.adapter = adapter
    }

    private fun setRecyclerViewLayoutManager(layoutManagerType: LayoutManagerType) {
        var scrollPosition = 0
        if (recyclerView.layoutManager != null) {
            scrollPosition =
                (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        }
        when (layoutManagerType) {
            LayoutManagerType.LINER_LAYOUT_MANAGER -> {
                layoutManager = LinearLayoutManager(this@MainActivity)
                currentLayoutType = LayoutManagerType.LINER_LAYOUT_MANAGER
            }
            LayoutManagerType.GRID_LAYOUT_MANGER -> {
                layoutManager = GridLayoutManager(this@MainActivity, SPAN_COUNT)
                currentLayoutType = LayoutManagerType.GRID_LAYOUT_MANGER
            }
        }

        with(recyclerView) {
            layoutManager = this@MainActivity.layoutManager
            scrollToPosition(scrollPosition)
        }
    }

    companion object {
        private val TAG = "MainActivity"
        private val SPAN_COUNT = 2
    }

    enum class LayoutManagerType {
        GRID_LAYOUT_MANGER,
        LINER_LAYOUT_MANAGER
    }
}
