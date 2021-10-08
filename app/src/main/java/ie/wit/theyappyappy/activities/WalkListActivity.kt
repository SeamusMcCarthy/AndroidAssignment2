package ie.wit.theyappyappy.activities

import ie.wit.theyappyappy.adapters.WalkAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.theyappyappy.R
import ie.wit.theyappyappy.main.MainApp
import ie.wit.theyappyappy.adapters.WalkListener
import ie.wit.theyappyappy.databinding.ActivityWalkListBinding
import ie.wit.theyappyappy.models.WalkModel

class WalkListActivity : AppCompatActivity(), WalkListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityWalkListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalkListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = WalkAdapter(app.walks.findAll(), this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, WalkActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onWalkClick(walk: WalkModel) {
        val launcherIntent = Intent(this, WalkActivity::class.java)
        launcherIntent.putExtra("walk_edit", walk)
        startActivityForResult(launcherIntent,0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

}

