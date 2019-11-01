package base

import base.model.MainRepo
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val baseModule = module {

    factory<MainRepo>()

}