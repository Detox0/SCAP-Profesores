package com.usach.sebastianvallejos.scapprofesores.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.usach.sebastianvallejos.scapprofesores.Models.Actividad;
import com.usach.sebastianvallejos.scapprofesores.Models.Profesores;
import com.usach.sebastianvallejos.scapprofesores.R;

import java.util.ArrayList;
import java.util.List;


public class MenuActivity extends AppCompatActivity {

    //Variables a utilizar
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private Spinner spinner;
    private Profesores profesor = new Profesores();
    private Intent intent;
    private String fecha;
    private List<Actividad> actividades = new ArrayList<Actividad>();
    private List<String> cursos = new ArrayList<String>();
    private Button boton_tarea;
    private Button boton_materiales;
    private Button boton_prueba;
    private TextView titulo_actividades;
    private LinearLayout layoutActividades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        intent = getIntent();
        layoutActividades = (LinearLayout) findViewById(R.id.actividades_existentes);
        titulo_actividades = (TextView) findViewById(R.id.titulo_dia_actividades);
        spinner = (Spinner) findViewById(R.id.spinner_cursos);
        boton_tarea = (Button) findViewById(R.id.boton_tarea);
        boton_materiales = (Button) findViewById(R.id.boton_materiales);
        boton_prueba = (Button) findViewById(R.id.boton_prueba);

        crearProfesor();
        obtenerFecha();
        obtenerCursos();
        accionBotonTarea();
        accionBotonMateriales();
        accionBotonPrueba();
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
    }

    //Funcion que recupera la fecha y la de formato
    private void obtenerFecha()
    {
        String dia,mes,ano;

        dia = intent.getStringExtra("dia");
        mes = intent.getStringExtra("mes");
        ano = intent.getStringExtra("ano");

        fecha = ano + "/" + mes + "/" + dia;
    }

    private void obtenerCursos()
    {
        DatabaseReference cursosRef = mDatabase.getReference(profesor.getColegio());

        cursosRef.child("profesores/"+profesor.getId()+"/cursos").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                cursos.add(dataSnapshot.getKey());
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

        //Ahora con los datos necesarios rellenamos spinner
        rellenarSpinner();
    }

    private void rellenarSpinner()
    {
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,cursos);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }

    private void accionBotonTarea()
    {
        //verificamos que se ha seleccionado un curso
        //VERIFICAR
        if(spinner.getSelectedItem() != null)
        {
            Intent intentTarea = new Intent(MenuActivity.this,DetailActivity.class);

            //Pasamos informacion a la siguiente vista
            intentTarea.putExtra("fecha",fecha);
            intentTarea.putExtra("nombre",profesor.getNombre());
            intentTarea.putExtra("apellidoPaterno",profesor.getApellidoPaterno());
            intentTarea.putExtra("apellidoMaterno",profesor.getApellidoMaterno());
            intentTarea.putExtra("correo",profesor.getCorreo());
            intentTarea.putExtra("id",profesor.getId());
            intentTarea.putExtra("colegio",profesor.getColegio());
            intentTarea.putExtra("tipo","Tarea");
            intentTarea.putExtra("seccion",spinner.getSelectedItem().toString());

            startActivity(intentTarea);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No se ha seleccionado curso, profavor seleccione un e intente nuevamente.", Toast.LENGTH_LONG).show();

        }
    }

    private void accionBotonMateriales()
    {
        //verificamos que se ha seleccionado un curso
        //VERIFICAR
        if(spinner.getSelectedItem() != null)
        {
            Intent intentMateriales = new Intent(MenuActivity.this,DetailActivity.class);

            //Pasamos informacion a la siguiente vista
            intentMateriales.putExtra("fecha",fecha);
            intentMateriales.putExtra("nombre",profesor.getNombre());
            intentMateriales.putExtra("apellidoPaterno",profesor.getApellidoPaterno());
            intentMateriales.putExtra("apellidoMaterno",profesor.getApellidoMaterno());
            intentMateriales.putExtra("correo",profesor.getCorreo());
            intentMateriales.putExtra("id",profesor.getId());
            intentMateriales.putExtra("colegio",profesor.getColegio());
            intentMateriales.putExtra("tipo","Materiales");

            startActivity(intentMateriales);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No se ha seleccionado curso, profavor seleccione un e intente nuevamente.", Toast.LENGTH_LONG).show();

        }
    }

    private void accionBotonPrueba()
    {
        //verificamos que se ha seleccionado un curso
        //VERIFICAR
        if(spinner.getSelectedItem() != null)
        {
            Intent intentPrueba = new Intent(MenuActivity.this,DetailActivity.class);

            //Pasamos informacion a la siguiente vista
            intentPrueba.putExtra("fecha",fecha);
            intentPrueba.putExtra("nombre",profesor.getNombre());
            intentPrueba.putExtra("apellidoPaterno",profesor.getApellidoPaterno());
            intentPrueba.putExtra("apellidoMaterno",profesor.getApellidoMaterno());
            intentPrueba.putExtra("correo",profesor.getCorreo());
            intentPrueba.putExtra("id",profesor.getId());
            intentPrueba.putExtra("colegio",profesor.getColegio());
            intentPrueba.putExtra("tipo","Prueba");

            startActivity(intentPrueba);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No se ha seleccionado curso, profavor seleccione un e intente nuevamente.", Toast.LENGTH_LONG).show();

        }
    }

    //Obtenemos las actividades de un dia y curso especificos
    private void obtenerActividades()
    {
        DatabaseReference actividadesRef = mDatabase.getReference(profesor.getColegio());

        actividadesRef.orderByChild("fechas").equalTo(fecha).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //Actividad correspondiente a ese dia
                Actividad activity = dataSnapshot.getValue(Actividad.class);

                //Si la actividad no corresponde con el curso actual, no se agrega
                if(activity.getSeccion().equals(spinner.getSelectedItem().toString()))
                {
                    actividades.add(activity);
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

        //Si existen actividades las agregamos, en caso contrario mostramos un mensaje de que no hay
        if(actividades.size() != 0)
        {
            colocarActividades();
        }
        else
        {
            titulo_actividades.setText("No existen actividades para este curso este dia.");
        }
    }

    //Se colocan las actividades para cierto dia en cierto curso
    private void colocarActividades()
    {
        titulo_actividades.setText("Las actividades para este curso, este dia son:");

        for(int i=0; i<actividades.size(); i++)
        {
            //Parametros necesarios para colocar los elementos nuevso en la vista
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            //Se crea el boton y se le asigna un Listener
            Button botonVista = new Button(this);
            botonVista.setText(actividades.get(i).getTipo());
            setearListener(botonVista,actividades.get(i));
            layoutActividades.addView(botonVista,params);
        }
    }

    //Le agregamos un listener a cada boton creado
    private void setearListener(Button boton, final Actividad activity)
    {

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDetalle = new Intent(MenuActivity.this, DetailActivity.class);

                intentDetalle.putExtra("tipo",activity.getTipo());
                intentDetalle.putExtra("fecha",fecha);
                intentDetalle.putExtra("descripcion",activity.getDescripcion());
                intentDetalle.putExtra("profesor",activity.getProfesor());

                startActivity(intentDetalle);
            }
        });

    }

}
