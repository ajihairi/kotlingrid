package com.app.testgridview.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ImageModel(@SerializedName("link")@Expose var imageLink : String)