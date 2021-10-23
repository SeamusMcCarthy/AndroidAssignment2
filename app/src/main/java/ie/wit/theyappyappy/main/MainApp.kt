package ie.wit.theyappyappy.main

import android.app.Application
import ie.wit.theyappyappy.models.WalkJSONStore
import ie.wit.theyappyappy.models.WalkMemStore
import ie.wit.theyappyappy.models.WalkStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp: Application() {

    lateinit var walks: WalkStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        walks = WalkJSONStore(applicationContext)
        i("TheYappyAppy started")
    }
}