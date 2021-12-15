package ie.wit.theyappyappy.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.theyappyappy.R
import ie.wit.theyappyappy.databinding.ActivityWalkBinding
import ie.wit.theyappyappy.helpers.showImagePicker
import ie.wit.theyappyappy.main.MainApp
import ie.wit.theyappyappy.models.Location
import ie.wit.theyappyappy.models.WalkModel
import timber.log.Timber.i

class WalkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWalkBinding
    var walk = WalkModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerImagePickerCallback()
        registerMapCallback()
        app = application as MainApp

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        binding.lengthPicker.minValue = 1
        binding.lengthPicker.maxValue = 30
        var edit = false
        if (intent.hasExtra("walk_edit")) {
            edit = true
            walk = intent.extras?.getParcelable("walk_edit")!!
            binding.walkTitle.setText(walk.title)
            binding.description.setText(walk.description)
            binding.lengthPicker.isVisible = false

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


            binding.btnAdd.setText(R.string.button_saveWalk)
            Picasso.get()
                .load(walk.image)
                .into(binding.walkImage)
            if (walk.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_walkImage)
            }
        } else {
            binding.btnAdd.setText(R.string.button_addWalk)
        }

        binding.btnAdd.setOnClickListener() {
            walk.title = binding.walkTitle.text.toString()
            walk.description = binding.description.text.toString()

            val selectedOption1: Int= binding.radioGroup1.checkedRadioButtonId
            walk.type = findViewById<RadioButton>(selectedOption1).text.toString()

            val selectedOption2: Int= binding.radioGroup2.checkedRadioButtonId
            walk.bins_provided = findViewById<RadioButton>(selectedOption2).text.toString()

            val selectedOption3: Int= binding.radioGroup3.checkedRadioButtonId
            walk.lead_required = findViewById<RadioButton>(selectedOption3).text.toString()

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
            showImagePicker(imageIntentLauncher)
        }

        binding.walkLocation.setOnClickListener {
            fetchLocation()
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

    private fun fetchLocation() {
        val task = fusedLocationProviderClient.lastLocation
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }

        task.addOnSuccessListener {
            if(it != null) {
                val location = Location(it.latitude, it.longitude, 15f)

                if (walk.zoom != 0f) {
                    location.lat = walk.lat
                    location.lng = walk.lng
                    location.zoom = walk.zoom
                }
                val launcherIntent = Intent(this, MapActivity::class.java)
                    .putExtra("location", location)
                mapIntentLauncher.launch(launcherIntent)
            }
        }
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
                            binding.chooseImage.setText(R.string.change_walkImage)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            walk.lat = location.lat
                            walk.lng = location.lng
                            walk.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}