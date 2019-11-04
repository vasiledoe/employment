package base.model

import android.app.Application
import base.baseModule
import features.auth.authModule
import features.create_job.createJobModule
import features.posted_jobs.postedJobsModule
import features.splash.splashModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import utils.utilsModule


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // use Koin logger
            printLogger(Level.DEBUG)

            // Android context
            androidContext(this@App)

            // modules
            modules(authModule + splashModule + utilsModule + createJobModule + baseModule + postedJobsModule)
        }
    }
}