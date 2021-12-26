package ie.wit.theyappyappy.room

import androidx.room.*
import ie.wit.theyappyappy.models.WalkModel

@Dao
interface WalkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(walk: WalkModel)

    @Query("SELECT * FROM WalkModel")
    suspend fun findAll(): List<WalkModel>

    @Query("select * from WalkModel where id = :id")
    suspend fun findById(id: Long): WalkModel

    @Update
    suspend fun update(walk: WalkModel)

    @Delete
    suspend fun deleteWalk(walk: WalkModel)
}