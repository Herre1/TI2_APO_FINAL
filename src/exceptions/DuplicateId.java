package exceptions;

public class DuplicateId extends Exception {
    public DuplicateId() {
        super("ERROR YOU CAN NOT REGISTER THE SAME ID!!!");
    }
}
