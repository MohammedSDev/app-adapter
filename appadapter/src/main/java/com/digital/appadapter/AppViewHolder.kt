package com.digital.appadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AppViewHolder(val v:View) : RecyclerView.ViewHolder(v) {

    abstract fun onBind()
}