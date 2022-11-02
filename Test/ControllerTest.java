package Test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import exceptions.NonExistingCity;
import exceptions.NonExistingCountry;
import exceptions.DuplicateId;
import exceptions.NonExistentCommand;
import model.*;

public class ControllerTest {

    GeograpicController controller = new GeograpicController();

    @Before
    public void setUp() throws Exception {
        ArrayList<Country> countries;
    }

    @Test
    public void testInsert() throws NonExistingCountry, NonExistentCommand, NonExistingCity, DuplicateId {
        String command = "INSERT INTO countries(id,name,population,countryCode) VALUES ('6ec3e8ec-3dd0-11ed-b878-0242ac120002','Colombia',50.2,'+57')";
        String command2 = "INSERT INTO cities(id,name,countryID,population) VALUES ('e4aa04f6-3dd0-11ed-b878-0242ac120002','Cali','6ec3e8ec-3dd0-11ed-b878-0242ac120002',2.2)";
        String command3 = "INSERT INTO countries(id,name,population,countryCode) VALUES ('71615790-3dd2-11ed-b878-0242ac120002','USA',329.5,'+1')";

        GeograpicController a = new GeograpicController();

        String b = a.insert(command);
        assertEquals("El pais Colombia Ha sido añadido con exito!!", b);

        String d = a.insert(command2);
        assertEquals("La ciudad ha sido registrada correctamente", d);

        b = a.insert(command3);
        assertEquals("El pais USA Ha sido añadido con exito!!", b);

        b = a.insert(command);
        assertEquals("OPS THE COUNTRY CANNOT BE WITH THE SAME ID", b);
    }

    @Test
    public void testDelete() throws NonExistentCommand, NonExistingCity, NonExistingCountry, DuplicateId {

        String command = "INSERT INTO countries(id,name,population,countryCode) VALUES ('6ec3e8ec-3dd0-11ed-b878-0242ac120002','Colombia',50.2,'+57')";
        String command2 = "INSERT INTO cities(id,name,countryID,population) VALUES ('e4aa04f6-3dd0-11ed-b878-0242ac120002','Cali','6ec3e8ec-3dd0-11ed-b878-0242ac120002',2.2)";
        String command3 = "INSERT INTO cities(id,name,countryID,population) VALUES ('g3dad2h-2ds5-22ko-djou23i01d2','Bogota','6ec3e8ec-3dd0-11ed-b878-0242ac120002',4.6)";

        GeograpicController a = new GeograpicController();

        String command4 = "DELETE FROM cities WHERE country = 'Colombia'";
        String command5 = "DELETE FROM cities WHERE population > 50";
        String command6 = "DELETE FROM cities WHERE population < 50";

        a.insert(command);
        a.insert(command2);
        a.insert(command3);

        String k = a.delete(command4);
        assertEquals("All the cities from the country Colombia Where erased", k);

        k = a.delete(command5);
        assertEquals("There is no cities with a population with more than 50.0", k);

        k = a.delete(command6);
        assertEquals("The city Bogota Was removed", k);

    }

    @Test
    public void testSelectFrom() throws NonExistentCommand, NonExistingCountry, NonExistingCity, DuplicateId {

        GeograpicController a = new GeograpicController();

        a.insert(
                "INSERT INTO countries(id,name,population,countryCode) VALUES ('6ec3e8ec-3dd0-11ed-b878-0242ac120002', 'Colombia', 50.2, '+57')");
        a.orderBy("SELECT * FROM countries WHERE name = 'Colombia'");

    }

    @Test
    public void testOrderBy() throws NonExistentCommand {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(new Country("'6ec3e8ec-3dd0-11ed-b878-0242ac120002'", "'Colombia'", 50.2, "'+57'"));
        String command = "SELECT * FROM countries WHERE population > 30.5";
        String found = "";
        String[] splitCommand = command.split(" ");
        double population = Double.parseDouble(splitCommand[7]);
        if (splitCommand[6].equals(">")) {
            for (int i = 0; i < countries.size(); i++) {
                if (countries.get(i).getPopulation() > population) {
                    found = countries.get(i).getName();
                }
            }
        }
        assertEquals("'Colombia'", found);
    }
}
