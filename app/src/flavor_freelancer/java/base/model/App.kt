package base.model

import android.app.Application
import base.baseModule
import features.auth.authModule
import features.details_job.detailsJobModule
import features.edit_talent_account.editTalentModule
import features.list_jobs.listJobsModule
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
            modules(
                authModule + splashModule + utilsModule + listJobsModule +
                        detailsJobModule + editTalentModule + baseModule
            )
        }
    }
}