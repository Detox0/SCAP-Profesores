package com.usach.sebastianvallejos.scapprofesores.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.google.firebase.database.ValueEventListener;
import com.usach.sebastianvallejos.scapprofesores.Models.Actividad;
import com.usach.sebastianvallejos.scapprofesores.Models.Profesores;
import com.usach.sebastianvallejos.scapprofesores.Models.Ses;
import com.usach.sebastianvallejos.scapprofesores.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateActivity extends AppCompatActivity {

    //Variables a utilizar
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private List<String> materias = new ArrayList<String>();
    private List<String> cursos = new ArrayList<String>();
    private Spinner spinnerMaterias;
    private Profesores profesor = new Profesores();
    private EditText descripcion;
    private Intent intent;
    private String tipo;
    private Button boton;
    private TextView titulo;
    private TextView desc_actividad;
    private String fecha;
    private String id = "";
    private TextView desc_cursos_ses;
    private Spinner cursos_ses;

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
            desc_cursos_ses = (TextView) findViewById(R.id.desc_curso_Ses);
            cursos_ses = (Spinner) findViewById(R.id.cursos_ses);
            cursos_ses.setVisibility(View.VISIBLE);

            rellenarVistaSes();
            obtenerIdSes();
            setearListenerSes();
        }else{
            obtenerIdActividades();
            setearListenerActividades();
        }

    }

    private void rellenarVistaSes()
    {
        desc_cursos_ses.setText("Seleccione el curso donde va a realizar la actividad:");

        DatabaseReference cursosRef = mDatabase.getReference(profesor.getColegio());

        cursosRef.child("profesores/"+profesor.getId()+"/secciones").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren())
                {
                    String curso = data.getKey().toString();
                    cursos.add(curso);
                }
                //Le entregamos los datos al spinner
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateActivity.this, android.R.layout.simple_spinner_item, cursos);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cursos_ses.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

        materiasRef.child("profesores/"+profesor.getId()+"/materias").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren())
                {
                    String materia = data.getKey().toString();
                    materias.add(materia);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateActivity.this, android.R.layout.simple_spinner_item, materias);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerMaterias.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
                    DatabaseReference guardarRef = mDatabase.getReference(profesor.getColegio());

                    actividad.setDescripcion(descripcion.getText().toString());
                    actividad.setFecha(fecha);
                    actividad.setTipo(tipo);
                    actividad.setProfesor(profesor.getNombre() + " " + profesor.getApellidoPaterno());
                    actividad.setMateria(spinnerMaterias.getSelectedItem().toString());
                    actividad.setSeccion(intent.getStringExtra("seccion"));

                    Integer numero = Integer.valueOf(id);
                    numero++;
                    id = numero.toString();

                    guardarRef.child("fechas").child(id).setValue(actividad);
                    guardarRef.child("config").child("actividades").setValue(id);

                    Toast.makeText(getApplicationContext(),"Actividad guardada exitosamente.",Toast.LENGTH_SHORT).show();

                    finish();

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
                    DatabaseReference guardarRef = mDatabase.getReference(profesor.getColegio());

                    actividad.setDescripcion(descripcion.getText().toString());

                    //Obtenemos la fecha actual
                    Date tiempo = Calendar.getInstance().getTime();
                    fecha = new SimpleDateFormat("yyyy/MM/dd").format(tiempo);
                    actividad.setFecha(fecha);

                    actividad.setProfesor(profesor.getNombre() + " " + profesor.getApellidoPaterno());
                    actividad.setMateria(spinnerMaterias.getSelectedItem().toString());
                    actividad.setSeccion(cursos_ses.getSelectedItem().toString());
                    actividad.setNegativas(0);
                    actividad.setPositivas(0);

                    Integer numero = Integer.valueOf(id);
                    numero++;
                    id = numero.toString();

                    //Se guarda la actividad
                    guardarRef.child("ses").child(id).setValue(actividad);
                    //Se guardan las cantidades de actividades actuales en la seccion de configuracion
                    guardarRef.child("config").child("ses").setValue(id);
                    //Se guarda en el profesor el ID de la actividad SES
                    //REVISAR
                    guardarRef.child("profesores/"+profesor.getId()+"/actividadesSES/").setValue(id,true);


                    Toast.makeText(getApplicationContext(),"Actividad guardada exitosamente.",Toast.LENGTH_SHORT).show();

                    finish();
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
