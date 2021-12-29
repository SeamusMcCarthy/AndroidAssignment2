package ie.wit.theyappyappy.views.login

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import ie.wit.theyappyappy.main.MainApp
import ie.wit.theyappyappy.models.WalkFireStore
import ie.wit.theyappyappy.views.walklist.WalkListView


class LoginPresenter (val view: LoginView)  {
    private lateinit var loginIntentLauncher : ActivityResultLauncher<Intent>
    var app: MainApp = view.application as MainApp
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: WalkFireStore? = null

    init{
        registerLoginCallback()
        if (app.walks is WalkFireStore) {
            fireStore = app.walks as WalkFireStore
        }
    }

    fun doLogin(email: String, password: String) {
        view.showProgress()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
//                val launcherIntent = Intent(view, WalkListView::class.java)
//                loginIntentLauncher.launch(launcherIntent)
                if (fireStore != null) {
                    fireStore!!.fetchWalks {
                        view?.hideProgress()
                        val launcherIntent = Intent(view, WalkListView::class.java)
                        loginIntentLauncher.launch(launcherIntent)
                    }
                } else {
                    view?.hideProgress()
                    val launcherIntent = Intent(view, WalkListView::class.java)
                    loginIntentLauncher.launch(launcherIntent)
                }
            } else {
                view?.hideProgress()
                view.showSnackBar("Login failed: ${task.exception?.message}")
            }
            view.hideProgress()
        }
    }

    fun doSignUp(email: String, password: String) {
        view.showProgress()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                fireStore!!.fetchWalks {
                    view?.hideProgress()
                    val launcherIntent = Intent(view, WalkListView::class.java)
                    loginIntentLauncher.launch(launcherIntent)
                }
            } else {
                view.showSnackBar("Login failed: ${task.exception?.message}")
            }
            view.hideProgress()
        }
    }

    private fun registerLoginCallback(){
        loginIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}
