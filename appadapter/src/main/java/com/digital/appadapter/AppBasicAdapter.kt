package com.digital.appadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalArgumentException

// VH : AppViewHolder, LVH : AppViewHolder
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

    final override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(position)
    }

    final override fun getItemCount(): Int {
        return getCount()
    }

    /**
     * get Item Count for AppAdapter
     * default: list.size
     * */
    open fun getCount():Int = list.size
}