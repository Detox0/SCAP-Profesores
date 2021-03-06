package com.usach.sebastianvallejos.scapprofesores.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.usach.sebastianvallejos.scapprofesores.Models.Profesores;
import com.usach.sebastianvallejos.scapprofesores.R;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity{

    //Definicion de variables
    private String correo;
    private String colegio="Ninguno";
    private String contrasena;
    private Boolean profesor_registrado = false;
    private FirebaseAuth mAuth;
    private Spinner spinnerColegios;
    private Button botonLogin;
    private Profesores profesor = new Profesores();
    private FirebaseDatabase mDataBase = FirebaseDatabase.getInstance();
    private List<String> colegios = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        spinnerColegios = (Spinner) findViewById(R.id.fidget_colegio);
        botonLogin = (Button) findViewById(R.id.boton_login);
        //Llamamos a los colegios registrados en la app
        obtenerColegio();

        //Ahora que tenemos lo necesario para poder iniciar sesion, comenzamos la activity verificando si ya existe un usuario
        inicio();
        //En caso contrario, se crea el boton para iniciar la actividad
        crearBoton();
    }

    //Recuperar los colegios registrados en la app
    private void obtenerColegio()
    {
        DatabaseReference colegiosRef = mDataBase.getReference();

        colegiosRef.child("colegios").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren())
                {
                    String cole = data.getKey().toString();
                    colegios.add(cole);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_spinner_item, colegios);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerColegios.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Verificamos si el usuario se encuentra con la sesion iniciada o no
    private void inicio()
    {
        FirebaseUser usuario = mAuth.getCurrentUser();
        //En el caso de haber iniciado sesion, se continua inmediatamente a la siguiente activity
        if(usuario != null)
        {
            correo = usuario.getEmail();
            Intent intent = new Intent(LoginActivity.this, ColegiosActivity.class);
            intent.putExtra("correo",correo);
            //Aca puede ir un intent que nos pregunte por el colegio
            startActivity(intent);
        }
    }

    //Funcion encargada de recuperar el correo del usuario
    private String recuperarCorreo()
    {
        EditText campoCorreo = (EditText) findViewById(R.id.correo_usuario);
        return campoCorreo.getText().toString().toLowerCase().replaceAll("\\s+", "");

    }

    //Funcion encargada de recuperar la contrasena del usuario
    private String recuperarContrasena()
    {
        EditText campoContrasena = (EditText) findViewById(R.id.password_usuario);
        return campoContrasena.getText().toString();
    }

    //Dentro de esta funcion se crea el boton y se le asigna una accion
    void crearBoton()
    {
        //Listener para el boton, le otorga una accion al mismo
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = recuperarCorreo();
                contrasena = recuperarContrasena();

                if (spinnerColegios.getSelectedItem() != null)
                {
                    mAuth.signInWithEmailAndPassword(correo,contrasena)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    verificarProfesor();
                                    colegio = spinnerColegios.getSelectedItem().toString();
                                    if(task.isSuccessful() && profesor_registrado)
                                    {
                                        Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                                        intent.putExtra("correo",correo);
                                        intent.putExtra("colegio",colegio);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Log.w("ER","signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Usuario o contraseña inválidos.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
                else{
                    Toast.makeText(LoginActivity.this,"No se ha seleccionado un colegio, porfavor seleccione uno.", Toast.LENGTH_LONG).show();
                    Log.i("PRUEBA","No se ha seleccionado un colegio, porfavor seleccione uno.");
                }


            }
        });
    }

    //Verificamos que el profesor pertenece al colegio en cuestion
    //Funcion que verifica que un profesor se encuentra registrado en un colegio
    private void verificarProfesor()
    {
        DatabaseReference profesoresRef = mDataBase.getReference(colegio);
        profesoresRef.child("profesores").orderByChild("correo").equalTo(correo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profesor_registrado = true;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
