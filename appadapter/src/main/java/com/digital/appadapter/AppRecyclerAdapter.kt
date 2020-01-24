package com.digital.appadapter

interface AppRecyclerAdapter<T>  {
    var callBack: OnItemClick<T>?
    fun getItemModel(pos:Int):T?
}