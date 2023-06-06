package com.example.qrscan.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.qrscan.MainActivity
import com.example.qrscan.R
import com.example.qrscan.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = Firebase.auth

        binding.txtLoginIntent.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            finish()
        }

        binding.btnSignUp.setOnClickListener {
            if (binding.edtEmail.text.toString().isEmpty()) {
                Toast.makeText(this, "Email must not be empty", Toast.LENGTH_SHORT).show()
            }
            if (binding.edtPassword.text.toString().isEmpty()) {
                Toast.makeText(this, "Password must not be empty", Toast.LENGTH_SHORT).show()
            }
            if (binding.edtEmail.text.toString().isNotEmpty() && binding.edtPassword.text.toString()
                    .isNotEmpty()
            ) {
                var email = binding.edtEmail.text.toString()
                var password = binding.edtPassword.text.toString()
                firebaseAuth.createUserWithEmailAndPassword(
                    email,
                    password
                ).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SignUpActivity, MainActivity::class.java)


                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)

                        startActivity(intent)
                    }
                }
            }

        }
    }
}

