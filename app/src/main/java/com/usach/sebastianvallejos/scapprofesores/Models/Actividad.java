package com.usach.sebastianvallejos.scapprofesores.Models;

/**
 * Created by sebastianvallejos on 15-02-18.
 */

public class Actividad {

    //Atributos de la clase
    private String seccion;
    private String materia;
    private String profesor;
    private String tipo;
    private String descripcion;
    private String fecha;

    //Constructor requerido por FireBase
    public Actividad(){}

    //Constructor con asignacion de atributos incluidos
    public Actividad(String section, String subject, String proffesor, String type, String description, String date)
    {
        this.seccion = section;
        this.materia = subject;
        this.profesor = proffesor;
        this.tipo = type;
        this.descripcion = description;
        this.fecha = date;

    }

    //Getters para obtener los datos de la clase
    public String getSeccion() { return this.seccion; }
    public String getMateria() { return this.materia; }
    public String getProfesor() { return this.profesor; }
    public String getTipo() { return this.tipo; }
    public String getDescripcion() { return this.descripcion; }
    public String getFecha(){ return this.fecha; }

    //Setters para guardar datos de las actividades
    public void setDescripcion(String desc){ this.descripcion = desc; }
    public void setMateria(String mat){ this.materia = mat; }
    public void setProfesor(String prof){ this.profesor = prof; }
    public void setTipo(String type){ this.tipo = type; }
    public void setFecha(String date){ this.fecha = date; }
    public void setSeccion(String section){ this.seccion = section; }

}
