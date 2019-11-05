package com.digital.appadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AppViewHolder(v:View) : RecyclerView.ViewHolder(v) {

    abstract fun onBind(position:Int)
}