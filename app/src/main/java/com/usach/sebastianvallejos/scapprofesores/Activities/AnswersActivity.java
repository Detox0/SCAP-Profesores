package com.usach.sebastianvallejos.scapprofesores.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.usach.sebastianvallejos.scapprofesores.Models.Answers;
import com.usach.sebastianvallejos.scapprofesores.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnswersActivity extends AppCompatActivity {

    private FirebaseDatabase mDataBase = FirebaseDatabase.getInstance();
    private Intent intent;
    private List<Button> boton = new ArrayList<Button>();
    private LinearLayout scroll_view;
    private String id;
    private String colegio;
    private List<Answers> respuestas = new ArrayList<Answers>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        intent = getIntent();

        scroll_view = (LinearLayout) findViewById(R.id.scroll_view_answers);
        obtenerDatos();
    }

    private void obtenerDatos(){
        this.id = intent.getStringExtra("id");
        this.colegio = intent.getStringExtra("colegio");
        consultar_BD();
    }

    private void consultar_BD(){
        DatabaseReference answersRef = mDataBase.getReference(colegio);
        answersRef.child("ses").child(id).child("respuestas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    HashMap<String, String> respuesta = (HashMap<String, String>) data.getValue();
                    Answers respu = new Answers();
                    respu.setNombre(respuesta.get("nombre"));
                    respu.setApellidoPaterno(respuesta.get("apellidoPaterno"));
                    respu.setApellidoMaterno(respuesta.get("apellidoMaterno"));
                    respu.setPregunta3(respuesta.get("respuesta_motivacion"));
                    respu.setDificultad(respuesta.get("respuesta_dificultad"));
                    respu.setDetalle(respuesta.get("detalle"));
                    respu.setTiempo(respuesta.get("respuesta_tiempo"));
                    respu.setIdApoderado(respuesta.get("idApoderado"));
                    crear_boton_respuesta(respu);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void crear_boton_respuesta(Answers resp){
        //Parametros necesarios para colocar los elementos nuevos en la vista
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        Button boton_respuesta = new Button(this);
        String nombre_boton = resp.getNombre() + " " + resp.getApellidoPaterno();
        boton_respuesta.setText(nombre_boton);
        setear_listener(resp, boton_respuesta);
        scroll_view.addView(boton_respuesta, params);
    }

    private void setear_listener(final Answers resp, Button boton){
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnswersActivity.this, DetailAnswerActivity.class);

                intent.putExtra("nombre", resp.getNombre());
                intent.putExtra("apellidoPaterno", resp.getApellidoPaterno());
                intent.putExtra("apellidoMaterno", resp.getApellidoMaterno());
                intent.putExtra("tiempo", resp.getTiempo());
                intent.putExtra("dificultad", resp.getDificultad());
                intent.putExtra("motivacion", resp.getPreguntaMotivacion());
                intent.putExtra("detalle", resp.getDetalle());
                startActivity(intent);
            }
        });
    }

}
