import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            parser p = new parser(new Lexer(new FileReader(args[0])));
            Object result = p.parse().value;
            System.out.println(result);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
