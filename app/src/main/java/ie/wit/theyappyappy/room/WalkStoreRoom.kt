package ie.wit.theyappyappy.room

import android.content.Context
import androidx.room.Room
import ie.wit.theyappyappy.models.WalkModel
import ie.wit.theyappyappy.models.WalkStore

class WalkStoreRoom(val context: Context) : WalkStore {

    var dao: WalkDao

    init {
        val database = Room.databaseBuilder(context, Database::class.java, "room_sample.db")
            .fallbackToDestructiveMigration()
            .build()
        dao = database.walkDao()
    }

    // Function and DAO expecting list. Model had arraylist, had to change model for Room imp.
    override suspend fun findAll(): List<WalkModel> {
        return dao.findAll()
    }

    override suspend fun findById(id: Long): WalkModel? {
        return dao.findById(id)
    }

    override suspend fun create(walk: WalkModel) {
        dao.create(walk)
    }

    override suspend fun update(walk: WalkModel) {
        dao.update(walk)
    }

    override suspend fun delete(walk: WalkModel) {
        dao.deleteWalk(walk)
    }

    fun clear() {
    }
}
