package ie.wit.theyappyappy.activities

import ie.wit.theyappyappy.adapters.WalkAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.theyappyappy.R
import ie.wit.theyappyappy.main.MainApp
import ie.wit.theyappyappy.adapters.WalkListener
import ie.wit.theyappyappy.databinding.ActivityWalkListBinding
import ie.wit.theyappyappy.models.WalkModel

class WalkListActivity : AppCompatActivity(), WalkListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityWalkListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityWalkListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadWalks()

        registerRefreshCallback()
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
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onWalkClick(walk: WalkModel) {
        val launcherIntent = Intent(this, WalkActivity::class.java)
        launcherIntent.putExtra("walk_edit", walk)
        refreshIntentLauncher.launch(launcherIntent)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        binding.recyclerView.adapter?.notifyDataSetChanged()
//        super.onActivityResult(requestCode, resultCode, data)
//    }

    private fun loadWalks() {
        showWalks(app.walks.findAll())
    }

    fun showWalks (walks: List<WalkModel>) {
        binding.recyclerView.adapter = WalkAdapter(walks, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadWalks() }
    }

}

