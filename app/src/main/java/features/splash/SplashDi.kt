package features.splash

import features.splash.model.SplashRepo
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val splashModule = module {

    factory<SplashRepo>()

}