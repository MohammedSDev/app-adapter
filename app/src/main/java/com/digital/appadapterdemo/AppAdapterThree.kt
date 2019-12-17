package com.digital.appadapterdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.digital.appadapter.AppAdapter
import com.digital.appadapter.AppBasicAdapter
import com.digital.appadapter.AppPagedListAdapter
import com.digital.appadapter.AppViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item.*

class AppAdapterThree /*: AppAdapter<String>() {


    class MyViewHolder(override val containerView: View):AppViewHolder(containerView),LayoutContainer{
        override fun onBind(position: Int) {
            itemTVOne.text = "item AppAdapterThree: $position"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false))
    }


}*/