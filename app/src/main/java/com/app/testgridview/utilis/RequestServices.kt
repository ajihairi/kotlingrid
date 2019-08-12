package com.app.testgridview.utilis

import com.app.testgridview.models.messages.GetImageMessage
import com.app.testgridview.models.responses.GetImageResponse
import io.reactivex.Observable
import retrofit2.http.*

interface RequestServices {

    companion object{
        const val BASE_URL = "https://api.imgur.com"
    }

    @Headers("Content-Type: application/json")
    @GET("/3/gallery/r/{category}/{page}")
    fun getImages(
        @Header("Authorization") string: String,
        @Path("page")page : String,
        @Path("category")category: String
    ):Observable<GetImageResponse>

}