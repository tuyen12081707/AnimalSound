package com.example.animalsound.model;

import java.util.Objects;

public class Animal {
    private final String idPhoto;
    private final String name;
    private final String idSound;

    public Animal(String idSound, String idPhoto, String name) {
        this.idPhoto = idPhoto;
        this.name = name;
        this.idSound = idSound;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public String getName() {
        return name;
    }

    public String getIdSound() {
        return idSound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(idPhoto, animal.idPhoto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPhoto);
    }
}
