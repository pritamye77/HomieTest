package homie.app.pritam.data.repository

import io.reactivex.Single
import homie.app.pritam.data.api.ApiHelper
import homie.app.pritam.data.model.PixabayData
import homie.app.pritam.di.component.ApiHelperComponent
import homie.app.pritam.di.component.DaggerApiHelperComponent
import javax.inject.Inject

/**
 * Repository class which has the duty of getting data from network and passes the data to the viewmodel
 */
class ImageRepository : OutputRepository {

    @Inject
    lateinit var apiHelper: ApiHelper

    init {
        val apiHelperComponent: ApiHelperComponent = DaggerApiHelperComponent.create();
        apiHelperComponent.inject(this)
    }

    override fun getData(searchWord: String, page: Int): Single<PixabayData> {

        return apiHelper.getImages(searchWord, page)

    }
}