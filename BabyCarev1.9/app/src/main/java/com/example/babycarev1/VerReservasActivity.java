package com.example.babycarev1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class VerReservasActivity extends AppCompatActivity {

    protected ScrollView scroll;
    protected LinearLayout layout;
    protected DataBaseSQL db;
    protected TextView label1;
    private ArrayList<Reserva> listaReservas = new ArrayList<Reserva>();
    private int idReserva, idUsuario, idCuidador;
    private String nombreCuidador, fechaReserva, horaReserva;
    private String[] datosReserva;
    private Intent pasarPantalla;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_reservas);

        //REFERENCIAS Y BASE DE DATOS
        db = new DataBaseSQL(this);
        scroll = findViewById(R.id.scroll1_verReservas);
        layout = findViewById(R.id.layout1_verReservas);

        //RECEPCION DE PAQUETES
        extras = getIntent().getExtras();
        if (extras != null)
        {
            idUsuario = extras.getInt("IDUSUARIO");
        }
        else
        {
            Toast.makeText(this, "Se produjo un error recibiendo la información de la sesión iniciada.", Toast.LENGTH_SHORT).show();
        }

        //CREACION DEL ARRAY DE RESERVAS
        for (String reservaTexto : db.consultarReservasUsuario(idUsuario))
        {
            //ASIGNACION DE DATOS DE LA RESERVA
            datosReserva = reservaTexto.split("---");

            idReserva = Integer.parseInt(datosReserva[0]);
            //idUsuario ya lo tengo, seria el 1
            idCuidador = Integer.parseInt(datosReserva[2]);
            fechaReserva = datosReserva[3];
            horaReserva = datosReserva[4];

            //CREACION DEL OBJETO RESERVA EN EL ARRAY DE RESERVAS
            listaReservas.add(new Reserva(idReserva, idUsuario, idCuidador, fechaReserva, horaReserva));

        }

        //CREO EL LAYOUT TARJETA DE CADA RESERVA
        for (Reserva reserva : listaReservas)
        {

            //REFERENCIA AL XML EMPLEADO PARA CADA TARJETA PERFIL
            View previewReserva = LayoutInflater.from(this).inflate(R.layout.item_reserva, scroll, false);

            //REFERENCIAS A LOS ELEMENTOS DE LA TARJETA
            TextView label1 = (TextView) previewReserva.findViewById(R.id.label1_reserva);
            TextView label2 = (TextView) previewReserva.findViewById(R.id.label2_reserva);
            TextView label3 = (TextView) previewReserva.findViewById(R.id.label3_reserva);
            Button boton1 = (Button) previewReserva.findViewById(R.id.boton1_reserva);

            //Asigno texto a las cajas
            String nombreCuidador = db.consultarCuidador(reserva.getIdCuidador()).get(0);
            label1.setText(nombreCuidador);
            label2.setText(reserva.getFecha());
            label3.setText(reserva.getHora());

            //BOTON CANCELAR RESERVA
            boton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(VerReservasActivity.this); //Constructor
                    builder.setMessage("¿Está seguro de cancelar la reserva?"); //Asigna mensaje a mostrar

                    //SI
                    builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try{

                                //DELETE
                                db.borrarReserva(reserva.getIdReserva());

                                Toast.makeText(VerReservasActivity.this, "Reserva cancelada correctamente", Toast.LENGTH_SHORT).show();

                                pasarPantalla = new Intent(VerReservasActivity.this, VerReservasActivity.class);
                                pasarPantalla.putExtra("IDUSUARIO", idUsuario);
                                finish();
                                startActivity(pasarPantalla);

                            } catch (Exception e) {
                                Toast.makeText(VerReservasActivity.this, "No se pudo cancelar la reserva", Toast.LENGTH_SHORT).show();;
                            }
                            
                        }
                    });

                    //NO
                    builder.setNegativeButton("No", null);
                    AlertDialog dialog = builder.create(); //Instancia
                    dialog.show(); //Muestra

                }
            });

            layout.addView(previewReserva);



        }


    }
}