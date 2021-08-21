package homie.app.pritam.di.component

import dagger.Component
import homie.app.pritam.data.repository.ImageRepository
import homie.app.pritam.di.module.ApiHelperModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiHelperModule::class])
interface ApiHelperComponent {
    fun inject(mainRepository: ImageRepository)
}
