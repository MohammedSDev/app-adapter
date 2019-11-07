package com.digital.appadapter

import androidx.recyclerview.widget.RecyclerView

abstract class AppBasicAdapter<T, VH : AppViewHolder> : RecyclerView.Adapter<VH>() {

    var autoNotify:Boolean = true
    var list: List<T> = mutableListOf()
    set(value) {
        field = value
        if(autoNotify)
            notifyDataSetChanged()
    }


//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
//
//    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(position)
    }

     override fun getItemCount(): Int {
        return getCount()
    }

    /**
     * get Item Count for AppAdapter
     * default: list.size
     * */
    open fun getCount():Int = list.size
}