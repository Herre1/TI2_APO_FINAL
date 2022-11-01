package exceptions;

public class NonExistingCity extends Exception {
    public NonExistingCity() {
        super("There is no cities :/");
    }
}
