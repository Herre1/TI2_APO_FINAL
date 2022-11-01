package model;

import exceptions.NonExistingCity;
import exceptions.NonExistingCountry;
import exceptions.NonExistentCommand;
import exceptions.DuplicateId;
import java.util.ArrayList;
import java.util.HashMap;

public class GeograpicController {

    ArrayList<Country> countries;
    HashMap<Integer, Country> dataBase;

    public GeograpicController() {
        countries = new ArrayList<>();
        dataBase = new HashMap<>();
    }

    public String insert(String command) throws NonExistingCountry, NonExistentCommand, NonExistingCity, DuplicateId {
        String ans = "";
        if (command.contains("INSERT INTO countries(id,name,population,countryCode) VALUES")) {
            String[] splitCommand = command.split("VALUES");
            String values = splitCommand[1].replace("(", "");
            values = values.replace(")", "");

            String[] splitValues = values.split(",");
            if (splitValues[0].contains("'") && splitValues[1].contains("'") && splitValues[3].contains("'")) {
                String id = splitValues[0].replace("'", "");
                id = id.replace(" ", "");

                for (int i=0 ; i < countries.size() ; i++) {
                if (countries.get(i).getId().equals(id)) {
                return "OPS THE COUNTRY CANNOT BE WITH THE SAME ID";
                }
                }

                String name = splitValues[1].replace("'", "");
                name = name.replace(" ", "");
                double population = Double.parseDouble(splitValues[2]);
                String countryCode = splitValues[3].replace("'", "");
                countryCode = countryCode.replace(" ", "");

                Country country = new Country(id, name, population, countryCode);
                countries.add(country);
                ans = "El pais " + name + " Ha sido añadido con exito!!";
                
            } else {
                throw new NonExistentCommand();
            }
        } else if (command.contains("INSERT INTO cities(id,name,countryID,population) VALUES")) {
            if (!countries.isEmpty()) {
                String[] splitCommand = command.split("VALUES");
                String values = splitCommand[1].replace("(", "");
                values = values.replace(")", "");

                String[] splitValues = values.split(",");
                if (splitValues[0].contains("'") && splitValues[1].contains("'") && splitValues[2].contains("'")) {
                    String id = splitValues[0].replace("'", "");
                    id = id.replace(" ", "");
                    String name = splitValues[1].replace("'", "");
                    name = name.replace(" ", "");
                    String countryID = splitValues[2].replace("'", "");
                    countryID = countryID.replace(" ", "");
                    double population = Double.parseDouble(splitValues[3]);

                    for (int i = 0; i < countries.size(); i++) {
                        if (countries.get(i).getId().equals(countryID)) {
                            countries.get(i).addCity(new City(id, name, countryID, population));
                            ans = "La ciudad ha sido registrada correctamente";
                            
                        } else {
                            throw new NonExistingCountry(countryID);
                        }
                    }
                } else {
                    throw new NonExistentCommand();
                }
            } else {
                throw new NonExistingCity();
            }
        } else {
            throw new NonExistentCommand();
        }
        return ans;
    }

    public String searchUndFilter(String command) throws NonExistentCommand, NonExistingCity {
        String ans = "";
        if (command.contains("WHERE")) {
            String[] splitCommand = command.split(" ");
            if (splitCommand[3].equals("countries")) {
                switch (splitCommand[5]) {
                    case "name":
                        String name = splitCommand[7];
                        if (name.contains("'")) {
                            name = name.replace("'", "");
                        } else {
                            throw new NonExistentCommand();
                        }
                        for (int i = 0; i < countries.size(); i++) {
                            if (countries.get(i).getName().equals(name)) {
                                ans = "Country " + name + " is registered";
                                break;
                            }
                        }
                        break;
                    case "population":
                        double population = Double.parseDouble(splitCommand[7]);
                        switch (splitCommand[6]) {
                            case ">":
                                for (int i = 0; i < countries.size(); i++) {
                                    if (countries.get(i).getPopulation() > population) {
                                        ans = countries.get(i).getName();
                                    }
                                }
                                break;
                            case "<":
                                for (int i = 0; i < countries.size(); i++) {
                                    if (countries.get(i).getPopulation() < population) {
                                        ans = countries.get(i).getName();
                                    }
                                }
                                break;
                            case "=":
                                for (int i = 0; i < countries.size(); i++) {
                                    if (countries.get(i).getPopulation() == population) {
                                        ans = countries.get(i).getName();
                                    }
                                }
                                break;
                            default:
                                throw new NonExistentCommand();
                        }
                        break;
                }
            } else if (splitCommand[3].equals("cities")) {
                switch (splitCommand[5]) {
                    case "name":
                        String name = splitCommand[7];
                        if (name.contains("'")) {
                            name = name.replace("'", "");
                        } else {
                            throw new NonExistentCommand();
                        }
                        for (int i = 0; i < countries.get(i).getCities().size(); i++) {
                            if (countries.get(i).getCities().get(i) == null) {
                                throw new NonExistingCity();
                            } else {
                                if (countries.get(i).getCities().get(i).getName().equals(name)) {
                                    ans ="City " + name + " is registered";
                                    break;
                                }
                            }
                        }
                        break;
                    case "population":
                        double population = Double.parseDouble(splitCommand[7]);

                        switch (splitCommand[6]) {
                            case ">":
                                for (int i = 0; i < countries.size(); i++) {
                                    for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                        if (countries.get(i).getCities().get(j) == null) {
                                            throw new NonExistingCity();
                                        } else {
                                            if (countries.get(i).getCities().get(j).getPopulation() > population) {
                                                ans = countries.get(i).getCities().get(j).getName();
                                            }
                                        }
                                    }
                                }
                                break;
                            case "<":
                                for (int i = 0; i < countries.size(); i++) {
                                    for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                        if (countries.get(i).getCities().get(j) == null) {
                                            throw new NonExistingCity();
                                        } else {
                                            if (countries.get(i).getCities().get(j).getPopulation() < population) {
                                                ans = countries.get(i).getCities().get(j).getName();
                                            }
                                        }
                                    }
                                }
                                break;
                            case "=":
                                for (int i = 0; i < countries.size(); i++) {
                                    for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                        if (countries.get(i).getCities().get(j) == null) {
                                            throw new NonExistingCity();
                                        } else {
                                            if (countries.get(i).getCities().get(j).getPopulation() == population) {
                                                ans = countries.get(i).getCities().get(j).getName();
                                            }
                                        }
                                    }
                                }
                                break;
                            default:
                                throw new NonExistentCommand();
                        }
                        break;
                }
            } else {
                throw new NonExistentCommand();
            }
        } else {
            if (command.contains("countries")) {
                for (int i = 0; i < countries.size(); i++) {
                    ans = countries.get(i).getName();
                }
            } else if (command.contains("cities")) {
                for (int i = 0; i < countries.size(); i++) {
                    for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                        ans = countries.get(i).getCities().get(j).getName();
                    }
                }
            }
        }
        return ans;
    }

    public String orderBy(String command) throws NonExistentCommand, NonExistingCity {
        String ans = "";
        if (command.contains("WHERE")) {
            String[] splitCommand = command.split(" ");
            if (splitCommand[3].equals("countries")) {
                switch (splitCommand[5]) {
                    case "name":
                        String name = splitCommand[7];
                        if (name.contains("'")) {
                            name = name.replace("'", "");
                        } else {
                            throw new NonExistentCommand();
                        }
                        for (int i = 0; i < countries.size(); i++) {
                            if (countries.get(i).getName().equals(name)) {
                                ans = "Country " + name + " is registered";
                                break;
                            }
                        }

                        break;
                    case "population":
                        double population = Double.parseDouble(splitCommand[7]);
                        switch (splitCommand[6]) {
                            case ">":
                                for (int i = 0; i < countries.size(); i++) {
                                    if (countries.get(i).getPopulation() > population) {
                                        ans = countries.get(i).getName();
                                    }
                                }
                                break;
                            case "<":
                                for (int i = 0; i < countries.size(); i++) {
                                    if (countries.get(i).getPopulation() < population) {
                                        ans = countries.get(i).getName();
                                    }
                                }
                                break;
                            case "=":
                                for (int i = 0; i < countries.size(); i++) {
                                    if (countries.get(i).getPopulation() == population) {
                                        ans = countries.get(i).getName();
                                    }
                                }
                                break;
                            default:
                                throw new NonExistentCommand();
                        }

                        // Collections.sort(countries, (o1, o2) -> {
                        // return o1.getName().compareTo(o2.getName())
                        // });

                        break;
                }
            } else if (splitCommand[3].equals("cities")) {
                switch (splitCommand[5]) {
                    case "name":
                        String name = splitCommand[7];
                        if (name.contains("'")) {
                            name = name.replace("'", "");
                        } else {
                            throw new NonExistentCommand();
                        }
                        for (int i = 0; i < countries.get(i).getCities().size(); i++) {
                            if (countries.get(i).getCities().get(i) == null) {
                                throw new NonExistingCity();
                            } else {
                                if (countries.get(i).getCities().get(i).getName().equals(name)) {
                                    ans = "City " + name + " is registered";
                                    break;
                                }
                            }
                        }
                        break;
                    case "population":
                        double population = Double.parseDouble(splitCommand[7]);
                        switch (splitCommand[6]) {
                            case ">":
                                for (int i = 0; i < countries.size(); i++) {
                                    for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                        if (countries.get(i).getCities().get(j) == null) {
                                            throw new NonExistingCity();
                                        } else {
                                            if (countries.get(i).getCities().get(j).getPopulation() > population) {
                                                ans = countries.get(i).getCities().get(j).getName();
                                            }
                                        }
                                    }
                                }
                                break;
                            case "<":
                                for (int i = 0; i < countries.size(); i++) {
                                    for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                        if (countries.get(i).getCities().get(j) == null) {
                                            throw new NonExistingCity();
                                        } else {
                                            if (countries.get(i).getCities().get(j).getPopulation() < population) {
                                                ans = countries.get(i).getCities().get(j).getName();
                                            }
                                        }
                                    }
                                }
                                break;
                            case "=":
                                for (int i = 0; i < countries.size(); i++) {
                                    for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                        if (countries.get(i).getCities().get(j) == null) {
                                            throw new NonExistingCity();
                                        } else {
                                            if (countries.get(i).getCities().get(j).getPopulation() == population) {
                                                ans = countries.get(i).getCities().get(j).getName();
                                            }
                                        }
                                    }
                                }
                                break;
                            default:
                                throw new NonExistentCommand();
                        }

                        // Collections.sort(countries, (o1, o2) -> {
                        // return
                        // })

                        break;
                }
            } else {
                throw new NonExistentCommand();
            }
        } else {
            if (command.contains("countries")) {
                for (int i = 0; i < countries.size(); i++) {
                    ans = countries.get(i).getName();
                }
            } else if (command.contains("cities")) {
                for (int i = 0; i < countries.size(); i++) {
                    for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                        ans = countries.get(i).getCities().get(j).getName();
                    }
                }
            }
        }
        return ans;
    }

    public String delete(String command) throws NonExistentCommand, NonExistingCity {
        String ans = "";
        if (command.contains("WHERE")) {
            String[] splitCommand = command.split(" ");

            if (splitCommand[2].equals("countries")) {
                switch (splitCommand[4]) {
                    case "name":
                        String name = splitCommand[6];
                        if (name.contains("'")) {
                            name = name.replace("'", "");
                        } else {
                            throw new NonExistentCommand();
                        }
                        for (int i = 0; i < countries.size(); i++) {
                            if (countries.get(i).getName().equals(name)) {
                                countries.remove(i);
                                ans = "The country " + i + " Was removed";
                                break;
                            }
                        }
                        break;
                    case "population":
                        double population = Double.parseDouble(splitCommand[6]);
                        switch (splitCommand[5]) {
                            case ">":
                                for (int i = 0; i < countries.size(); i++) {
                                    if (countries.get(i).getPopulation() > population) {
                                        countries.remove(i);
                                        ans = "The country " + i + " Was removed";
                                    }
                                }
                                break;
                            case "<":
                                for (int i = 0; i < countries.size(); i++) {
                                    if (countries.get(i).getPopulation() < population) {
                                        countries.remove(i);
                                        ans = "The country " + i + " Was removed";
                                    }
                                }
                                break;
                            case "=":
                                for (int i = 0; i < countries.size(); i++) {
                                    if (countries.get(i).getPopulation() == population) {
                                        countries.remove(i);
                                        ans = "The country " + i + " Was removed";
                                    }
                                }
                                break;
                            default:
                                throw new NonExistentCommand();
                        }
                        break;
                }
            } else if (splitCommand[2].equals("cities")) {
                switch (splitCommand[4]) {
                    case "name":
                        String name = splitCommand[6];
                        if (name.contains("'")) {
                            name = name.replace("'", "");
                        } else {
                            throw new NonExistentCommand();
                        }
                        for (int i = 0; i < countries.get(i).getCities().size(); i++) {
                            if (countries.get(i).getCities().get(i) == null) {
                                throw new NonExistingCity();
                            } else {
                                if (countries.get(i).getCities().get(i).getName().equals(name)) {
                                    countries.get(i).getCities().remove(i);
                                    ans = "The city " + i + " Was removed";
                                    break;
                                }
                            }
                        }
                        break;
                    case "population":
                        double population = Double.parseDouble(splitCommand[6]);
                        switch (splitCommand[5]) {
                            case ">":
                                for (int i = 0; i < countries.size(); i++) {
                                    for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                        if (countries.get(i).getCities().get(j) == null) {
                                            throw new NonExistingCity();
                                        } else {
                                            if (countries.get(i).getCities().get(j).getPopulation() > population) {
                                                countries.get(i).getCities().remove(j);
                                                ans = "The city " + j + " Was removed";
                                            }
                                        }
                                    }
                                }
                                break;
                            case "<":
                                for (int i = 0; i < countries.size(); i++) {
                                    for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                        if (countries.get(i).getCities().get(j) == null) {
                                            throw new NonExistingCity();
                                        } else {
                                            if (countries.get(i).getCities().get(j).getPopulation() < population) {
                                                countries.get(i).getCities().remove(j);
                                                ans = "The city " + j + " Was removed";
                                            }
                                        }
                                    }
                                }
                                break;
                            case "=":
                                for (int i = 0; i < countries.size(); i++) {
                                    for (int j = 0; j < countries.get(i).getCities().size(); j++) {
                                        if (countries.get(i).getCities().get(j) == null) {
                                            throw new NonExistingCity();
                                        } else {
                                            if (countries.get(i).getCities().get(j).getPopulation() == population) {
                                                countries.get(i).getCities().remove(j);
                                                ans = "The city " + j + " Was removed";
                                            }
                                        }
                                    }
                                }
                                break;
                            default:
                                throw new NonExistentCommand();
                        }
                        break;
                }
            } else {
                throw new NonExistentCommand();
            }
        }
        return ans;
    }

    public void addToProgram(ArrayList<Country> arr) {
        for (int i = 0; i < arr.size(); i++) {
            dataBase.put(i, arr.get(i));
            countries.add(arr.get(i));
        }
    }

    public ArrayList<Country> getDataBaseAsArrayList() {
        ArrayList<Country> arr = new ArrayList<>();
        for (int i = 0; i < dataBase.size(); i++) {
            arr.add(i, dataBase.get(i));
        }
        return arr;
    }
}
