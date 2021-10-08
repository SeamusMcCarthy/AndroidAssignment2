package ie.wit.theyappyappy.models

interface WalkStore {
    fun findAll(): List<WalkModel>
    fun create(walk: WalkModel)
    fun update(walk: WalkModel)
}