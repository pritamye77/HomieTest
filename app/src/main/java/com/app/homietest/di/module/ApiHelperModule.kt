package homie.app.pritam.di.module

import dagger.Module
import dagger.Provides
import homie.app.pritam.data.api.ApiHelper
import javax.inject.Singleton

@Module
class ApiHelperModule {

    @Singleton
    @Provides
    fun providesApiHelper(): ApiHelper {
        return ApiHelper()
    }
}