package ie.wit.theyappyappy.views.walk

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.Picasso
import ie.wit.theyappyappy.R
import ie.wit.theyappyappy.databinding.ActivityWalkBinding
import ie.wit.theyappyappy.models.WalkModel
import timber.log.Timber.i

class WalkView : AppCompatActivity() {
    private lateinit var binding: ActivityWalkBinding
    private lateinit var presenter: WalkPresenter
    var walk = WalkModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityWalkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = WalkPresenter(this)

        binding.chooseImage.setOnClickListener {
            presenter.cacheWalk(binding.walkTitle.text.toString(), binding.description.text.toString())
            presenter.doSelectImage()
        }

        binding.walkLocation.setOnClickListener {
            presenter.cacheWalk(binding.walkTitle.text.toString(), binding.description.text.toString())
            presenter.doSetLocation()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_walk, menu)
//        val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
//        if (presenter.edit){
//            deleteMenu.setVisible(true)
//        }
//        else{
//            deleteMenu.setVisible(false)
//        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
//            R.id.item_save -> {
//                if (binding.placemarkTitle.text.toString().isEmpty()) {
//                    Snackbar.make(binding.root, R.string.enter_placemark_title, Snackbar.LENGTH_LONG)
//                        .show()
//                } else {
//                    presenter.doAddOrSave(binding.placemarkTitle.text.toString(), binding.description.text.toString())
//                }
//            }
//            R.id.item_delete -> {
//                presenter.doDelete()
//            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }

        }
        return super.onOptionsItemSelected(item)
    }
    fun showWalk(walk: WalkModel) {
        binding.walkTitle.setText(walk.title)
        binding.description.setText(walk.description)

        Picasso.get()
            .load(walk.image)
            .into(binding.walkImage)
        if (walk.image != Uri.EMPTY) {
            binding.chooseImage.setText(R.string.change_walkImage)
        }

    }

    fun updateImage(image: Uri){
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.walkImage)
        binding.chooseImage.setText(R.string.change_walkImage)
    }
}
