package com.example.proyectofinal;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Product  implements Serializable {
    private int id;
    private float precio, valoracion;
    private String nombre, descripcion, marca, categoria, shortDesc, caracteristicas, image;
    private boolean disponibilidad;


    public Product() {
    }

    public Product(int id, String nombre, float precio, String descripcion, String shortDesc, String caracteristicas, String marca, float valoracion, String image, boolean disponibilidad, String categoria ) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.shortDesc = shortDesc;
        this.marca = marca;
        this.valoracion = valoracion;
        this.image = image;
        this.disponibilidad = disponibilidad;
        this.categoria = categoria;
        this.caracteristicas = caracteristicas;
    }

    public int getId() {
        return id;
    }

    public float getPrecio() {
        return precio;
    }

    public float getValoracion() {
        return valoracion;
    }

    public String getImage() {
        return image;
    }

    public String getNombre() {
        return nombre;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public String getCaracteristicas() { return caracteristicas;
    }
}
