package ie.wit.theyappyappy.views.walk

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ie.wit.theyappyappy.databinding.ActivityWalkBinding
import ie.wit.theyappyappy.main.MainApp
import ie.wit.theyappyappy.models.Location
import ie.wit.theyappyappy.models.WalkModel
import ie.wit.theyappyappy.helpers.showImagePicker
import ie.wit.theyappyappy.views.editlocation.EditLocationView
import timber.log.Timber
import timber.log.Timber.i

class WalkPresenter(private val view: WalkView) {
    var walk = WalkModel()
    var app: MainApp = view.application as MainApp
    var binding: ActivityWalkBinding = ActivityWalkBinding.inflate(view.layoutInflater)
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false;

    init {
        if (view.intent.hasExtra("walk_edit")) {
            i("In here")
            edit = true
            walk = view.intent.extras?.getParcelable("walk_edit")!!
            view.showWalk(walk)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }

    fun doAddOrSave(title: String, description: String, length: Int, walk_type: String, bins_provided: String, lead_required: String ) {
        walk.title = title
        walk.description = description
        walk.length = length
        walk.bins_provided = bins_provided
        walk.lead_required = lead_required
        i("In doAddOrSave : " + walk_type)
        walk.type = walk_type
        if (edit) {
            app.walks.update(walk)
        } else {
            app.walks.create(walk)
        }

        view.finish()

    }

    fun doCancel() {
        view.finish()

    }

    fun doDelete() {
        app.walks.delete(walk)
        view.finish()

    }

    fun doSelectImage() {
        showImagePicker(imageIntentLauncher)
    }

    fun doSetLocation() {
        val location = Location(52.245696, -7.139102, 15f)
        if (walk.zoom != 0f) {
            location.lat =  walk.lat
            location.lng = walk.lng
            location.zoom = walk.zoom
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }
    fun cacheWalk (title: String, description: String) {
        walk.title = title;
        walk.description = description
    }

    private fun registerImagePickerCallback() {

        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            walk.image = result.data!!.data!!
                            view.updateImage(walk.image)
                        }
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }

            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            walk.lat = location.lat
                            walk.lng = location.lng
                            walk.zoom = location.zoom
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }

            }
    }
}
