package com.usach.sebastianvallejos.scapprofesores.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.usach.sebastianvallejos.scapprofesores.R;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    //Variables a utilizar
    private Intent intent;
    private TextView titulo;
    private TextView desc_actividad;
    private TextView profesor;
    private TextView fecha;
    private TextView materia;
    private TextView descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        intent = getIntent();
        titulo = (TextView) findViewById(R.id.titulo_detalle_actividad);
        desc_actividad = (TextView) findViewById(R.id.descripcion_detalle_actividad);
        profesor = (TextView) findViewById(R.id.profesor_detalle_actividad);
        fecha = (TextView) findViewById(R.id.fecha_detalle_actividad);
        descripcion = (TextView) findViewById(R.id.descripcion_otra_actividad);
        materia = (TextView) findViewById(R.id.materia_detalle_actividad);

        setearTitulos();
        setearTextosActividad();
    }

    private void setearTitulos()
    {
        String tipo = intent.getStringExtra("tipo");

        if (tipo.equals("Tarea"))
        {
            titulo.setText("Tarea");
            desc_actividad.setText("Esta tarea ha sido desarrollada por otro docente, por lo que no puedes modificarla, pero si puedes basarte en ella para crear tus actividades.");
        }
        else if(tipo.equals("Prueba"))
        {
            titulo.setText("Prueba");
            desc_actividad.setText("Esta prueba ha sido desarrollada por otro profesor, por lo que no puedes editar ni borrar, pero puedes revisar que tan complicada es la actividad para planificar mejor tus actividades.");
        }
        else
        {
            titulo.setText("Materiales");
            desc_actividad.setText("Estos materiales han sido solicitados por otro docente, por lo que solamente puedes observar los mismos sin modificarlos.");
        }
    }

    private void setearTextosActividad()
    {
        profesor.setText(intent.getStringExtra("profesor"));
        materia.setText(intent.getStringExtra("materia"));
        fecha.setText(intent.getStringExtra("fecha"));
        descripcion.setText(intent.getStringExtra("descripcion"));
    }
}
