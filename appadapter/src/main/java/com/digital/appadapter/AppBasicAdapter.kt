package com.digital.appadapter

import androidx.recyclerview.widget.RecyclerView

abstract class AppBasicAdapter<T, VH : AppViewHolder<T>> : RecyclerView.Adapter<VH>(),AppRecyclerAdapter<T> {

    override var callBack: OnItemClick<T>? = null
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
        holder.onBind(getItem(position))
    }

     override fun getItemCount(): Int {
        return getCount()
    }

    fun getItem(position:Int):T{
        return list[position]
    }
    /**
     * get Item Count for AppAdapter
     * default: list.size
     * */
    open fun getCount():Int = list.size

    final override fun getItemModel(pos: Int): T = list[pos]


    fun setCallback(callback:OnItemClick<T>):AppBasicAdapter<T, VH>{
        this.callBack = callback
        return this
    }
    fun setList(items:List<T>):AppBasicAdapter<T, VH>{
        this.list = items
        return this
    }
}