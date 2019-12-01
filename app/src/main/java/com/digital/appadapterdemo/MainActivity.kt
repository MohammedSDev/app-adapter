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
            return@AppPagedListAdapter object : AppViewHolder(v) {
                override fun onBind(position: Int) {
                    //get<TextView>(R.id.itemTVOne).text = "..."
                }

            }
        })
        recycler.adapter = adapter
        //multi view type
        val adapter2 = AppPagedListAdapter(diff,
            { pos: Int ->
                return@AppPagedListAdapter when (pos) {
                    0 -> R.layout.item
                    1 -> R.layout.item2
                    else ->
                        if (pos % 2 == 0)
                            R.layout.item3
                        else
                            R.layout.item
                }
            }
            , { p, vType ->
                //onCreateVH
                val v = p.appInflate(vType)
                return@AppPagedListAdapter object : AppViewHolder(v) {
                    override fun onBind(position: Int) {
                        when(vType){
                            R.layout.item -> {get<TextView>(R.id.itemTVOne).text = "..1.."}
                            R.layout.item2 -> {get<TextView>(R.id.item2TVOne).text = "..2.."}
                            R.layout.item3 -> {get<TextView>(R.id.item3TVOne).text = "..3.."}
                        }

                    }

                }
            })
        recycler.adapter = adapter

        /**
         * how to ue AppAdapter:
         * */
        //way:1
        recycler.adapter = AppAdapter<String>(R.layout.item, MVH::class.java).also {
            it.list = listOf("One", "Two", "Three")
        }

        //way:2
        recycler.adapter = AppAdapter<String>(R.layout.item) {
            //onBind
            get<TextView>(R.id.itemTVOne).text = "item with onBind you: $it"
        }.also {
            it.list = listOf("One", "Two", "Three")
        }

        //way:3
        recycler.adapter = AppAdapterTwo().also {
            it.list = listOf("One", "Two", "Three")
        }

        //way:4
        recycler.adapter = AppAdapterThree().also {
            it.list = listOf("One", "Two", "Three")
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

        //way:6 : multi type view
        recycler.adapter = AppAdapter<String>({ pos ->
            return@AppAdapter when (pos) {
                0 -> R.layout.item
                1 -> R.layout.item2
                else -> R.layout.item3
            }
        }) { parent, viewType ->

            when (viewType) {
                R.layout.item -> {
                    val vv = parent.appInflate(R.layout.item)
                    object : AppViewHolder(vv) {
                        override fun onBind(position: Int) {
                            get<TextView>(R.id.itemTVOne).text = "recycler.adapter1: $position"
                        }
                    }
                }
                R.layout.item2 -> {
                    val vv = parent.appInflate(R.layout.item2)
                    object : AppViewHolder(vv) {
                        override fun onBind(position: Int) {
                            get<TextView>(R.id.item2TVOne).text = "recycler.adapter2: $position"
                        }
                    }
                }
                else -> {
                    val vv = parent.appInflate(R.layout.item3)
                    object : AppViewHolder(vv) {
                        override fun onBind(position: Int) {
                            get<TextView>(R.id.item3TVOne).text = "recycler.adapter3: $position"
                        }
                    }
                }
            }

        }.also {
            it.list = listOf("One", "Two", "Three", "four", "five", "six", "seven")
        }

        //way:7 : multi type view
        recycler.adapter = AppAdapter<String>({ pos ->
            return@AppAdapter when (pos) {
                0 -> R.layout.item
                1 -> R.layout.item2
                else -> if (pos % 2 == 0)
                    R.layout.item3
                else
                    R.layout.item2
            }
        }) { parent, viewType ->
            val vv = parent.appInflate(viewType)
            object : AppViewHolder(vv) {
                override fun onBind(position: Int) {
                    when (viewType) {
                        R.layout.item -> {
                            get<TextView>(R.id.itemTVOne).text = "recycler.adapter1: $position"
                        }
                        R.layout.item2 -> {
                            get<TextView>(R.id.item2TVOne).text = "recycler.adapter2: $position"
                        }
                        R.layout.item3 -> {
                            get<TextView>(R.id.item3TVOne).text = "recycler.adapter3: $position"
                        }
                    }
                }
            }
        }.also {
            it.list = listOf("One", "Two", "Three", "four", "five", "six", "seven")
        }
    }

    class MVH(override val containerView: View) : AppViewHolder(containerView), LayoutContainer {
        override fun onBind(position: Int) {
            itemTVOne.text = "My OWn item MVH: $position"
            itemTVOne.text = "item MVH: $position"
        }

    }
}
