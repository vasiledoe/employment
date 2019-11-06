package features.details_job

import features.details_job.model.DetailsJobRepo
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val detailsJobModule = module {

    factory<DetailsJobRepo>()

}