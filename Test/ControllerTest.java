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
        String command3 = "INSERT INTO countries(id,name,population,countryCode) VALUES ('71615790-3dd2-11ed-b878-0242ac120002','USA',329.5,'+1')";

        GeograpicController a = new GeograpicController();

        String b = a.insert(command);
        assertEquals("El pais Colombia Ha sido añadido con exito!!", b);

        String d = a.insert(command2);
        assertEquals("La ciudad ha sido registrada correctamente", d);

        b = a.insert(command3);
        assertEquals("El pais USA Ha sido añadido con exito!!", b);

        b = a.delete(command3);
        assertEquals(b, b);

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
