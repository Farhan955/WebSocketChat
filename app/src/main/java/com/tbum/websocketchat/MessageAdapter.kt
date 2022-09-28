package com.tbum.websocketchat

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.tbum.websocketchat.databinding.ItemMsgBinding

/**
 * Created by FA on 09/28/2022.
 */

class MessageAdapter(var context: Context, var arrayList: ArrayList<Message>) :


    RecyclerView.Adapter<MessageAdapter.VH?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_msg, parent, false)
        return VH(view)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: VH, position: Int) {

        val obj = arrayList[position]

        holder.binding.run {

            if (obj.fromServer!!) {
                tvHisMsg.text = obj.msg
                tvMyMsg.visibility=View.GONE
                tvHisMsg.visibility=View.VISIBLE
            } else {
                tvMyMsg.text = obj.msg
                tvMyMsg.visibility=View.VISIBLE
                tvHisMsg.visibility=View.GONE
            }
        }


    }


    fun addItem(message: Message) {
        arrayList.add(message)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemMsgBinding.bind(itemView)

    }


}
