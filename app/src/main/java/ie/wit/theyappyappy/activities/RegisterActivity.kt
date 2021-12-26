package ie.wit.theyappyappy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import ie.wit.theyappyappy.databinding.ActivityRegisterBinding
import ie.wit.theyappyappy.main.MainApp
import ie.wit.theyappyappy.views.walklist.WalkListView

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    lateinit var app: MainApp
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setTheme(R.style.Theme_TheYappyAppy)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        setContentView(binding.root)
        app = application as MainApp

        binding.txtLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnRegister.setOnClickListener {
            if (binding.etEmail.length() == 0 || binding.etPassword.length() == 0 ||
                binding.etConfirmPass.text.toString() != binding.etPassword.text.toString()) {
                if (binding.etEmail.length() == 0) {
                    binding.etEmail.error = "Field can't be blank"
                }
                if (binding.etPassword.length() == 0) {
                    binding.etPassword.error = "Field can't be blank"
                }
                if (binding.etConfirmPass.text.toString() != binding.etPassword.text.toString()) {
                    binding.etConfirmPass.error = "Password didn't match"
                }
            } else {
                mAuth.createUserWithEmailAndPassword(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                    .addOnCompleteListener(this) {task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this@RegisterActivity, WalkListView::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@RegisterActivity, "Registration Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        setUpToolbar()
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}