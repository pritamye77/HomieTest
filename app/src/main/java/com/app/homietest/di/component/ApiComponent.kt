package homie.app.pritam.di.component

import dagger.Component
import homie.app.pritam.data.api.ApiHelper
import homie.app.pritam.di.module.ApiModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(apiHelper: ApiHelper)
}
