package ie.wit.theyappyappy.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.wit.theyappyappy.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "walks.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<WalkModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class WalkJSONStore(private val context: Context) : WalkStore {

    var walks = mutableListOf<WalkModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<WalkModel> {
        logAll()
        return walks
    }

    override fun create(walk: WalkModel) {
        walk.id = generateRandomId()
        walks.add(walk)
        serialize()
    }


    override fun update(walk: WalkModel) {
        // todo
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