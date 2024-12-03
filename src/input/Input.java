package input;

import java.net.URISyntaxException;
import java.nio.file.*;
import java.io.IOException;
import java.util.Objects;

public class Input {

    public static final String INPUT_DAY_1;
    public static final String INPUT_DAY_2;
    public static final String INPUT_DAY_3;

    static {
        //DAY 1:
        try {
            Path path = Paths.get(Objects.requireNonNull(Input.class.getClassLoader().getResource("input/data/input_day_1.txt")).toURI());
            INPUT_DAY_1 = new String(Files.readAllBytes(path));
        } catch (IOException | URISyntaxException e) {
            throw new ExceptionInInitializerError("Failed to load input_day_1.txt");
        }
        //DAY 2:
        try {
            Path path = Paths.get(Objects.requireNonNull(Input.class.getClassLoader().getResource("input/data/input_day_2.txt")).toURI());
            INPUT_DAY_2 = new String(Files.readAllBytes(path));
        } catch (IOException | URISyntaxException e) {
            throw new ExceptionInInitializerError("Failed to load input_day_2.txt");
        }
        //DAY 3:
        try {
            Path path = Paths.get(Objects.requireNonNull(Input.class.getClassLoader().getResource("input/data/input_day_3.txt")).toURI());
            INPUT_DAY_3 = new String(Files.readAllBytes(path));
        } catch (IOException | URISyntaxException e) {
            throw new ExceptionInInitializerError("Failed to load input_day_3.txt");
        }
    }

    public static void main(String[] args) {
        System.out.println(INPUT_DAY_1);
        System.out.println(INPUT_DAY_2);
    }
}
