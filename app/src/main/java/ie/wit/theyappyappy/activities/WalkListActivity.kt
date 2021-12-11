package ie.wit.theyappyappy.activities

import ie.wit.theyappyappy.adapters.WalkAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ItemTouchHelper
//import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.theyappyappy.R
import ie.wit.theyappyappy.main.MainApp
import ie.wit.theyappyappy.adapters.WalkListener
import ie.wit.theyappyappy.databinding.ActivityWalkListBinding
import ie.wit.theyappyappy.helpers.GestureHelpers
import ie.wit.theyappyappy.models.WalkModel

class WalkListActivity : AppCompatActivity(), WalkListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityWalkListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapsIntentLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalkListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadWalks()
        registerRefreshCallback()
        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, WalkActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
            R.id.item_map -> {
                val launcherIntent = Intent(this, WalkMapsActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onWalkClick(walk: WalkModel) {
        val launcherIntent = Intent(this, WalkActivity::class.java)
        launcherIntent.putExtra("walk_edit", walk)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun loadWalks() {
        showWalks(app.walks.findAll())
    }

    fun showWalks (walks: ArrayList<WalkModel>) {

        binding.recyclerView.adapter = WalkAdapter(walks, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
        val swipeGesture = object : GestureHelpers(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction){
                    ItemTouchHelper.LEFT -> {
                        app.walks.delete(walks[viewHolder.adapterPosition])
                        binding.recyclerView.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                    }
                    ItemTouchHelper.RIGHT -> {
                        val archiveItem = walks[viewHolder.adapterPosition]
                        app.walks.delete(walks[viewHolder.adapterPosition])
                        binding.recyclerView.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                        app.walks.create(archiveItem)
                        binding.recyclerView.adapter?.notifyItemInserted(walks.size)
                    }
                }
            }
        }
        val touchHelper= ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(binding.recyclerView)

    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadWalks() }
    }

    private fun registerMapCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }

}

