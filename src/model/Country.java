package model;

import java.util.ArrayList;

public class Country {

    private String id;
    private String name;
    private double population;
    private String countryCode;

    private ArrayList<City> cities;

    public Country(String id, String name, double population, String countryCode) {
        cities = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.population = population;
        this.countryCode = countryCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPopulation() {
        return population;
    }

    public void setPopulation(double population) {
        this.population = population;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public ArrayList<City> getCities(){
        return cities;
    }

    public void addCity(City city){
        cities.add(city);
    }
}
