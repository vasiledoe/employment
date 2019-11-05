package features.list_jobs

import features.list_jobs.model.DataFormatter
import features.list_jobs.model.ListJobsRepo
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val listJobsModule = module {

    factory<ListJobsRepo>()
    factory<DataFormatter>()

}