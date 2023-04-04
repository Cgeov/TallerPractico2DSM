package com.example.tallerdsm01

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Ejercicio1Activity : AppCompatActivity() {
    data class Estudiante(val nombre: String = "", val notas: List<Double> = listOf(), val promedio: Double = 0.0)
    private lateinit var dbReference: DatabaseReference
    private lateinit var estudianteList: MutableList<Estudiante>
    private lateinit var adapter: EstudianteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ejercicio1)

        val editTextNombre = findViewById<EditText>(R.id.editTextNombre)
        val editTextNota1 = findViewById<EditText>(R.id.editTextNota1)
        val editTextNota2 = findViewById<EditText>(R.id.editTextNota2)
        val editTextNota3 = findViewById<EditText>(R.id.editTextNota3)
        val editTextNota4 = findViewById<EditText>(R.id.editTextNota4)
        val editTextNota5 = findViewById<EditText>(R.id.editTextNota5)
        val listView = findViewById<ListView>(R.id.listView)
        val button = findViewById<Button>(R.id.buttonEjercicio1)



        dbReference = Firebase.database.reference.child("estudiantes")
        estudianteList = mutableListOf()
        adapter = EstudianteAdapter(this, estudianteList)
        listView.adapter = adapter

        button.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val notas = mutableListOf<Double>()
            notas.add(editTextNota1.text.toString().toDouble())
            notas.add(editTextNota2.text.toString().toDouble())
            notas.add(editTextNota3.text.toString().toDouble())
            notas.add(editTextNota4.text.toString().toDouble())
            notas.add(editTextNota5.text.toString().toDouble())
            val promedio = notas.average()
            val estudiante = Estudiante(nombre, notas, promedio)
            dbReference.push().setValue(estudiante)
        }


        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // Obtener el estudiante seleccionado
            val estudiante = adapter.getItem(position)

            // Crear un diálogo para editar el estudiante
            val dialogView = LayoutInflater.from(this).inflate(R.layout.ejercicio1, null)
            val editTextNombre = dialogView.findViewById<EditText>(R.id.editTextNombre)
            val editTextNota1 = dialogView.findViewById<EditText>(R.id.editTextNota1)
            val editTextNota2 = dialogView.findViewById<EditText>(R.id.editTextNota2)
            val editTextNota3 = dialogView.findViewById<EditText>(R.id.editTextNota3)
            val editTextNota4 = dialogView.findViewById<EditText>(R.id.editTextNota4)
            val editTextNota5 = dialogView.findViewById<EditText>(R.id.editTextNota5)
            editTextNombre.setText(estudiante!!.nombre)
            editTextNota1.setText(estudiante.notas[0].toString())
            editTextNota2.setText(estudiante.notas[1].toString())
            editTextNota3.setText(estudiante.notas[2].toString())
            editTextNota4.setText(estudiante.notas[3].toString())
            editTextNota5.setText(estudiante.notas[4].toString())
            val dialogBuilder = AlertDialog.Builder(this)
                .setTitle("Editar estudiante")
                .setView(dialogView)
                .setPositiveButton("Guardar") { _, _ ->
                    // Guardar los cambios en la base de datos y actualizar el adaptador
                    val nombre = editTextNombre.text.toString()
                    val notas = mutableListOf<Double>()
                    notas.add(editTextNota1.text.toString().toDouble())
                    notas.add(editTextNota2.text.toString().toDouble())
                    notas.add(editTextNota3.text.toString().toDouble())
                    notas.add(editTextNota4.text.toString().toDouble())
                    notas.add(editTextNota5.text.toString().toDouble())
                    val promedio = notas.average()
                    val newItem = Estudiante(nombre, notas, promedio)
                    val key = estudianteList[position].hashCode().toString() // Se usa el hashCode para generar una clave única
                    dbReference.child(key).setValue(newItem)
                    adapter.updateItem(position, newItem)
                }
                .setNegativeButton("Cancelar", null)

            val dialog = dialogBuilder.create()

            // Cambiar el tiempo de espera del doble clic a 500ms
            var lastClickTime = 0L
            view.setOnClickListener {
                if (System.currentTimeMillis() - lastClickTime < 500) {
                    dialog.show()
                }
                lastClickTime = System.currentTimeMillis()
            }
        }



        listView.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, view, position, id ->
            // Obtener el estudiante seleccionado
            val estudiante = adapter.getItem(position)

            // Eliminar el estudiante del adaptador
            adapter.remove(estudiante)

            // Notificar al adaptador que los datos han cambiado
            adapter.notifyDataSetChanged()
            true
        }

        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                estudianteList.clear()
                for (childSnapshot in snapshot.children) {
                    val estudiante = childSnapshot.getValue(Estudiante::class.java)
                    if (estudiante != null) {
                        estudianteList.add(estudiante)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainActivity", "Error al leer la base de datos", error.toException())
            }
        })
    }

    class EstudianteAdapter(context: Context, private val estudianteList: MutableList<Estudiante>) :
        ArrayAdapter<Estudiante>(context, 0, estudianteList) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.cardejercicio1, parent, false)
            }
            val estudiante = estudianteList[position]
            view!!.findViewById<TextView>(R.id.textViewNombre).text = estudiante.nombre
            view.findViewById<TextView>(R.id.textViewPromedio).text = "Promedio: %.2f".format(estudiante.promedio)
            return view
        }
        fun updateItem(position: Int, newItem: Estudiante) {
            estudianteList[position] = newItem
            notifyDataSetChanged()
        }
    }
}