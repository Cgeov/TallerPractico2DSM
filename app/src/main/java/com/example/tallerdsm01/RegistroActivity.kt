package com.example.tallerdsm01

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class RegistroActivity : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro)
        val presioneAqui = findViewById<TextView>(R.id.linkRegistro);

        val btn = findViewById<Button>(R.id.btnRegistro);
        val txtCorreo = findViewById<EditText>(R.id.txtCorreoRegistro)
        val txtPass = findViewById<EditText>(R.id.txtPassRegistro)
        auth = FirebaseAuth.getInstance()
        btn.setOnClickListener {
            newAccount(txtCorreo.text.toString(),txtPass.text.toString())
        }

        presioneAqui.setOnClickListener{
            val intent = Intent(this,  LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun newAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Usuario Creado Exitosamente", Toast.LENGTH_LONG).show()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    val user = auth.currentUser
                } else {
                    Toast.makeText(this, "Fallo en el registro", Toast.LENGTH_LONG).show()
                }
            }
    }
}