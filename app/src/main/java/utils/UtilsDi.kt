package utils

import org.koin.dsl.module
import org.koin.experimental.builder.single

val utilsModule = module {

    single<MyLogs>()
    single<ResUtil>()
    single<DataFormatter>()

}