import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

@SuppressWarnings("serial")
public class BadConfigFormatException extends RuntimeException {

    private String filename = "configErrorFile.log";

    public BadConfigFormatException(String message) {
        super(message);
        try {
            File logFile = new File(filename);
            PrintWriter writer = new PrintWriter(logFile);
            writer.println(message);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    public String toString() {
        return "Board configuration error";
    }
}
