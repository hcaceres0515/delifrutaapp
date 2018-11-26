package com.delifruta.harold.delifruta.models;

/**
 * Created by harold on 1/23/18.
 */

public class Customer {

    private int id;
    private String name;
    private String dni;
    private String phone;

    public Customer() {
    }

    public Customer(int id, String name, String dni, String phone) {
        this.id = id;
        this.name = name;
        this.dni = dni;
        this.phone = phone;
    }
}
