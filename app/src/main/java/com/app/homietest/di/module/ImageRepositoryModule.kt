package homie.app.pritam.di.module

import dagger.Module
import dagger.Provides
import homie.app.pritam.data.repository.ImageRepository
import homie.app.pritam.data.repository.OutputRepository
import javax.inject.Singleton

@Module
class ImageRepositoryModule {

    @Singleton
    @Provides
    fun providesImageRepository(): OutputRepository {
        return ImageRepository()
    }
}