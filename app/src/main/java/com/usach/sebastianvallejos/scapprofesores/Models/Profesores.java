package com.usach.sebastianvallejos.scapprofesores.Models;

/**
 * Created by sebastianvallejos on 22-02-18.
 */

public class Profesores {

    //Atributos de la clase
    private String id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String colegio;
    private String edad;

    //Constructor requerido por FireBase
    public Profesores(){}

    //Constructor con asignacion de atributos incluidos
    public Profesores(String name, String last_namep, String last_namem, String email, String age)
    {
        this.nombre = name;
        this.apellidoPaterno = last_namep;
        this.apellidoMaterno = last_namem;
        this.correo = email;
        this.edad = age;

    }

    //Getters para obtener los datos de la clase
    public String getNombre() { return this.nombre; }
    public String getApellidoPaterno() { return this.apellidoPaterno; }
    public String getApellidoMaterno() { return this.apellidoMaterno; }
    public String getCorreo() { return this.correo; }
    public String getEdad() { return this.edad; }
    public String getId() { return this.id; }
    public String getColegio() { return this.colegio; }

    //Setters de la clase
    public void setNombre(String name) {this.nombre = name;}
    public void setApellidoPaterno(String apellidoP){this.apellidoPaterno = apellidoP;}
    public void setApellidoMaterno(String apellidoM){this.apellidoMaterno = apellidoM;}
    public void setCorreo(String email){this.correo = email;}
    public void setEdad(String age){this.edad = age;}
    public void setId(String aidi){ this.id = aidi; }
    public void setColegio(String school) { this.colegio = school; }

}
