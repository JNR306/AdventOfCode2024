package input;

import java.net.URISyntaxException;
import java.nio.file.*;
import java.io.IOException;
import java.util.Objects;

public class Input {

    public static final String INPUT_DAY_1;

    static {
        //DAY 1:
        try {
            Path path = Paths.get(Objects.requireNonNull(Input.class.getClassLoader().getResource("input/data/input_day_1.txt")).toURI());
            INPUT_DAY_1 = new String(Files.readAllBytes(path));
        } catch (IOException | URISyntaxException e) {
            throw new ExceptionInInitializerError("Failed to load input_day_1.txt");
        }
    }

    public static void main(String[] args) {
        System.out.println(INPUT_DAY_1);
    }
}
