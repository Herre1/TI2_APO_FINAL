package exceptions;

public class NonExistentCommand extends Exception{
    public NonExistentCommand(){
        super("OPS THAT IS NOT A COMMAND!!!");
    }
}
