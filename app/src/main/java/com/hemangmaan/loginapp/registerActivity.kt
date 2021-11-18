package com.hemangmaan.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hemangmaan.loginapp.databinding.ActivityRegisterBinding

class registerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        binding.ContinueBt.setOnClickListener {
            if(checking()){
                val email = binding.userEmailEt.text.toString()
                val password = binding.userPasswordEt.text.toString()
                var name = binding.UsernameEt.text.toString()
                var phone = binding.userPhoneEt.text.toString()
                val user = hashMapOf(
                    "Name" to name,
                    "Email" to email,
                    "Phone" to phone
                )
                val users=db.collection("USERS")
                val query = users.whereEqualTo("Email",email).get().addOnSuccessListener {
                        tasks->
                        if(tasks.isEmpty){
                            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                                task->
                                if(task.isSuccessful){
                                    users.document(email).set(user)
                                    val intent = Intent(this,LoggedIn::class.java)
                                    intent.putExtra("Email",email)
                                    startActivity(intent)
                                    finish()
                                }
                                else{
                                    Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        else{
                            Toast.makeText(this, "User Already Registerd", Toast.LENGTH_LONG).show()
                            val intent = Intent(this,MainActivity::class.java)
                            startActivity(intent)
                        }
                }
            }
            else{
                Toast.makeText(this, "Enter the Details", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checking():Boolean{
         if(binding.UsernameEt.text.toString().trim{it<=' '}.isNotEmpty()
             && binding.userPhoneEt.text.toString().trim { it<=' ' }.isNotEmpty()
             && binding.userEmailEt.text.toString().trim { it<=' ' }.isNotEmpty()
             && binding.userPasswordEt.text.toString().trim { it<=' ' }.isNotEmpty()){
             return true
         }
        return false
    }
}