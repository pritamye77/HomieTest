package homie.app.pritam.data.api

import homie.app.pritam.di.component.ApiComponent
import homie.app.pritam.di.component.DaggerApiComponent
import javax.inject.Inject

class ApiHelper {

    @Inject
    lateinit var apiService: ApiService

    init {
        val apiComponent: ApiComponent = DaggerApiComponent.create()
        apiComponent.inject(this)
    }

    fun getImages(input: String, page: Int) = apiService.getImages(input)
    //fun getImages(input: String, page: Int) = apiService.getImages(input, page)

}