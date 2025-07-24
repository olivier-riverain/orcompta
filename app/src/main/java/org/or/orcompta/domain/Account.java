package org.or.orcompta.domain;

public class Account {
    private final String name;
    private final String description;
    
    public Account(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String toString() {
        return this.name + " " + this.description;
    }
}
