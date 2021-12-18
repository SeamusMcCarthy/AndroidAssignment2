package ie.wit.theyappyappy.views.walk

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
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
        binding.lengthPicker.minValue = 0
        binding.lengthPicker.maxValue = 30

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
            R.id.item_save -> {
                if (binding.walkTitle.text.toString().isEmpty()) {
                    Snackbar.make(binding.root, R.string.enter_title, Snackbar.LENGTH_LONG)
                        .show()
                } else {

                    val selectedOption1: Int= binding.radioGroup1.checkedRadioButtonId
                    val walk_type = findViewById<RadioButton>(selectedOption1).text.toString()

                    val selectedOption2: Int= binding.radioGroup2.checkedRadioButtonId
                    val bins_provided = findViewById<RadioButton>(selectedOption2).text.toString()

                    val selectedOption3: Int= binding.radioGroup3.checkedRadioButtonId
                    val lead_required = findViewById<RadioButton>(selectedOption3).text.toString()

                    i("Walk type = " + walk_type)

                    presenter.doAddOrSave(binding.walkTitle.text.toString(), binding.description.text.toString(), binding.lengthPicker.value, walk_type, bins_provided, lead_required)
                }
            }
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
        if (walk.length > 0) {
            binding.lengthPicker.value = walk.length
        } else {
            binding.lengthPicker.value = 0
        }


        i("Selected re-entry " + walk.type)
        if(binding.radioBeach.text.equals(walk.type)) {
            binding.radioBeach.isChecked = true
        } else {
            binding.radioPark.isChecked = true
        }

        if(binding.radioBinsYes.text.equals(walk.bins_provided)) {
            binding.radioBinsYes.isChecked = true
        } else {
            binding.radioBinsNo.isChecked = true
        }

        if(binding.radioLeadYes.text.equals(walk.lead_required)) {
            binding.radioLeadYes.isChecked = true
        } else {
            binding.radioLeadNo.isChecked = true
        }


//        binding.btnAdd.setText(R.string.button_saveWalk)
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
