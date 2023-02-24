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

class Ejercicio1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ejercicio1)

        val txtNombre = findViewById<EditText>(R.id.txtNombre1)
        val txtNota1 = findViewById<EditText>(R.id.txtNota1)
        val txtNota2 = findViewById<EditText>(R.id.txtNota2)
        val txtNota3 = findViewById<EditText>(R.id.txtNota3)
        val txtNota4 = findViewById<EditText>(R.id.txtNota4)
        val txtNota5 = findViewById<EditText>(R.id.txtNota5)
        val boton = findViewById<Button>(R.id.btnPromedio)
        val resultado = findViewById<TextView>(R.id.lblResultado1)
        val estado = findViewById<TextView>(R.id.lblResultadosEstado)

        boton.setOnClickListener{
            val nota1 = txtNota1.text.toString().toFloat()
            val nota2 = txtNota2.text.toString().toFloat()
            val nota3 = txtNota3.text.toString().toFloat()
            val nota4 = txtNota4.text.toString().toFloat()
            val nota5 = txtNota5.text.toString().toFloat()

            val promedio = String.format("%.2f", (nota1+nota2+nota3+nota4+nota5)/5)
            resultado.text = "${txtNombre.text.toString()}, su promedio de notas es: ${promedio}"
            if (promedio.toFloat() >= 6){
                estado.text = "Estado: Aprobado"
            }else{
                estado.text = "Estado: Reprobado"
            }
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