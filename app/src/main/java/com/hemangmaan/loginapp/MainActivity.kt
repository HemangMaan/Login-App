package com.hemangmaan.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.hemangmaan.loginapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private  lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()

        binding.RegisterBt.setOnClickListener {
            val intent = Intent(this,registerActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.LoginBt.setOnClickListener{
            if(checking()){
                val email = binding.Usernametv.text.toString()
                val password = binding.Passwordtv.text.toString()
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
                    task ->
                    if(task.isSuccessful){
                        val intent = Intent(this,LoggedIn::class.java)
                        intent.putExtra("Email",email)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this, "Wrong Details", Toast.LENGTH_LONG).show()
                    }
                }
            }
            else{
                Toast.makeText(this, "Enter the Details", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun checking():Boolean{
        if(binding.Usernametv.text.toString().trim{it<=' '}.isNotEmpty() && binding.Passwordtv.text.toString().trim{it<=' '}.isNotEmpty()){
            return true
        }
        return false
    }
}