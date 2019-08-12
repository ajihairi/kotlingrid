package com.app.testgridview.viewmodels

import android.app.Dialog
import android.content.Context
import android.widget.AbsListView
import android.widget.GridView
import androidx.databinding.BindingAdapter
import com.app.testgridview.models.responses.GetImageResponse
import com.app.testgridview.utilis.ClientInstance
import com.app.testgridview.utilis.RequestServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(var context: Context) : BaseViewMOdel {

    lateinit var gridView : GridView

    lateinit var subscriptionGetProducts : Disposable

    lateinit var requestServices: RequestServices

    fun loadFirstImages(page : String, category: String,listener: GenerateImageListener){
        requestServices = ClientInstance
            .getUnsafetyRetrofitInstance(RequestServices.BASE_URL)
            .create(RequestServices::class.java)
        subscriptionGetProducts = requestServices
            .getImages("Client-ID 308906dca8e14a5",page,category)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response: GetImageResponse? ->
                listener.onResponse(response)
            },{ throwable: Throwable? ->
                listener.onFailure(throwable!!)
            })
    }

    fun fetcheMoreImage(page : String, category: String, listener: GenerateImageListener){
        requestServices = ClientInstance
            .getUnsafetyRetrofitInstance(RequestServices.BASE_URL)
            .create(RequestServices::class.java)
        subscriptionGetProducts = requestServices
            .getImages("Client-ID 308906dca8e14a5",page,category)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response: GetImageResponse? ->
                listener.onResponse(response)
            },{ throwable: Throwable? ->
                listener.onFailure(throwable!!)
            })
    }

    interface GenerateImageListener {
        fun onResponse(response: GetImageResponse?)
        fun onFailure(throwable: Throwable)
    }



    override fun destroy() {}


}