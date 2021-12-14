package ie.wit.theyappyappy.models

interface WalkStore {
    fun findAll(): ArrayList<WalkModel>
    fun create(walk: WalkModel)
    fun update(walk: WalkModel)
    fun delete(walk: WalkModel)
    fun findById(id:Long) : WalkModel?
}