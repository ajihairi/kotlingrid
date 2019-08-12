package com.app.testgridview.models.responses

import com.app.testgridview.models.GalleryModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetImageResponse(@SerializedName("data")@Expose var data:ArrayList<GalleryModel>)