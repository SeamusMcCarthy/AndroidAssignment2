package ie.wit.theyappyappy.models

import android.util.Log.i
import timber.log.Timber.i

class WalkMemStore : WalkStore {

    val walks = ArrayList<WalkModel>()

    override fun findAll(): ArrayList<WalkModel> {
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
            foundWalk.type = walk.type
            foundWalk.bins_provided = walk.bins_provided
            foundWalk.lead_required = walk.lead_required
            foundWalk.length = walk.length
            foundWalk.lat = walk.lat
            foundWalk.lng = walk.lng
            foundWalk.zoom = walk.zoom
            logAll()
        }
    }

    override fun delete(walk: WalkModel) {
        walks.remove(walk)
    }

    fun logAll() {
        walks.forEach{ i("${it}") }
    }

    override fun findById(id:Long) : WalkModel? {
        val foundWalk: WalkModel? = walks.find { it.id == id }
        return foundWalk
    }
}