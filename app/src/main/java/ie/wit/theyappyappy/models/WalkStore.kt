package ie.wit.theyappyappy.models

interface WalkStore {
    fun findAll(): List<WalkModel>
    fun create(placemark: WalkModel)
    fun update(placemark: WalkModel)
}