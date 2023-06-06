package com.example.qrscan.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.qrscan.MainActivity
import com.example.qrscan.R
import com.example.qrscan.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var firebaseAuth :FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = Firebase.auth
        binding.signUpIntent.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            finish()
        }

        binding.btnLogIn.setOnClickListener {


            if (binding.edtEmail.text.toString().isEmpty()) {
                Toast.makeText(this, "Email must not be empty", Toast.LENGTH_SHORT).show()
            }
            if (binding.edtPass.text.toString().isEmpty()) {
                Toast.makeText(this, "Password must not be empty", Toast.LENGTH_SHORT).show()
            }
            if (binding.edtEmail.text.toString().isNotEmpty() && binding.edtPass.text.toString()
                    .isNotEmpty()
            ) {
                firebaseAuth.signInWithEmailAndPassword(
                    binding.edtEmail.text.toString(),
                    binding.edtPass.text.toString()
                )
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            Toast.makeText(this, " Logged In Successfully", Toast.LENGTH_SHORT)
                                .show()

                            val intent = Intent(this@LoginActivity, MainActivity::class.java)


                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)

                            startActivity(intent)
                        }

                    }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser!=null){
            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            finish()
        }
    }
}