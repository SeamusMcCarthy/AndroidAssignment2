package ie.wit.theyappyappy.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WalkModel(var id: Long = 0,
                     var title: String = "",
                     var description: String = "",
                     var type: String = "",
                     var length: Int = 0,
                     var bins_provided: String = "",
                     var lead_required: String = "",
                     var image: Uri = Uri.EMPTY) : Parcelable