package net.penguincoders.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.penguincoders.todoapp.Adapters.ToDoAdapter;
import net.penguincoders.todoapp.Model.ToDoModel;
import net.penguincoders.todoapp.Utils.DatabaseHandler;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


//Clase más importante de la aplicación. Se encarga de la gestión completa de tareas.
public class TaskActivity extends AppCompatActivity implements DialogCloseListener{

    //Declaramos los elementos a utilizar
    private DatabaseHandler db;
    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private FloatingActionButton fab;
    private List<ToDoModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //Conectamos con la base de datos SQLite
        db = new DatabaseHandler(this);
        db.openDatabase();

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db, TaskActivity.this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        fab = findViewById(R.id.fab);
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);

        //escuchador del botón de creación de nuevas tareas
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }

    //Método para gestionar el cierre del diálogo modal y notificar de los cambos producidos en la lista de tareas
    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }

    //Método para volver a la actividad anterior
    public void openMainActivity(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}