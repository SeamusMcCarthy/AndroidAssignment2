package ie.wit.theyappyappy.models

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class WalkModel(@PrimaryKey(autoGenerate = true)var id: Long = 0,
                     var title: String = "",
                     var description: String = "",
                     var type: String = "",
                     var length: Int = 0,
                     var bins_provided: String = "",
                     var lead_required: String = "",
                     var image: Uri = Uri.EMPTY,
                     var lat : Double = 0.0,
                     var lng: Double = 0.0,
                     var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable