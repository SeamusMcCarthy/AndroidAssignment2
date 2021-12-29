package ie.wit.theyappyappy.main

import android.app.Application
import ie.wit.theyappyappy.models.WalkFireStore
import ie.wit.theyappyappy.models.WalkStore
import ie.wit.theyappyappy.room.WalkStoreRoom
import timber.log.Timber
import timber.log.Timber.i

class MainApp: Application() {

    lateinit var walks: WalkStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        walks = WalkFireStore(applicationContext)
        i("TheYappyAppy started")
    }
}