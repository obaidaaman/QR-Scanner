package com.example.qrscan

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.qrscan.Activity.CaptureAct
import com.example.qrscan.Activity.LoginActivity
import com.example.qrscan.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private lateinit var dbRef : DatabaseReference

    private lateinit var firebaseAuth: FirebaseAuth

    private val barLauncher :  ActivityResultLauncher<ScanOptions>  = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null)  {

val userId = dbRef.push().key!!

            dbRef.child(userId).setValue(result.contents)
                .addOnCompleteListener{
                    if (it.isSuccessful){
                        val builder = AlertDialog.Builder(this@MainActivity)

                        builder.setTitle("Result")
                        builder.setMessage("Data has been stored")
                        builder.setPositiveButton("Ok", DialogInterface.OnClickListener(){
                                dialog, _ ->
                            dialog.dismiss()

                        } )
                        builder.show()
                    }
                }


        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth= Firebase.auth
        dbRef= FirebaseDatabase.getInstance().getReference("qrdata")

        binding.QRbutton.setOnClickListener{

            scanCode()

            //  startActivity(Intent(this@MainActivity,ScannerActivity::class.java))
        }
        binding.btnSignOut.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(this@MainActivity, LoginActivity::class.java)


            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)

            startActivity(intent)
        }



    }

    private fun scanCode() {
        val options = ScanOptions()
        options.setPrompt("Volume Up to turn on flash")
        options.setBeepEnabled(true)
        options.setOrientationLocked(true)
        options.captureActivity = CaptureAct::class.java
        barLauncher.launch(options)


    }


}