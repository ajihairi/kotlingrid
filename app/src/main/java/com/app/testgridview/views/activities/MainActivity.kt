package com.app.testgridview.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AbsListView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.app.testgridview.ITEM_TYPE_CONTENT
import com.app.testgridview.ITEM_TYPE_LOADING
import com.app.testgridview.R
import com.app.testgridview.databinding.ActivityMainBinding
import com.app.testgridview.models.User
import com.app.testgridview.models.responses.GetImageResponse
import com.app.testgridview.viewmodels.MainViewModel
import com.app.testgridview.views.adapters.ImageGridAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import android.widget.TabHost



class MainActivity : AppCompatActivity() {

    var emptyData : ArrayList<String> = ArrayList()
    var imageData:ArrayList<String> = ArrayList()
    var typeData : ArrayList<Int> = ArrayList()
    lateinit var binding:ActivityMainBinding
    lateinit var viewModel : MainViewModel
    lateinit var adapter : ImageGridAdapter
    var isLoading : Boolean = false
    var increment:Int=1
    var category : String = "happy"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = MainViewModel(this)
        binding.vmodel = viewModel
        binding.user = User("ajihairi")
        initFirstData()
        btnArt.setOnClickListener { changeCategoryToArt() }
        btnDesign.setOnClickListener { changeCategoryToDesign() }

        val host = findViewById(R.id.tab_host) as TabHost
        host.setup()

        var spec = host.newTabSpec("Tab One")
        spec.setContent(R.id.tab_one_container)
        spec.setIndicator("Posts")
        host.addTab(spec)

        spec = host.newTabSpec("Tab Two")
        spec.setContent(R.id.tab_two_container)
        spec.setIndicator("Add")
        host.addTab(spec)
    }

    private fun initFirstData(){
        viewModel.loadFirstImages(increment.toString(),category,GenerateImage())
    }

    inner class GenerateImage : MainViewModel.GenerateImageListener{
        override fun onResponse(response: GetImageResponse?) {
            try {
                for(base:Int in 0 until response!!.data.size){
                    if(response.data[base] != null) {
                        imageData.add(response.data[base].images)
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
            adapter = ImageGridAdapter(applicationContext, imageData, typeData )
            gridView.adapter = adapter
            gridView.setOnScrollListener(object : AbsListView.OnScrollListener {
                override fun onScroll(
                    view: AbsListView,
                    firstVisibleItem: Int, visibleItemCount: Int,
                    totalItemCount: Int
                ) {
                    val lastItem = firstVisibleItem + visibleItemCount
                    if (lastItem == totalItemCount) {
                        increment++
                        refreshData()
                        if(!isLoading) {
                            viewModel.fetcheMoreImage(increment.toString(), category, GenerateMoreImage())
                            isLoading = true
                        }
                    }
                }

                override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {}
            })
        }
        override fun onFailure(throwable: Throwable) {}
    }

    inner class GenerateMoreImage : MainViewModel.GenerateImageListener{

        override fun onResponse(response: GetImageResponse?) {
            try {
                for(base:Int in 0 until response!!.data.size){
                    if(response.data[base] != null) {
                        imageData.add(response.data[base].images)
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
            addData(imageData, ITEM_TYPE_CONTENT)
            refreshData()
            isLoading = false
//            doAsync {
//                addData(imageData, ITEM_TYPE_CONTENT)
//                Thread.sleep(1000)
//                uiThread {
//                    refreshData()
//                    isLoading = false
//                }
//            }
        }

        override fun onFailure(throwable: Throwable) {
            Toast.makeText(applicationContext,  "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
        }

    }

    fun changeCategoryToArt(){
        category = "art"
        increment = 1
        imageData = ArrayList<String>()
        viewModel.loadFirstImages(increment.toString(),category,GenerateImage())
    }

    fun changeCategoryToDesign(){
        category = "design"
        increment = 1
        imageData = ArrayList<String>()
        viewModel.loadFirstImages(increment.toString(),category,GenerateImage())
    }

    private fun addData(data:ArrayList<String>,
                        dataViewType:Int){
        for(img:Int in 0 until data.size){
            imageData.add(data[img])
        }
        typeData.add(dataViewType)
    }

    private fun refreshData(){
        adapter.refreshData(imageData, typeData)
    }

    private fun removeData(lent : Int){
        imageData.removeAt(lent)
        typeData.removeAt(lent)
    }
}
