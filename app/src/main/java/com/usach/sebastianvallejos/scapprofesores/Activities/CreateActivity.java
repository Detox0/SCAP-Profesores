package com.usach.sebastianvallejos.scapprofesores.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.usach.sebastianvallejos.scapprofesores.Models.Ses;
import com.usach.sebastianvallejos.scapprofesores.R;

import java.util.ArrayList;
import java.util.List;

public class CreateActivity extends AppCompatActivity {

    //Variables a utilizar
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private List<String> materias = new ArrayList<String>();
    private Spinner spinnerMaterias;
    private Profesores profesor;
    private EditText descripcion;
    private Intent intent;
    private String tipo;
    private Button boton;
    private TextView titulo;
    private TextView desc_actividad;
    private String fecha;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        intent = getIntent();
        descripcion = (EditText) findViewById(R.id.descripcion_actividad);
        boton = (Button) findViewById(R.id.boton_crear_actividad);
        titulo = (TextView) findViewById(R.id.titulo_crear_actividad);
        desc_actividad = (TextView) findViewById(R.id.descripcion_crear_actividad);
        spinnerMaterias = (Spinner) findViewById(R.id.spinner_materias);
        tipo = intent.getStringExtra("tipo");
        fecha = intent.getStringExtra("fecha");

        crearProfesor();
        setearTitulos();

        //Si la actividad es ses u otra poseen diferentes listeners
        if (tipo.equals("ses"))
        {
            setearListenerSes();
        }else{
            setearListenerActividades();
        }

    }

    private void crearProfesor()
    {
        profesor.setColegio(intent.getStringExtra("colegio"));
        profesor.setId(intent.getStringExtra("id"));
        profesor.setNombre(intent.getStringExtra("nombre"));
        profesor.setCorreo(intent.getStringExtra("correo"));
        profesor.setApellidoMaterno(intent.getStringExtra("apellidoMaterno"));
        profesor.setApellidoPaterno(intent.getStringExtra("apellidoPaterno"));

        setearSpinner();
    }

    private void setearSpinner()
    {
        DatabaseReference materiasRef = mDatabase.getReference(profesor.getColegio());

        materiasRef.child("profesor/"+profesor.getId()+"/materias").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                materias.add(dataSnapshot.getKey().toString());

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

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,materias);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerMaterias.setAdapter(spinnerArrayAdapter);
    }

    private void setearTitulos()
    {

        if (tipo.equals("Tarea"))
        {
            titulo.setText("Tarea");
            desc_actividad.setText("Aqui puedes ingresar la descripción de la tarea a solicitar, recuerda que esta tarea va a ser vista por los apoderados para incentivar el estudio de los alumnos.");
        }
        else if(tipo.equals("Prueba"))
        {
            titulo.setText("Prueba");
            desc_actividad.setText("Aquí puedes informar a los apoderados de alguna evaluación que desees realizar, recuerda tratar de informar los tópicos a evaluar para que ellos puedan repasar con sus alumnos.");
        }
        else if(tipo.equals("ses")){
            titulo.setText("Sistema Estudio Semanal");
            desc_actividad.setText("Aqui puedes colocar la actividad dirigida a los padres para desarrollar con sus hijos, recuerda que esta actividad debe ser corta, pero concisa para que tenga un impacto frente al apoderado y estudiante.");
        }
        else
        {
            titulo.setText("Materiales");
            desc_actividad.setText("Aquí puedes solicitar materiales para tu clase, estos le llegarán a los apoderados, igualmente hazles llegar a los alumnos esta lista, para no quitarles responsabilidades a ellos.");
        }
    }

    private void setearListenerActividades()
    {
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Actividad actividad = new Actividad();

                //Revisamos que haya un texto escrito
                if(descripcion.getText() != null)
                {
                    obtenerIdActividades();

                    if(id.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Ha ocurrido un error inesperado, intente nuevamente.",Toast.LENGTH_LONG);
                    }
                    else
                    {
                        DatabaseReference guardarRef = mDatabase.getReference(profesor.getColegio());

                        actividad.setDescripcion(descripcion.getText().toString());
                        actividad.setFecha(fecha);
                        actividad.setTipo(tipo);
                        actividad.setProfesor(profesor.getNombre() + " " + profesor.getApellidoPaterno());
                        //Verificar esto
                        //actividad.setMateria();
                        actividad.setSeccion(intent.getStringExtra("seccion"));

                        Integer numero = Integer.valueOf(id);
                        numero++;
                        id = numero.toString();

                        guardarRef.child("fechas").child(id).setValue(actividad);
                        guardarRef.child("config").child("actividades").setValue(id);

                        Toast.makeText(getApplicationContext(),"Actividad guardada exitosamente.",Toast.LENGTH_SHORT);

                        finish();


                    }
                }
                else
                {
                    Toast.makeText(CreateActivity.this, "No ha ingresado una descripcion, porfavor ingrese una.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setearListenerSes()
    {
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Ses actividad = new Ses();

                //Revisamos que haya un texto escrito
                if(descripcion.getText() != null)
                {
                    obtenerIdSes();

                    if(id.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Ha ocurrido un error inesperado, intente nuevamente.",Toast.LENGTH_LONG);
                    }
                    else
                    {
                        DatabaseReference guardarRef = mDatabase.getReference(profesor.getColegio());

                        actividad.setDescripcion(descripcion.getText().toString());
                        actividad.setFecha(fecha);
                        actividad.setProfesor(profesor.getNombre() + " " + profesor.getApellidoPaterno());
                        //VERIFICAR ESTO
                        //actividad.setMateria();
                        actividad.setSeccion(intent.getStringExtra("seccion"));
                        actividad.setNegativas("0");
                        actividad.setPositivas("0");

                        Integer numero = Integer.valueOf(id);
                        numero++;
                        id = numero.toString();

                        guardarRef.child("ses").child(id).setValue(actividad);
                        guardarRef.child("config").child("ses").setValue(id);

                        Toast.makeText(getApplicationContext(),"Actividad guardada exitosamente.",Toast.LENGTH_SHORT);

                        finish();

                    }
                }
                else
                {
                    Toast.makeText(CreateActivity.this, "No ha ingresado una descripcion, porfavor ingrese una.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void obtenerIdActividades()
    {
        DatabaseReference configRef = mDatabase.getReference(profesor.getColegio());

        configRef.child("config").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().toString().equals("actividades"))
                {
                    id = dataSnapshot.getValue().toString();
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

    private void obtenerIdSes()
    {
        DatabaseReference configRef = mDatabase.getReference(profesor.getColegio());

        configRef.child("config").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().toString().equals("ses"))
                {
                    id = dataSnapshot.getValue().toString();
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
}
