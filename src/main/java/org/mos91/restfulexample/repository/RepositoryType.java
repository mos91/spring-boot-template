package org.mos91.restfulexample.repository;

/**
 * Created by Oleg.Meleshin on 10/20/2016.
 */
public enum RepositoryType {

    H2("H2"), POSTGRES("Postgres");

    RepositoryType(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "RepositoryType{name='" + name + "'}";
    }
}
