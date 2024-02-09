package com.masanz.ut2.ejercicio5.Dto;

import java.sql.Date;

public class Comida {
    private  int idComid;
    private String nombreComida;
    private Date fCaducidad;

    public Comida(int idComid, String nombreComida, Date fCaducidad) {
        this.idComid = idComid;
        this.nombreComida = nombreComida;
        this.fCaducidad = fCaducidad;
    }

    public int getIdComid() {
        return idComid;
    }

    public void setIdComid(int idComid) {
        this.idComid = idComid;
    }

    public String getNombreComida() {
        return nombreComida;
    }

    public void setNombreComida(String nombreComida) {
        this.nombreComida = nombreComida;
    }

    public Date getfCaducidad() {
        return fCaducidad;
    }

    public void setfCaducidad(Date fCaducidad) {
        this.fCaducidad = fCaducidad;
    }
}
