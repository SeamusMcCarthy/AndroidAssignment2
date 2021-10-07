package ie.wit.theyappyappy.main

import android.app.Application
import ie.wit.theyappyappy.models.WalkMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp: Application() {

    val walks = WalkMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("TheYappyAppy started")
    }
}