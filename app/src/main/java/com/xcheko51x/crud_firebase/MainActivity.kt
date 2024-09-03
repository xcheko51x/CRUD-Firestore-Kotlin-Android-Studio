package com.xcheko51x.crud_firebase

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.xcheko51x.crud_firebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TareaAdapter
    private lateinit var viewModel: TareaViewModel

    var tareaEdit = Tarea()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TareaViewModel::class.java]

        viewModel.listaTareas.observe(this) { tareas ->
            setupRecyclerView(tareas)
        }

        binding.btnAgregarTarea.setOnClickListener {
            val tarea = Tarea(
                titulo = binding.etTitulo.text.toString(),
                descripcion = binding.etDescripcion.text.toString()
            )

            viewModel.agregarTarea(tarea)

            binding.etTitulo.setText("")
            binding.etDescripcion.setText("")
        }

        binding.btnActualizarTarea.setOnClickListener {
            tareaEdit.titulo = ""
            tareaEdit.descripcion = ""

            tareaEdit.titulo = binding.etTitulo.text.toString()
            tareaEdit.descripcion = binding.etDescripcion.text.toString()

            viewModel.actualizarTarea(tareaEdit)

            adapter.notifyDataSetChanged()

            binding.etTitulo.setText("")
            binding.etDescripcion.setText("")
        }
    }

    fun setupRecyclerView(listaTareas: List<Tarea>) {
        adapter = TareaAdapter(listaTareas, ::borrarTarea, ::actualizarTarea)
        binding.rvTareas.adapter = adapter
    }

    fun borrarTarea(id: String) {
        viewModel.borrarTarea(id)
    }

    fun actualizarTarea(tarea: Tarea) {
        tareaEdit = tarea

        binding.etTitulo.setText(tareaEdit.titulo)
        binding.etDescripcion.setText(tareaEdit.descripcion)
    }
}