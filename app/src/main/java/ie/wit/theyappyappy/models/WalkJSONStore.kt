package ie.wit.theyappyappy.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.wit.theyappyappy.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

const val JSON_FILE = "walks.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<WalkModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class WalkJSONStore(private val context: Context) : WalkStore {

//    var walks = mutableListOf<WalkModel>()
        var walks = ArrayList<WalkModel>()
    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override suspend fun findAll(): ArrayList<WalkModel> {
        logAll()
        return walks
    }

    override suspend fun create(walk: WalkModel) {
        walk.id = generateRandomId()
        walks.add(walk)
        serialize()
    }

    override suspend fun findById(id:Long) : WalkModel? {
        val walksList = findAll()
        var foundWalk: WalkModel? = walksList.find { w -> w.id == id }
        return foundWalk
    }


    override suspend fun update(walk: WalkModel) {
        val walksList = findAll()
        var foundWalk: WalkModel? = walksList.find { w -> w.id == walk.id }
        if (foundWalk != null) {
            foundWalk.title = walk.title
            foundWalk.description = walk.description
            foundWalk.image = walk.image
            foundWalk.lat = walk.lat
            foundWalk.lng = walk.lng
            foundWalk.zoom = walk.zoom
            foundWalk.type = walk.type
            foundWalk.bins_provided = walk.bins_provided
            foundWalk.lead_required = walk.lead_required
            foundWalk.length = walk.length
        }
        serialize()
    }

    override suspend fun delete(walk: WalkModel) {
        walks.remove(walk)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(walks, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        walks = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        walks.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}