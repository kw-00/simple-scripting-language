import java.io.*;

public class Main {
    public static class State {
        public static int lastErrorCode = 0;
    }

    public static void main(String[] args) {
        try {
            parser p = new parser(new Lexer(new FileReader(args[0])));
            p.parse();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
