package com.example.tallerdsm01

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class Ejercicio3Activity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ejercicio3)

        val txtnum1 = findViewById<EditText>(R.id.txtNumero1)
        val txtnum2 = findViewById<EditText>(R.id.txtNumero2)
        val lblResult = findViewById<TextView>(R.id.lblResultados3)
        val button = findViewById<Button>(R.id.ButtonOperations)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val valores = arrayOf("Suma", "Resta", "Multiplicación", "División")
        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, valores)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adaptador
        var valorObtenido: String = ""
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                valorObtenido = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(this,"No se ha seleccionado nada",Toast.LENGTH_LONG)
            }
        }

        button.setOnClickListener {
            val numero1 = txtnum1.text.toString().toFloat()
            val numero2 = txtnum2.text.toString().toFloat()
            if(valorObtenido != "Division" && numero2.toDouble() != 0.0){
                when (valorObtenido) {
                    "Suma" -> {
                        lblResult.text = "Respuesta: ${String.format("%.2f",numero1+numero2)}"
                    }
                    "Resta" -> {
                        lblResult.text = "Respuesta: ${String.format("%.2f",numero1-numero2)}"
                    }
                    "Multiplicación" -> {
                        lblResult.text = "Respuesta: ${String.format("%.2f",numero1*numero2)}"
                    }
                    "División" -> {
                        lblResult.text = "Respuesta: ${String.format("%.2f",numero1/numero2)}"
                    }
                    else -> {
                        Toast.makeText(this,"No existe",Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }
}