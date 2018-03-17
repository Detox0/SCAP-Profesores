package com.usach.sebastianvallejos.scapprofesores.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.usach.sebastianvallejos.scapprofesores.R;

public class SESDetailActivity extends AppCompatActivity {

    //Variables a utilizar
    private Intent intent;
    private long positivos;
    private long negativos;
    private long total;
    private TextView cant_apoderados;
    private TextView cant_correctas;
    private TextView cant_erroneas;
    private TextView analisis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesdetail);

        intent = getIntent();

        cant_apoderados = (TextView) findViewById(R.id.cant_apoderados);
        cant_correctas = (TextView) findViewById(R.id.cant_correctas);
        cant_erroneas = (TextView) findViewById(R.id.cant_erroneas);
        analisis = (TextView) findViewById(R.id.analisis);

        obtenerDatosPrevios();
        colocarDatos();
    }

    private void colocarDatos()
    {
        total = positivos+negativos;

        cant_apoderados.setText(String.valueOf(total));
        cant_correctas.setText(String.valueOf(positivos));
        cant_erroneas.setText(String.valueOf(negativos));

        if(total<7)
        {
            analisis.setText("Tu actividad aun no ha sido respondida por suficientes apoderados, espera un poco para que la resuelvan.");
        }
        else
        {
            if (positivos>negativos)
            {
                analisis.setText("¡Vas muy bien!, ahora solo falta revisar esos casos donde no se entendió muy bien la pregunta, pero en general, ¡felicitaciones!.");
            }
            else if(negativos > positivos)
            {
                analisis.setText("Al parecer la actividad no ha sido bien recibida dentro de tus estudiantes, si puedes, vuelve a revisar esta materia con ellos.");
            }
            else
            {
                analisis.setText("La mitad de tus estudiantes han podido responder bien esta pregunta, al igual que la cantidad de estudiantes que no han podido responderla correctamente, si puedes, revisa esta actividad con ellos.");
            }
        }

    }

    private void obtenerDatosPrevios()
    {
        positivos = intent.getLongExtra("positivos",0);
        negativos = intent.getLongExtra("negativos",0);
    }

}
