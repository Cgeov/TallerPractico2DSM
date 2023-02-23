package com.example.tallerdsm01

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Ejercicio2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ejercicio2)

        val txt2 = findViewById<EditText>(R.id.txtNombre2)
        val txt1=findViewById<EditText>(R.id.txtSalario)
        val button=findViewById<Button>(R.id.btnCalcularSalario)
        val txtResult = findViewById<TextView>(R.id.lblResults2)

        button.setOnClickListener {
            val salarioBase = txt1.text.toString().toFloat()
            val deducciones = (salarioBase*0.03) + (salarioBase*0.04) + (salarioBase*0.05)
            val salarioNeto = salarioBase - deducciones
            val formato = String.format("%.2f", salarioNeto)
            txtResult.text = "${txt2.text.toString()}, su salario neto es: $${formato.toString()}"
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.opciones, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.opcion1) {
            Toast.makeText(this, "Se seleccionó la primer opción", Toast.LENGTH_LONG).show()
            val intent = Intent(this,  Ejercicio1Activity::class.java)
            startActivity(intent)
        }
        if (id == R.id.opcion2) {
            Toast.makeText(this, "Se seleccionó la segunda opción", Toast.LENGTH_LONG).show()
            val intent = Intent(this, Ejercicio2Activity::class.java)
            startActivity(intent)
        }
        if (id == R.id.opcion3) {
            Toast.makeText(this, "Se seleccionó la tercer opción", Toast.LENGTH_LONG).show()
            val intent = Intent(this, Ejercicio3Activity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}