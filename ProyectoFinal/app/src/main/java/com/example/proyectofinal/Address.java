package com.example.proyectofinal;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Address  implements Serializable {
    private String nombre, apellidos, direccion, direccion2, CP, ciudad, country, phone;

    public Address() {

    }

    public Address(String nombre, String apellidos, String direccion, String direccion2, String CP, String ciudad, String country, String phone) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.direccion2 = direccion2;
        this.CP = CP;
        this.ciudad = ciudad;
        this.country = country;
        this.phone = phone;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public String getCP() {
        return CP;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }
}
