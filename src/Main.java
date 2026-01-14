import java.io.*;
import java.util.*;

public class Main {
    public static class State {
        public static int lastErrorCode = 0;
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            // Command prompt
            var line = scanner.nextLine();
            // Block (BEGIN/END)
            if (line.equals("BEGIN")) {
                var prompt = new StringBuilder();
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    if (line.equals("END")) {
                        try (var reader = new StringReader(prompt.toString())) {
                            parser p = new parser(new Lexer(reader));
                            p.parse();
                        } catch (Exception e) {
                            System.err.println("Syntax error: " + e.getMessage());
                        } finally {
                            break;
                        }
                    } else {
                        prompt.append(line).append("\n");
                    }
                }
            } else {
                // Line-by-line interactive processing
                try (var reader = new StringReader(line)) {
                    parser p = new parser(new Lexer(reader));
                    p.parse();
                } catch (Exception e) {
                    System.err.println("Syntax error: " + e.getMessage());
                }
            }

        }
    }
}
