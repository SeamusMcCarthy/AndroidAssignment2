package ie.wit.theyappyappy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
//import com.github.ajalt.timberkt.Timber.i
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ie.wit.theyappyappy.databinding.ActivityLoginBinding
import ie.wit.theyappyappy.main.MainApp
import ie.wit.theyappyappy.views.walklist.WalkListView
import timber.log.Timber.i

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var app: MainApp

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        binding.toolbar1.title = title
        setSupportActionBar(binding.toolbar1)
        setContentView(binding.root)
        app = application as MainApp

        mAuth = Firebase.auth
        setUpToolbar()

        binding.txtRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogin.setOnClickListener {
            mAuth.signInWithEmailAndPassword(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                .addOnCompleteListener(this) {task ->
                    i("%s%s", "Login Details " + binding.etEmail.text.toString() + " ", binding.etPassword.text.toString())
                    if (task.isSuccessful) {
                        val intent = Intent(this@LoginActivity, WalkListView::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar1)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

}
