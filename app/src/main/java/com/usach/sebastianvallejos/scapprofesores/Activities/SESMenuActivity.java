package com.usach.sebastianvallejos.scapprofesores.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.usach.sebastianvallejos.scapprofesores.Models.Profesores;
import com.usach.sebastianvallejos.scapprofesores.Models.Ses;
import com.usach.sebastianvallejos.scapprofesores.R;

public class SESMenuActivity extends AppCompatActivity {

    //Variables a utilizar
    private FirebaseDatabase mDataBase = FirebaseDatabase.getInstance();
    private Intent intent;
    private Profesores profesor = new Profesores();
    private LinearLayout layout_ses;
    private ImageButton botonSesNuevo;
    private String idSes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesmenu);

        intent  = getIntent();

        layout_ses = (LinearLayout) findViewById(R.id.layout_SES);
        botonSesNuevo = (ImageButton) findViewById(R.id.crear_ses);

        crearProfesor();
        setearListenerSesNuevo();
    }

    //Funcion encargada de recuperar los datos anteriores y crear un profesor
    private void crearProfesor()
    {
        profesor.setColegio(intent.getStringExtra("colegio"));
        profesor.setId(intent.getStringExtra("id"));
        profesor.setNombre(intent.getStringExtra("nombre"));
        profesor.setCorreo(intent.getStringExtra("correo"));
        profesor.setApellidoMaterno(intent.getStringExtra("apellidoMaterno"));
        profesor.setApellidoPaterno(intent.getStringExtra("apellidoPaterno"));

        obtenerActividadesSES();
    }

    private void obtenerActividadesSES()
    {
        DatabaseReference sesRef = mDataBase.getReference(profesor.getColegio());

        sesRef.child("profesores/"+profesor.getId()+"/actividadesSES").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                idSes = dataSnapshot.getKey().toString();
                recuperarActividad();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void recuperarActividad()
    {
        DatabaseReference infoSesRef = mDataBase.getReference(profesor.getColegio());

        //Ubicamos la actividad y la obtenemos
        infoSesRef.child("ses").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().toString().equals(idSes))
                {
                    Ses actividad = dataSnapshot.getValue(Ses.class);
                    actividad.setId(dataSnapshot.getKey());
                    crearBoton(actividad);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void crearBoton(Ses actividad)
    {
        //Parametros necesarios para colocar los elementos nuevso en la vista
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        //Se crea el boton y se le asigna un Listener
        Button botonVista = new Button(this);
        botonVista.setText(actividad.getMateria());
        setearListenerSesExistente(actividad, botonVista);
        layout_ses.addView(botonVista,params);
    }

    private void setearListenerSesExistente(final Ses actividad, Button boton)
    {
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{

                    Intent intentSes = new Intent(SESMenuActivity.this, SESDetailActivity.class);

                    intentSes.putExtra("positivos",actividad.getPositivas());
                    intentSes.putExtra("negativos",actividad.getNegativas());

                    startActivity(intentSes);
                }
                catch(Exception e) {
                    Toast.makeText(getApplicationContext(),""+e.toString(),Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void setearListenerSesNuevo()
    {
        botonSesNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    Intent intentSes = new Intent(SESMenuActivity.this, CreateActivity.class);

                    //Traspasamos informacion importante para la siguiente vista
                    intentSes.putExtra("nombre",profesor.getNombre());
                    intentSes.putExtra("apellidoPaterno",profesor.getApellidoPaterno());
                    intentSes.putExtra("apellidoMaterno",profesor.getApellidoMaterno());
                    intentSes.putExtra("correo",profesor.getCorreo());
                    intentSes.putExtra("id",profesor.getId());
                    intentSes.putExtra("colegio",profesor.getColegio());
                    intentSes.putExtra("tipo","ses");

                    startActivity(intentSes);
                }
                catch (Exception e){
                    Toast.makeText(SESMenuActivity.this, e.toString(),Toast.LENGTH_SHORT);
                }
            }
        });
    }

}
