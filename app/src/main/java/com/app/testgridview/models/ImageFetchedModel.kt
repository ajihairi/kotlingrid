package com.app.testgridview.models

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import java.util.ArrayList

class ImageFetchedModel : BaseObservable(){
    private val dataGallery = ArrayList<GalleryModel>()
    private val liveDataGallery = MutableLiveData<ArrayList<GalleryModel>>()
}