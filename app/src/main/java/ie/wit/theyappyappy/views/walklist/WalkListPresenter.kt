package ie.wit.theyappyappy.views.walklist

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import ie.wit.theyappyappy.views.walk.WalkView
import ie.wit.theyappyappy.main.MainApp
import ie.wit.theyappyappy.models.WalkModel
import ie.wit.theyappyappy.views.login.LoginView
import ie.wit.theyappyappy.views.map.WalkMapView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber.i

class WalkListPresenter(val view: WalkListView) {
    var app: MainApp = view.application as MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }

    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun getWalks() = app.walks.findAll()

    suspend fun deleteWalk(walk: WalkModel) {
        i("In Delete function - Walk Title = " + walk.title)
        try {
            app.walks.delete(walk)
        } catch (e: Exception) {
            i("Problem with delete : " + e.message )
        }

    }

    suspend fun createWalk(walk: WalkModel) {
        i("In Create function - Walk Title = " + walk.title)
        try {
            walk.id = 0
            app.walks.create(walk)
        } catch (e: Exception) {
            i("Problem with create : " + e.message )
        }

    }

    fun doAddWalk() {
        val launcherIntent = Intent(view, WalkView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doLogout() {
        auth.signOut()
        val launcherIntent = Intent(view, LoginView::class.java)
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