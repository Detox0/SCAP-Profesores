package com.usach.sebastianvallejos.scapprofesores.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

import com.usach.sebastianvallejos.scapprofesores.Models.Profesores;
import com.usach.sebastianvallejos.scapprofesores.R;

public class CalendarActivity extends AppCompatActivity {

    //Creamos las variables a utilizar
    private Intent intent;
    private CalendarView calendario;
    private Profesores profesor = new Profesores();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendario = (CalendarView) findViewById(R.id.calendario);

        crearProfesor();
        respuestaCalendario();

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

    //Una vez que se obtenga la fecha deseada, se procedera a la siguiente actividad
    private void respuestaCalendario()
    {
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {

                //creamos el intent para la siguiente vista
                Intent intentActividad = new Intent(CalendarActivity.this,MenuActivity.class);

                //Pasamos informacion a la siguiente vista
                intentActividad.putExtra("dia",day);
                intentActividad.putExtra("mes",month);
                intentActividad.putExtra("ano",year);
                intentActividad.putExtra("nombre",profesor.getNombre());
                intentActividad.putExtra("apellidoPaterno",profesor.getApellidoPaterno());
                intentActividad.putExtra("apellidoMaterno",profesor.getApellidoMaterno());
                intentActividad.putExtra("correo",profesor.getCorreo());
                intentActividad.putExtra("id",profesor.getId());
                intentActividad.putExtra("colegio",profesor.getColegio());

                startActivity(intentActividad);
            }
        });
    }

}
