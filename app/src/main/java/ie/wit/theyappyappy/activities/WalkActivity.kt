package ie.wit.theyappyappy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.theyappyappy.R
import ie.wit.theyappyappy.databinding.ActivityWalkBinding
import ie.wit.theyappyappy.helpers.showImagePicker
import ie.wit.theyappyappy.main.MainApp
import ie.wit.theyappyappy.models.WalkModel
import timber.log.Timber
import timber.log.Timber.i

class WalkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWalkBinding
    var walk = WalkModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Was seeing errors where app crashed as soon as I opened this activity. Traced it back to an issue with
        // AppBarLayout. Theme didn't seem to be resetting via .postSplashScreenTheme so had to force reset it to original
        // layout.
        setTheme(R.style.Theme_TheYappyAppy)
//        i("Theme details 2 : " + getApplication().getTheme())
        binding = ActivityWalkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerImagePickerCallback()

        app = application as MainApp

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        var edit = false
        if (intent.hasExtra("walk_edit")) {
            edit = true
            walk = intent.extras?.getParcelable("walk_edit")!!
            binding.walkTitle.setText(walk.title)
            binding.description.setText(walk.description)
            i("Selected re-entry " + walk.type)
            if(binding.radioBeach.text.equals(walk.type)) {
                binding.radioBeach.isChecked = true
            } else {
                binding.radioPark.isChecked = true
            }
            binding.btnAdd.setText(R.string.button_saveWalk)
            Picasso.get()
                .load(walk.image)
                .into(binding.walkImage)
        } else {
            binding.btnAdd.setText(R.string.button_addWalk)
        }

//      Removed Timber plant from here as it's now planted in MainApp
//        Timber.plant(Timber.DebugTree())
        i("Walk Activity started...")


        binding.btnAdd.setOnClickListener() {
            walk.title = binding.walkTitle.text.toString()
            walk.description = binding.description.text.toString()

            val selectedOption: Int= binding.radioGroup1!!.checkedRadioButtonId
            walk.type = findViewById<RadioButton>(selectedOption).text.toString()
//            i("Selected option  = " + walk.type )
            if (walk.title.isNotEmpty()) {
                if (edit) {
                    app.walks.update(walk.copy())
                } else {
                    app.walks.create(walk.copy())
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,R.string.enter_title, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        binding.chooseImage.setOnClickListener {
            i("Select image")
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_walk, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            walk.image = result.data!!.data!!
                            Picasso.get()
                                .load(walk.image)
                                .into(binding.walkImage)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_beach ->
                    if (checked) {
                        // Pirates are the best
                    }
                R.id.radio_park ->
                    if (checked) {
                        // Ninjas rule
                    }
            }
        }
    }
}