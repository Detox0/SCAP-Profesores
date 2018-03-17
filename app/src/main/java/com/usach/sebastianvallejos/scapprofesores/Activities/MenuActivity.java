package com.usach.sebastianvallejos.scapprofesores.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.google.firebase.database.ValueEventListener;
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
    private Integer actividades;
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


        obtenerFecha();
        crearProfesor();
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
        obtenerCursos();
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

    //Funcion que busca los cursos del profesor actual
    private void obtenerCursos()
    {
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
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MenuActivity.this, android.R.layout.simple_spinner_item, cursos);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);

                //Accion para que cuando se cambie el curso se haga nuevamente la consulta
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        //Limpiar la vista
                        limpiarActividades();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                obtenerActividades();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Agregamos un listener al boton tarea
    private void accionBotonTarea()
    {
        boton_tarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verificamos que se ha seleccionado un curso
                if(spinner.getSelectedItem() != null)
                {
                    Intent intentTarea = new Intent(MenuActivity.this,CreateActivity.class);

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
        });
    }

    //Agregamos un listener al boton materiales
    private void accionBotonMateriales()
    {
        boton_materiales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verificamos que se ha seleccionado un curso
                if(spinner.getSelectedItem() != null)
                {
                    Intent intentMateriales = new Intent(MenuActivity.this,CreateActivity.class);

                    //Pasamos informacion a la siguiente vista
                    intentMateriales.putExtra("fecha",fecha);
                    intentMateriales.putExtra("nombre",profesor.getNombre());
                    intentMateriales.putExtra("apellidoPaterno",profesor.getApellidoPaterno());
                    intentMateriales.putExtra("apellidoMaterno",profesor.getApellidoMaterno());
                    intentMateriales.putExtra("correo",profesor.getCorreo());
                    intentMateriales.putExtra("id",profesor.getId());
                    intentMateriales.putExtra("colegio",profesor.getColegio());
                    intentMateriales.putExtra("tipo","Materiales");
                    intentMateriales.putExtra("seccion",spinner.getSelectedItem().toString());

                    startActivity(intentMateriales);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No se ha seleccionado curso, profavor seleccione un e intente nuevamente.", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    //Agregamos un listener al boton prueba
    private void accionBotonPrueba()
    {
        boton_prueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verificamos que se ha seleccionado un curso
                if(spinner.getSelectedItem() != null)
                {
                    Intent intentPrueba = new Intent(MenuActivity.this,CreateActivity.class);

                    //Pasamos informacion a la siguiente vista
                    intentPrueba.putExtra("fecha",fecha);
                    intentPrueba.putExtra("nombre",profesor.getNombre());
                    intentPrueba.putExtra("apellidoPaterno",profesor.getApellidoPaterno());
                    intentPrueba.putExtra("apellidoMaterno",profesor.getApellidoMaterno());
                    intentPrueba.putExtra("correo",profesor.getCorreo());
                    intentPrueba.putExtra("id",profesor.getId());
                    intentPrueba.putExtra("colegio",profesor.getColegio());
                    intentPrueba.putExtra("tipo","Prueba");
                    intentPrueba.putExtra("seccion",spinner.getSelectedItem().toString());

                    startActivity(intentPrueba);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No se ha seleccionado curso, porfavor seleccione uno e intente nuevamente.", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    //Obtenemos las actividades de un dia y curso especificos
    private void obtenerActividades()
    {
        actividades = 0;

        DatabaseReference actividadesRef = mDatabase.getReference(profesor.getColegio());

        actividadesRef.child("fechas").orderByChild("fecha").equalTo(fecha).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    //Actividad correspondiente a ese dia
                    Actividad activity = data.getValue(Actividad.class);

                    Log.i("PRUEBA","La seccion es: "+activity.getSeccion());
                    Log.i("PRUEBA","El spinner tiene: "+spinner.getSelectedItem().toString());

                    if(activity.getSeccion() != null && spinner.getSelectedItem() != null)
                    {
                        //Si la actividad no corresponde con el curso actual, no se agrega
                        if(activity.getSeccion().equals(spinner.getSelectedItem().toString()))
                        {
                            colocarActividades(activity);
                            actividades++;
                        }
                    }


                }

                //Si existen actividades las agregamos, en caso contrario mostramos un mensaje de que no hay
                if(actividades == 0)
                {
                    titulo_actividades.setText("No existen actividades para este curso este dia.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //Se colocan las actividades para cierto dia en cierto curso
    private void colocarActividades(Actividad actividad)
    {
        titulo_actividades.setText("Las actividades para este curso, este dia son:");

        //Parametros necesarios para colocar los elementos nuevso en la vista
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        //Se crea el boton y se le asigna un Listener
        Button botonVista = new Button(this);
        botonVista.setText(actividad.getTipo());
        setearListener(botonVista,actividad);
        layoutActividades.addView(botonVista,params);

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
                intentDetalle.putExtra("materia",activity.getMateria());
                intentDetalle.putExtra("descripcion",activity.getDescripcion());
                intentDetalle.putExtra("profesor",activity.getProfesor());

                startActivity(intentDetalle);
            }
        });

    }

    //Limpia la vista para que se vean las nuevas actividades
    private void limpiarActividades()
    {
        //Si el layout presenta mas de un boton, se borra
        if (layoutActividades.getChildCount() > 0 )
        {
            layoutActividades.removeAllViews();
        }
        //Entregar las nuevas actividades
        obtenerActividades();
    }

}
