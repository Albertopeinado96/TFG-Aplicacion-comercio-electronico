package com.example.proyectofinal;

public class User {
    public String name, email, phone;
    public String address, country, provincia, postalcode, poblacion, nif;

    public User(){

    }

    public User(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = "";
        this.country = "";
        this.provincia = "";
        this.postalcode = "";
        this.poblacion = "";
        this.nif = "";
    }

    public User(String address, String country, String provincia, String postalcode, String poblacion, String nif) {
        this.address = address;
        this.provincia = provincia;
        this.postalcode = postalcode;
        this.poblacion = poblacion;
        this.nif = nif;
        this.country = country;
    }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public String getProvincia() { return provincia; }

    public String getPostalcode() {
        return postalcode;
    }

    public String getPoblacion() { return poblacion; }

    public String getNif() {
        return nif;
    }
}
