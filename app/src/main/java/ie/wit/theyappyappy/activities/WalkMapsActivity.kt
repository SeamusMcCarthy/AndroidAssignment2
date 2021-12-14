package ie.wit.theyappyappy.activities

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import ie.wit.theyappyappy.R
import ie.wit.theyappyappy.databinding.ActivityWalkMapsBinding
import ie.wit.theyappyappy.databinding.ContentWalkMapsBinding
import ie.wit.theyappyappy.main.MainApp

class WalkMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

//    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityWalkMapsBinding
    private lateinit var contentBinding: ContentWalkMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp

        binding = ActivityWalkMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentWalkMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }

    fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)
        app.walks.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }

    }

    override fun onMarkerClick(marker: Marker): Boolean {
//        contentBinding.currentTitle.text = marker.title
        val tag = marker.tag as Long
        val walk = app.walks.findById(tag)
        contentBinding.currentTitle.text = walk!!.title
        contentBinding.currentDescription.text = walk!!.description
//        contentBinding.imageView2.setImageBitmap(readImageFromPath(this@WalkMapsActivity, walk.image))
        Picasso.get()
            .load(walk.image)
            .into(contentBinding.imageView2)
        return true
    }

}