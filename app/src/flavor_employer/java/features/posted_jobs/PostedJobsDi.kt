package features.posted_jobs

import features.posted_jobs.model.DataFormatter
import features.posted_jobs.model.PostedJobsRepo
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val postedJobsModule = module {

    factory<PostedJobsRepo>()
    factory<DataFormatter>()

}