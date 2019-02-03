package com.usach.sebastianvallejos.scapprofesores.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.usach.sebastianvallejos.scapprofesores.R;

public class SESDetailActivity extends AppCompatActivity {

    //Variables a utilizar
    private Intent intent;
    private String id;
    private String colegio;
    private long total;
    private TextView cant_apoderados;
    private TextView analisis;
    private Button ver_detalles;
    private TextView text_facil;
    private TextView text_normal;
    private TextView text_dificil;
    private TextView text_menos;
    private TextView text_entre;
    private TextView text_mas;
    private TextView text_q1;
    private TextView text_q2;
    private TextView text_q3;
    private String faciles;
    private String normales;
    private String dificiles;
    private String menos;
    private String entre;
    private String mas;
    private String entusiasmado;
    private String desinteresado;
    private String aburrido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesdetail);

        intent = getIntent();
        obtener_datos_vista();
        obtenerDatosPrevios();
        colocarDatos();
        crear_listener();
    }

    private void obtener_datos_vista(){
        ver_detalles = (Button) findViewById(R.id.detalle_SES);
        cant_apoderados = (TextView) findViewById(R.id.cant_apoderados);
        analisis = (TextView) findViewById(R.id.analisis);
        text_facil = (TextView) findViewById(R.id.respuestas_faciles);
        text_normal = (TextView) findViewById(R.id.respuestas_normales);
        text_dificil = (TextView) findViewById(R.id.respuestas_dificiles);
        text_menos = (TextView) findViewById(R.id.respuestas_menos);
        text_entre = (TextView) findViewById(R.id.respuestas_entre);
        text_mas = (TextView) findViewById(R.id.respuestas_mas);
        text_q1 = (TextView) findViewById(R.id.respuestas_pregunta3_1);
        text_q2 = (TextView) findViewById(R.id.respuestas_pregunta3_2);
        text_q3 = (TextView) findViewById(R.id.respuestas_pregunta3_3);
    }

    private void colocarDatos()
    {
        total = Integer.valueOf(faciles )+ Integer.valueOf(normales)+ Integer.valueOf(dificiles);

        cant_apoderados.setText(String.valueOf(total));
        text_facil.setText("Fácil: "+faciles);
        text_normal.setText("Regular: "+normales);
        text_dificil.setText("Difícil: "+dificiles);
        text_menos.setText("Menos de 5: "+menos);
        text_entre.setText("Entre 5 y 10: "+entre);
        text_mas.setText("Más de 10: "+mas);
        text_q1.setText("Entusiasmado: "+entusiasmado);
        text_q2.setText("Desinteresado: "+desinteresado);
        text_q3.setText("Aburrido: "+aburrido);
    }

    private void obtenerDatosPrevios()
    {
        id = intent.getStringExtra("id");
        colegio = intent.getStringExtra("colegio");
        faciles = intent.getStringExtra("faciles");
        normales = intent.getStringExtra("normales");
        dificiles = intent.getStringExtra("dificiles");
        menos = intent.getStringExtra("menos");
        entre = intent.getStringExtra("entre");
        mas = intent.getStringExtra("mas");
        entusiasmado = intent.getStringExtra("entusiasmado");
        desinteresado = intent.getStringExtra("desinteresado");
        aburrido = intent.getStringExtra("aburrido");
    }

    private void crear_listener(){

        ver_detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SESDetailActivity.this, AnswersActivity.class);

                intent.putExtra("id", id);
                intent.putExtra("colegio", colegio);

                startActivity(intent);
            }
        });
    }

}
