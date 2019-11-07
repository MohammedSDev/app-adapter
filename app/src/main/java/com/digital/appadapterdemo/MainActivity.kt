package com.digital.appadapterdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.digital.appadapter.AppAdapter
import com.digital.appadapter.AppPagedListAdapter
import com.digital.appadapter.AppViewHolder
import com.digital.appadapter.appInflate
import com.digital.appadapterdemo.adapter.AppAdapterTwo
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.*

//import kotlinx.android.synthetic.main.item.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            val v = p.appInflate(R.layout.item)
            return@AppPagedListAdapter object :AppViewHolder(v){
                override fun onBind(position: Int) {

                }

            }
        })

        //way:1
        recycler.adapter = AppAdapter<String>(R.layout.item, MVH::class.java).also {
            it.list = listOf("One","Two","Three")
        }

        //way:2
        recycler.adapter = AppAdapter<String>(R.layout.item){
            //onBind
            get<TextView>(R.id.itemTVOne).text = "item with onBind you: $it"
        }.also {
            it.list = listOf("One","Two","Three")
        }

        //way:3
        recycler.adapter = AppAdapterTwo().also {
            it.list = listOf("One","Two","Three")
        }

        //way:4
        recycler.adapter = AppAdapterThree().also {
            it.list = listOf("One","Two","Three")
        }

        //way:5
        recycler.adapter = AppAdapter<String> { parent, viewType ->
            val vv = parent.appInflate(R.layout.item)
            return@AppAdapter object : AppViewHolder(vv) {
                override fun onBind(position: Int) {
                    get<TextView>(R.id.itemTVOne).text = "recycler.adapter: $position"
                }
            }
        }.also {
            it.list = listOf("One", "Two", "Three")
        }
    }

    class MVH(override val containerView: View) : AppViewHolder(containerView), LayoutContainer {
        override fun onBind(position: Int) {
            itemTVOne.text = "item MVH: $position"
        }

    }
}
