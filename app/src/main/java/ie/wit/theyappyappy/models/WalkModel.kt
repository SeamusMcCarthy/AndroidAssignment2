package ie.wit.theyappyappy.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WalkModel(var id: Long = 0,
                     var title: String = "",
                     var description: String = "",
//                     var type: Boolean,
                     var image: Uri = Uri.EMPTY) : Parcelable