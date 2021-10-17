package ie.wit.theyappyappy.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
//import com.github.ajalt.timberkt.Timber.i
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ie.wit.theyappyappy.R
import ie.wit.theyappyappy.databinding.ActivityLoginBinding
import ie.wit.theyappyappy.databinding.ActivityWalkBinding
import ie.wit.theyappyappy.databinding.ActivityWalkListBinding
import ie.wit.theyappyappy.main.MainApp
import timber.log.Timber
import timber.log.Timber.i


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var app: MainApp

//    private lateinit var toolbar1: Toolbar
//    private lateinit var etEmail: EditText
//    private lateinit var etPassword: EditText
//    private lateinit var btnLogin: Button
//    private lateinit var txtRegister: TextView
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_TheYappyAppy)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        binding.toolbar1.title = title
        setSupportActionBar(binding.toolbar1)
        setContentView(binding.root)
        app = application as MainApp

//        setContentView(R.layout.activity_login)
        mAuth = Firebase.auth
//        etEmail = findViewById(R.id.etEmail)
//        etPassword = findViewById(R.id.etPassword)
//        btnLogin = findViewById(R.id.btnLogin)
//        txtRegister = findViewById(R.id.txtRegister)
//        toolbar1 = findViewById(R.id.toolbar1)
        setUpToolbar()

        binding.txtRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

//        val email = etEmail.text.toString()
//        val password = etPassword.text.toString()

        binding.btnLogin.setOnClickListener {
            mAuth.signInWithEmailAndPassword(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                .addOnCompleteListener(this) {task ->
                    i("%s%s", "Login Details " + binding.etEmail.text.toString() + " ", binding.etPassword.text.toString())
                    if (task.isSuccessful) {
                        val intent = Intent(this@LoginActivity, WalkListActivity::class.java)
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
