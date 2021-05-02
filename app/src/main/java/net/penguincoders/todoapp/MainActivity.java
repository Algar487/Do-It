package net.penguincoders.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//Clase que gestiona la actividad principal de la aplicación. En este caso es muy sencilla
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Método que abre la pantalla de gestión de tareas
    public void openTaskActivity(View view) {
        Intent i = new Intent(this, TaskActivity.class);
        startActivity(i);
    }



}