package ie.wit.theyappyappy.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ie.wit.theyappyappy.helpers.Converters
import ie.wit.theyappyappy.models.WalkModel

@Database(entities = arrayOf(WalkModel::class), version = 1,  exportSchema = false)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun walkDao(): WalkDao
}