package ie.wit.theyappyappy.views.walklist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.theyappyappy.R
//import ie.wit.theyappyappy.views.walklist.WalkAdapter
//import ie.wit.theyappyappy.views.walklist.WalkListener
import ie.wit.theyappyappy.databinding.ActivityWalkListBinding
import ie.wit.theyappyappy.helpers.GestureHelpers
import ie.wit.theyappyappy.main.MainApp
import ie.wit.theyappyappy.models.WalkModel
//import ie.wit.theyappyappy.views.walklist.WalkListPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.Timber.i

class WalkListView : AppCompatActivity(), WalkListener {

    lateinit var app: MainApp
    lateinit var binding: ActivityWalkListBinding
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

        val swipeGesture = object : GestureHelpers(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction){
                    ItemTouchHelper.LEFT -> {
                        val pos = viewHolder.adapterPosition
                        GlobalScope.launch(Dispatchers.IO) {
                            presenter.deleteWalk(presenter.getWalks()[pos])
                            loadWalks()
                        }
                    }
                    ItemTouchHelper.RIGHT -> {
                        val pos = viewHolder.adapterPosition
                        GlobalScope.launch(Dispatchers.IO) {
                            val archiveItem = presenter.getWalks()[pos]
                            presenter.deleteWalk(archiveItem)
                            presenter.createWalk(archiveItem)
                            loadWalks()
                        }
                    }
                }
            }
        }
        val touchHelper= ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(binding.recyclerView)

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
            R.id.item_logout -> { presenter.doLogout() }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onWalkClick(walk: WalkModel) {
        presenter.doEditWalk(walk)

    }

    override fun onResume() {
        super.onResume()
        loadWalks()
        Timber.i("recyclerView onResume")

    }

    private fun loadWalks() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.recyclerView.adapter = WalkAdapter(presenter.getWalks(), this@WalkListView)
            i("In LoadWalks and size is : " + presenter.getWalks().size)
        }

    }
}