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

class Ejercicio2Activity : AppCompatActivity() {
    data class Empleado(val nombre: String = "", val notas: List<Double> = listOf(), val promedio: Double = 0.0)
    private lateinit var dbReference: DatabaseReference
    private lateinit var empleadoList: MutableList<Empleado>
    private lateinit var adapter: EmpleadoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ejercicio2)

        val editTextNombre = findViewById<EditText>(R.id.txtNombre2)
        val editTextNota1 = findViewById<EditText>(R.id.txtSalario)
        val listView = findViewById<ListView>(R.id.listViewEjercicio2)
        val button = findViewById<Button>(R.id.btnCalcularSalario)



        dbReference = Firebase.database.reference.child("empleados")
        empleadoList = mutableListOf()
        adapter = EmpleadoAdapter(this, empleadoList)
        listView.adapter = adapter

        button.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val notas = mutableListOf<Double>()
            notas.add(editTextNota1.text.toString().toDouble())
            val promedio = (editTextNota1.text.toString().toDouble()*0.03) + (editTextNota1.text.toString().toDouble()*0.04) + (editTextNota1.text.toString().toDouble()*0.05)
            val salarioNeto = editTextNota1.text.toString().toDouble() - promedio
            val empleado = Empleado(nombre, notas, salarioNeto)
            dbReference.push().setValue(empleado)
        }


        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val estudiante = adapter.getItem(position)

            val dialogView = LayoutInflater.from(this).inflate(R.layout.ejercicio2, null)
            val editTextNombre = dialogView.findViewById<EditText>(R.id.txtNombre2)
            val editTextNota1 = dialogView.findViewById<EditText>(R.id.txtSalario)
            editTextNombre.setText(estudiante!!.nombre)
            editTextNota1.setText(estudiante.notas[0].toString())
            val dialogBuilder = AlertDialog.Builder(this)
                .setTitle("Editar empleado")
                .setView(dialogView)
                .setPositiveButton("Guardar") { _, _ ->
                    val nombre = editTextNombre.text.toString()
                    val notas = mutableListOf<Double>()
                    notas.add(editTextNota1.text.toString().toDouble())
                    val promedio = (editTextNota1.text.toString().toDouble()*0.03) + (editTextNota1.text.toString().toDouble()*0.04) + (editTextNota1.text.toString().toDouble()*0.05)
                    val salarioNeto = editTextNota1.text.toString().toDouble() - promedio
                    val newItem = Empleado(nombre, notas, salarioNeto)
                    val key = empleadoList[position].hashCode().toString() // Se usa el hashCode para generar una clave única
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
            val estudiante = adapter.getItem(position)
            adapter.remove(estudiante)
            adapter.notifyDataSetChanged()
            true
        }

        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                empleadoList.clear()
                for (childSnapshot in snapshot.children) {
                    val estudiante = childSnapshot.getValue(Empleado::class.java)
                    if (estudiante != null) {
                        empleadoList.add(estudiante)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainActivity", "Error al leer la base de datos", error.toException())
            }
        })
    }

    class EmpleadoAdapter(context: Context, private val empleadoList: MutableList<Empleado>) :
        ArrayAdapter<Empleado>(context, 0, empleadoList) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.cardejercicio2, parent, false)
            }
            val estudiante = empleadoList[position]
            view!!.findViewById<TextView>(R.id.textViewNombreCardTwo).text = estudiante.nombre
            view.findViewById<TextView>(R.id.textViewSalario).text = "Promedio: %.2f".format(estudiante.promedio)
            return view
        }
        fun updateItem(position: Int, newItem: Empleado) {
            empleadoList[position] = newItem
            notifyDataSetChanged()
        }
    }
}