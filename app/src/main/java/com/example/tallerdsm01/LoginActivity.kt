package com.example.tallerdsm01

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity  : AppCompatActivity(){
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        auth = FirebaseAuth.getInstance()
        val presioneAqui = findViewById<TextView>(R.id.linkLogin);
        val txtCorreo = findViewById<EditText>(R.id.txtCorreoLogin)
        val txtPass = findViewById<EditText>(R.id.txtPassLogin)
        val btn = findViewById<Button>(R.id.btnLogin);

        btn.setOnClickListener {
            Login(txtCorreo.text.toString(),txtPass.text.toString())
        }
        presioneAqui.setOnClickListener{
            val intent = Intent(this,  RegistroActivity::class.java)
            startActivity(intent)
        }

    }

    private fun Login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG).show()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    val user = auth.currentUser
                } else {
                    Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_LONG).show()
                }
            }
    }
}