package base

import android.app.Application
import features.auth.authModule
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
            modules(authModule + splashModule + utilsModule)
        }
    }
}