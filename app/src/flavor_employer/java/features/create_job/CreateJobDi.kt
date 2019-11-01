package features.create_job

import features.create_job.model.CreateJobRepo
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val createJobModule = module {

    factory<CreateJobRepo>()

}