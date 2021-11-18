package com.hemangmaan.loginapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.hemangmaan.loginapp.databinding.ActivityLoggedInBinding

class LoggedIn : AppCompatActivity() {
    private lateinit var db:FirebaseFirestore
    private lateinit var binding: ActivityLoggedInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoggedInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedpref = this?.getPreferences(Context.MODE_PRIVATE) ?:return
        val isLogin = sharedpref.getString("Email","1")
        binding.logout.setOnClickListener {
            sharedpref.edit().remove("Email").apply()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        if(isLogin=="1") {
            var email = intent.getStringExtra("Email")
            if(email!=null)
            {
                setText(email)
                with(sharedpref.edit()) {
                    putString("Email", email)
                    apply()
                }
            }
            else{
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        else{
            isLogin?.let { setText(it) }
        }
    }

    private fun setText(email: String) {
        db = FirebaseFirestore.getInstance()
        db.collection("USERS").document(email).get().addOnSuccessListener {
            task->
            binding.Name.text = task.get("Name").toString()
            binding.Phone.text = task.get("Phone").toString()
            binding.EmailTV.text = task.get("Email").toString()
        }
    }

}