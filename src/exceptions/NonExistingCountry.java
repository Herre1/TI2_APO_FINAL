package exceptions;

public class NonExistingCountry extends Exception{
    public NonExistingCountry(String name) {
        super("THERE IS NO COUNTRY FOR"+name);
    }
}
