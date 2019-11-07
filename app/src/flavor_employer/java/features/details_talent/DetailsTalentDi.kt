package features.details_talent

import features.details_talent.model.DetailsTalentRepo
import org.koin.dsl.module
import org.koin.experimental.builder.factory


val detailsTalentModule = module {

    factory<DetailsTalentRepo>()

}