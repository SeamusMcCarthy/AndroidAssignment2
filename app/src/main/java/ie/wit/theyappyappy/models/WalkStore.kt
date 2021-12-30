package ie.wit.theyappyappy.models

interface WalkStore {
    suspend fun findAll(): List<WalkModel>
    suspend fun create(walk: WalkModel)
    suspend fun update(walk: WalkModel)
    suspend fun delete(walk: WalkModel)
    suspend fun findById(id:Long) : WalkModel?
    suspend fun findByFbId(fbid:String) : WalkModel?
    suspend fun clear()
}