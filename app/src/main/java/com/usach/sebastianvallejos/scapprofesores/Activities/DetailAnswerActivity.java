package com.usach.sebastianvallejos.scapprofesores.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.usach.sebastianvallejos.scapprofesores.Models.Answers;
import com.usach.sebastianvallejos.scapprofesores.R;

public class DetailAnswerActivity extends AppCompatActivity {

    private Intent intent;
    private Answers respuestas = new Answers();
    private TextView detalle;
    private TextView nombre;
    private TextView tiempo;
    private TextView dificultad;
    private TextView pregunta3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_answer);

        intent = getIntent();

        detalle = (TextView) findViewById(R.id.detalle_respuesta);
        nombre = (TextView) findViewById(R.id.nombre_apoderado);
        tiempo = (TextView) findViewById(R.id.respuesta_tiempo);
        dificultad = (TextView) findViewById(R.id.respuesta_dificultad);
        pregunta3 = (TextView) findViewById(R.id.respuesta_pregunta3);
        obtener_datos();
    }

    private void obtener_datos(){
        this.respuestas.setNombre(intent.getStringExtra("nombre"));
        this.respuestas.setApellidoPaterno(intent.getStringExtra("apellidoPaterno"));
        this.respuestas.setApellidoMaterno(intent.getStringExtra("apellidoMaterno"));
        this.respuestas.setDetalle(intent.getStringExtra("detalle"));
        this.respuestas.setTiempo(intent.getStringExtra("tiempo"));
        this.respuestas.setDificultad(intent.getStringExtra("dificultad"));
        this.respuestas.setPregunta3(intent.getStringExtra("motivacion"));
        rellenar_vista();
    }

    private void rellenar_vista(){
        this.nombre.setText(respuestas.getNombre() + " " + respuestas.getApellidoPaterno());
        this.tiempo.setText(colocar_tiempo(respuestas.getTiempo()));
        this.dificultad.setText(colocar_dificultad(respuestas.getDificultad()));
        this.pregunta3.setText(colocar_motivacion(respuestas.getPreguntaMotivacion()));
        if (respuestas.getDetalle().equals("")){
            detalle.setText("Sin detalles....");
        }else{
            detalle.setText(respuestas.getDetalle());
        }
    }

    private String colocar_tiempo(String tiempo){
        if (tiempo.equals("menos")){
            return "Menos de 5 minutos";
        }
        else if (tiempo.equals("entre")){
            return "Entre 5 y 10 minutos";
        }
        else{
            return "Más de 10 minutos";
        }
    }

    private String colocar_dificultad(String tiempo){
        if (tiempo.equals("faciles")){
            return "Fácil";
        }
        else if (tiempo.equals("normales")){
            return "Regular";
        }
        else{
            return "Difícil";
        }
    }

    private String colocar_motivacion(String motivacion){
        if (motivacion.equals("entusiasmado")){return "Entusiasmado(a)";}
        else if (motivacion.equals("desinteresado")){return "Desinteresado(a)";}
        else return "Aburrido(a)";
    }
}
