package ie.wit.theyappyappy.models

import android.util.Log.i
import timber.log.Timber.i

class WalkMemStore : WalkStore {

    val walks = ArrayList<WalkModel>()

    override fun findAll(): List<WalkModel> {
        return walks
    }

    override fun create(walk: WalkModel) {
        walks.add(walk)
    }

    override fun update(walk: WalkModel) {
        var foundWalk: WalkModel? = walks.find { w -> w.id == walk.id }
        if (foundWalk != null) {
            foundWalk.title = walk.title
            foundWalk.description = walk.description
            logAll()
        }
    }

    fun logAll() {
        walks.forEach{ i("${it}") }
    }
}