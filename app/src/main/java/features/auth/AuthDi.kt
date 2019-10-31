package features.auth

import features.auth.model.AuthRepo
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val authModule = module {

    factory<AuthRepo>()

}