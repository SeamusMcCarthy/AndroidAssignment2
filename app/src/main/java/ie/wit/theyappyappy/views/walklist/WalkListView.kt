package ie.wit.theyappyappy.views.walklist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.theyappyappy.R
import ie.wit.theyappyappy.adapters.WalkAdapter
import ie.wit.theyappyappy.adapters.WalkListener
import ie.wit.theyappyappy.databinding.ActivityWalkListBinding
import ie.wit.theyappyappy.main.MainApp
import ie.wit.theyappyappy.models.WalkModel
import ie.wit.theyappyappy.views.walklist.WalkListPresenter
import timber.log.Timber

class WalkListView : AppCompatActivity(), WalkListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityWalkListBinding
    lateinit var presenter: WalkListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalkListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = WalkListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadWalks()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddWalk() }
            R.id.item_map -> { presenter.doShowWalksMap() }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onWalkClick(walk: WalkModel) {
        presenter.doEditWalk(walk)

    }

    override fun onResume() {
        //update the view
        binding.recyclerView.adapter?.notifyDataSetChanged()
        Timber.i("recyclerView onResume")
        super.onResume()
    }

    private fun loadWalks() {
        binding.recyclerView.adapter = WalkAdapter(presenter.getWalks(), this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}