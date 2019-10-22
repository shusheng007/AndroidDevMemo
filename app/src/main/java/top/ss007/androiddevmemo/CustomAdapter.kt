package top.ss007.androiddevmemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Ben.Wang
 *
 * @author Ben.Wang
 * @modifier
 * @createDate 2019/10/11 17:30
 * @description
 */
class CustomAdapter(private val dataSet: List<MemoData>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    var itemClickListener:OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.main_row_item, parent, false);
        return ViewHolder(v);
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val memoData:MemoData=dataSet[position]
        holder.tvName.text = memoData.name
        holder.tvName.setOnClickListener {
            itemClickListener?.onItemClicked(holder.tvName,memoData)
        }
    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvName: TextView

        init {
            tvName = v.findViewById(R.id.tv_item_name)
        }
    }

    interface OnItemClickListener{
          fun onItemClicked(view:View,data:MemoData)
    }
}