# app-adapter
![Release](https://jitpack.io/v/clickapps-android/app-adapter.svg)


App-adapter is an easy,flexible library for you Android customized RecyclerView.Adapter &amp;  PagedListAdapter for an easy use.
you can use it directally inside you Activity/Fragment or in a separate class.just create you item design 
and use it safely,faster along with views cache.



# add dependence
in project level build.gradle

```gradle
allprojects {
  repositories {
                  google()
                  jcenter()
                    //...
                  maven { url 'https://jitpack.io' }
  }
}
```
in app level build.gradle
```gradle
dependencies {
        implementation 'com.github.clickapps-android:app-adapter:<last-build>'
}
```

#How to Ues

here is some examples...to name a few

# way 1
using `apply plugin: 'kotlin-android-extensions'`

just create your ViewHolder class anywhere inside your **Activity/Fragment**.

```kotlin
class MainActivity : AppCompatActivity() {

  ...

  recycler.adapter = AppAdapter<String>(R.layout.item, MVH::class.java).also {
            it.list = listOf("One", "Two", "Three")
  }

  ...

  class MVH(override val containerView: View) : AppViewHolder(containerView), LayoutContainer {
      override fun onBind(position: Int) {
          itemTVOne.text = "item MVH: $position"
      }

  }

}
```

# Way 2
you should use `get<VIEW_TYPE>`(view_id) to access your view faster & safely
```kotlin
recycler.adapter = AppAdapter<String>(R.layout.item) {
    //onBind
    get<TextView>(R.id.itemTVOne).text = "item with onBind you: $it"
}.also {
    it.list = listOf("One", "Two", "Three")
}

```
# Way 3
useing separate ViewHolder class
```kotlin
class AppAdapterTwo : AppAdapter<String>({ parent, viewType ->
  val vv = parent.appInflate(R.layout.item)
  object : AppViewHolder(vv) {
      override fun onBind(position: Int) {
          get<TextView>(R.id.itemTVTwo).text = "AppAdapterTwo: $position"
      }
  }
})
```
then in your **Activity/Fragment/View**.
```kotlin
recycler.adapter = AppAdapterTwo().also {
    it.list = listOf("One", "Two", "Three")
}
```

# Way 4
using `apply plugin: 'kotlin-android-extensions'`
using extends
```kotlin
class AppAdapterThree : AppAdapter<String>() {


    class MyViewHolder(override val containerView: View):AppViewHolder(containerView),LayoutContainer{
        override fun onBind(position: Int) {
            itemTVOne.text = "item AppAdapterThree: $position"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false))
    }


}
```
```kotlin
recycler.adapter = AppAdapterThree().also {
    it.list = listOf("One", "Two", "Three")
}
```

# Way 5

```kotlin
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
```

# Way 6
using multi view types
```kotlin
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
```

# Way 7
using multi view types,views id must be unique from each other
```kotlin
recycler.adapter = AppAdapter<String>({ pos ->
    return@AppAdapter when (pos) {
        0 -> R.layout.item
        1 -> R.layout.item2
        else -> R.layout.item3
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
```


# PagedListAdapter


```kotlin
//create your DiffUtil.ItemCallback
val diff = object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return false
    }

}
//your adapter here
val adapter = AppPagedListAdapter(diff, { p, vType ->
    //onCreateVH
    val v = p.appInflate(R.layout.item)
    return@AppPagedListAdapter object : AppViewHolder(v) {
        override fun onBind(position: Int) {
            get<TextView>(R.id.itemTVOne).text = "..."
        }

    }
})
recycler.adapter = adapter
```


# PagedListAdapter with multi view types



```kotlin
//create your DiffUtil.ItemCallback
val diff = object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return false
    }

}
//your adapter here
val adapter2 = AppPagedListAdapter(diff,
    { pos: Int ->
        return@AppPagedListAdapter when (pos) {
            0 -> R.layout.item
            1 -> R.layout.item2
            else -> R.layout.item3
                
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

```


# 
Enjoy using app-adapter library,report any bugs you found, or even drop me an email gg.goo.mobile@gmail.com


