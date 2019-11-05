package com.digital.appadapterdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.digital.appadapter.AppPagedListAdapter
import com.digital.appadapter.AppViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item.*

class AppPagedListAdapterOne : AppPagedListAdapter<String>(diff) {

    companion object {
        val diff = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return false
            }

        }
    }

    class MyVH(override val containerView: View):AppViewHolder(containerView),LayoutContainer{

        override fun onBind(position: Int) {

        }
    }

    override fun onCreateBasicVH(parent: ViewGroup, viewType: Int): AppViewHolder {
//        return super.onCreateBasicVH(parent, viewType)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)

//        return object:AppViewHolder(view),LayoutContainer{
//            override val containerView: View?
//                get() = view
//
//            override fun onBind(position: Int) {
//
//                itemTVOne
//            }
//        }
        return MyVH(view)
    }

    override fun onCreateLVH(parent: ViewGroup, viewType: Int): AppViewHolder {
        return super.onCreateLVH(parent, viewType)
    }


}