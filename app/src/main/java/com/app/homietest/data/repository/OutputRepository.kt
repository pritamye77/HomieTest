package homie.app.pritam.data.repository


import io.reactivex.Single
import homie.app.pritam.data.model.PixabayData

interface OutputRepository {
    fun getData(searchWord: String, page: Int): Single<PixabayData>
}