package homie.app.pritam.di.component

import dagger.Component
import homie.app.pritam.di.module.ImageRepositoryModule
import homie.app.pritam.ui.viewmodel.MainViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [ImageRepositoryModule::class])
interface ImageRepositoryComponent {
    fun inject(imagesViewModel: MainViewModel)
}
