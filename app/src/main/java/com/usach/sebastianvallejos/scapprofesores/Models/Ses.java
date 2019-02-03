package com.usach.sebastianvallejos.scapprofesores.Models;

/**
 * Created by sebastianvallejos on 05-03-18.
 */

public class Ses {
    private String id;
    private String profesor;
    private String descripcion;
    private String fecha;
    private String seccion;
    private long faciles;
    private long normales;
    private long dificiles;
    private long menos;
    private long entre;
    private long mas;
    private long entusiasmado;
    private long desinteresado;
    private long aburrido;
    private String materia;

    public Ses(){}

    public Ses(String aidi, String teacher , String desc, String date, String section,
               long easy, long normal, long hard, long less, long between, long more,
               long q1, long q2, long q3, String subject)
    {
        this.id = aidi;
        this.profesor = teacher;
        this.descripcion = desc;
        this.seccion = section;
        this.fecha = date;
        this.faciles = easy;
        this.normales = normal;
        this.dificiles = hard;
        this.menos = less;
        this.entre = between;
        this.mas = more;
        this.entusiasmado = q1;
        this.desinteresado = q2;
        this.aburrido = q3;
        this.materia = subject;
    }

    //Getters
    public String getId(){ return this.id; }
    public String getFecha() {return this.fecha;}
    public String getSeccion() {return this.seccion;}
    public String getProfesor(){ return this.profesor; }
    public String getDescripcion() {return this.descripcion;}
    public String getMateria() {return this.materia;}
    public long getFaciles() {return this.faciles;}
    public long getNormales() {return this.normales;}
    public long getDificiles() { return dificiles; }
    public long getMenos() { return menos; }
    public long getEntre() { return entre; }
    public long getMas() { return mas; }
    public long getEntusiasmado() { return entusiasmado; }
    public long getDesinteresado() { return desinteresado; }
    public long getAburrido() { return aburrido; }

    //Setters
    public void setId(String id) {this.id = id;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setFecha(String fecha) {this.fecha = fecha;}
    public void setProfesor(String profesor) {this.profesor = profesor;}
    public void setSeccion(String seccion) {this.seccion = seccion;}
    public void setMateria(String materia) {this.materia = materia;}
    public void setFaciles(long faciles) { this.faciles = faciles; }
    public void setNormales(long normales) { this.normales = normales; }
    public void setDificiles(long dificiles) { this.dificiles = dificiles; }
    public void setMenos(long menos) { this.menos = menos; }
    public void setEntre(long entre) { this.entre = entre; }
    public void setMas(long mas) { this.mas = mas; }
    public void setEntusiasmado(long q1) { this.entusiasmado = q1; }
    public void setDesinteresado(long q2) { this.desinteresado = q2; }
    public void setAburrido(long q3) { this.aburrido = q3; }
}
