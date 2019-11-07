package com.digital.appadapterdemo.adapter

import android.widget.TextView
import com.digital.appadapter.AppAdapter
import com.digital.appadapter.AppViewHolder
import com.digital.appadapter.appInflate
import com.digital.appadapterdemo.R

class AppAdapterTwo : AppAdapter<String> ({parent, viewType ->
    val vv = parent.appInflate(R.layout.item)
    object : AppViewHolder(vv) {
        override fun onBind(position: Int) {

            val gg = 3003
            println(gg)

            get<TextView>(R.id.itemTVTwo).text = "AppAdapterTwo: $position"
        }


    }
})