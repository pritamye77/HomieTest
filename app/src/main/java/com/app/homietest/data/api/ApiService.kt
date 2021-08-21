package homie.app.pritam.data.api

import io.reactivex.Single
import homie.app.pritam.BuildConfig
import homie.app.pritam.data.model.PixabayData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(
        "?key=" + BuildConfig.API_CREDENTIAL + "&image_type=photo&pretty=true"
    )
    fun getImages(
        @Query("q") input: String?,
    ): Single<PixabayData>

}