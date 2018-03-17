package com.usach.sebastianvallejos.scapprofesores.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.usach.sebastianvallejos.scapprofesores.Models.Profesores;
import com.usach.sebastianvallejos.scapprofesores.R;

public class MainMenuActivity extends AppCompatActivity {

    //Variables a utilizar
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private Intent intent;
    private Profesores profesor = new Profesores();
    private ImageButton botonCalendario;
    private ImageButton botonSes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Obtenemos el intent de esta actividad
        intent = getIntent();

        //Se encuentran los botones de la vista
        botonCalendario = (ImageButton) findViewById(R.id.boton_calendario);
        botonSes = (ImageButton) findViewById(R.id.boton_ses);


        buscarProfesor(intent.getStringExtra("correo"),intent.getStringExtra("colegio"));

        crearBotonCalendario();
        crearBotonSes();
    }

    //Funcion encargada de buscar al profesor actual en la base de datos
    private void buscarProfesor(String correo, final String colegio)
    {
        //Obtenemos la base de datos
        DatabaseReference profesoresRef = mDatabase.getReference(colegio);

        //Hacemos la consulta
        profesoresRef.child("profesores").orderByChild("correo").equalTo(correo).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                profesor = dataSnapshot.getValue(Profesores.class);
                profesor.setId(dataSnapshot.getKey().toString());
                profesor.setColegio(colegio);
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

    //Le otorgamos una funcion al boton
    private void crearBotonCalendario()
    {
        botonCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCalendario = new Intent(MainMenuActivity.this,CalendarActivity.class);

                //Traspasamos informacion importante para la siguiente vista
                intentCalendario.putExtra("nombre",profesor.getNombre());
                intentCalendario.putExtra("apellidoPaterno",profesor.getApellidoPaterno());
                intentCalendario.putExtra("apellidoMaterno",profesor.getApellidoMaterno());
                intentCalendario.putExtra("correo",profesor.getCorreo());
                intentCalendario.putExtra("id",profesor.getId());
                intentCalendario.putExtra("colegio",profesor.getColegio());

                startActivity(intentCalendario);

            }
        });
    }

    private void crearBotonSes()
    {
        botonSes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSes = new Intent(MainMenuActivity.this,SESMenuActivity.class);

                //Traspasamos informacion importante para la siguiente vista
                intentSes.putExtra("nombre",profesor.getNombre());
                intentSes.putExtra("apellidoPaterno",profesor.getApellidoPaterno());
                intentSes.putExtra("apellidoMaterno",profesor.getApellidoMaterno());
                intentSes.putExtra("correo",profesor.getCorreo());
                intentSes.putExtra("id",profesor.getId());
                intentSes.putExtra("colegio",profesor.getColegio());

                startActivity(intentSes);

            }
        });

    }

}
