package features.list_talents

import features.list_talents.model.TalentsRepo
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val listTalentsModule = module {

    factory<TalentsRepo>()

}