package com.digital.appadapterdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import com.digital.appadapter.AppAdapter
import com.digital.appadapter.AppPagedListAdapter
import com.digital.appadapter.AppViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutView = LayoutInflater.from(this).inflate(R.layout.item, null, false)
        val diff = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return false
            }

        }
        val adapter = AppPagedListAdapter(diff, { p, vType ->
            //onCreateVH
            return@AppPagedListAdapter AppPagedListAdapter.AppVH(layoutView)
        }, {
            //onBind
            itemTVOne.text = "item $it"
            itemTVTwo.text = "item2 $it"
        })


        recycler.adapter = AppAdapter<String>(R.layout.item, MVH::class.java).also {
            it.list = listOf("One","Two","Three")
        }

        recycler.adapter = AppAdapter<String>(R.layout.item){
            //onBind
            val ccccc:Int  = 11002
            println(ccccc)
            itemTVOne.text = "item: $it"
        }.also {
            it.list = listOf("One","Two","Three")
        }

        recycler.adapter = AppAdapterOne().also {
            it.list = listOf("One","Two","Three")
        }


    }
    class MVH(override val containerView: View) : AppViewHolder(containerView),LayoutContainer {
        override fun onBind(position: Int) {
            itemTVOne.text = "item: $position"
        }

    }
}
