package com.example.babycarev1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

public class PrintActivity extends AppCompatActivity {

    //CLASE CON FINES DE DESARROLLO, PARA VER QUE SE CREAN LOS USUARIOS DEL REGISTRO

    protected ListView lista1;
    protected DataBaseSQL db;
    private Intent pasarPantalla;
    private ArrayList<String> usuarios = new ArrayList<String>();
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> usuario;
    private String contenidoItem;
    private String[] partes;
    private int identificador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        //BASE DE DATOS Y REFERENCIAS
        db = new DataBaseSQL(this);
        lista1 = (ListView) findViewById(R.id.lista1_listado);

        //MOSTRAR NOTAS EN LISTA
        usuarios = db.consultarUsuarios();
        adaptador = new ArrayAdapter<String>(PrintActivity.this, android.R.layout.simple_list_item_1, usuarios);
        lista1.setAdapter(adaptador);

        //CLICK DE ITEM DE LISTA
        lista1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                contenidoItem = parent.getItemAtPosition(position).toString();
                partes = contenidoItem.split(".-"); //Separar id y texto

                pasarPantalla.putExtra("ID", partes[0]);
                pasarPantalla.putExtra("TITULO", partes[1]);
                finish();
                startActivity(pasarPantalla);
            }
        });
    }


}