package com.usach.sebastianvallejos.scapprofesores.Models;

public class Answers {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String respuesta_tiempo;
    private String respuesta_dificultad;
    private String respuesta_motivacion;
    private String detalle;
    private String idApoderado;

    public Answers(){}

    public Answers(String name, String father_last_name, String mother_last_name, String time, String difficulty, String q3, String detail, String id){
        this.nombre = name;
        this.apellidoMaterno = mother_last_name;
        this.apellidoPaterno = father_last_name;
        this.respuesta_tiempo = time;
        this.respuesta_dificultad = difficulty;
        this.respuesta_motivacion = q3;
        this.detalle = detail;
        this.idApoderado = id;
    }

    //Getters
    public String getNombre() { return this.nombre; }
    public String getApellidoPaterno() { return this.apellidoPaterno; }
    public String getApellidoMaterno() { return this.apellidoMaterno; }
    public String getDetalle() { return this.detalle; }
    public String getTiempo() { return this.respuesta_tiempo; }
    public String getDificultad() { return this.respuesta_dificultad; }
    public String getPreguntaMotivacion() { return this.respuesta_motivacion; }
    public String getIdApoderado() { return idApoderado; }

    //Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
    public void setDificultad(String dificultad) { this.respuesta_dificultad = dificultad; }
    public void setPregunta3(String motivacion) { this.respuesta_motivacion = motivacion; }
    public void setTiempo(String tiempo) { this.respuesta_tiempo = tiempo; }
    public void setIdApoderado(String id) { this.idApoderado=id; }
}
