package ie.wit.theyappyappy.views.walklist

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ie.wit.theyappyappy.views.walk.WalkView
import ie.wit.theyappyappy.main.MainApp
import ie.wit.theyappyappy.models.WalkModel
import ie.wit.theyappyappy.views.map.WalkMapView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WalkListPresenter(val view: WalkListView) {
    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }

    suspend fun getWalks() = app.walks.findAll()

    fun doAddWalk() {
        val launcherIntent = Intent(view, WalkView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditWalk(walk: WalkModel) {
        val launcherIntent = Intent(view, WalkView::class.java)
        launcherIntent.putExtra("walk_edit", walk)
        mapIntentLauncher.launch(launcherIntent)
    }

    fun doShowWalksMap() {
        val launcherIntent = Intent(view, WalkMapView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }
    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { GlobalScope.launch(Dispatchers.Main)
                { getWalks() }
            }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}