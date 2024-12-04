package input;

import java.net.URISyntaxException;
import java.nio.file.*;
import java.io.IOException;
import java.util.Objects;

public class Input {

    public static final String INPUT_DAY_1;
    public static final String INPUT_DAY_2;
    public static final String INPUT_DAY_3;
    public static final String INPUT_DAY_4;

    private static final int dayCount = 4;

    static {
        //ALL DAYS:
        String[] allInputs = new String[dayCount];
        for (int i = 1; i <= dayCount; i++) {
            try {
                //All input files should be named "input_day_X" where X is the corresponding day of december (1-25).
                //They should be stored in src/input/data/.
                Path path = Paths.get(Objects.requireNonNull(Input.class.getClassLoader().getResource("input/data/input_day_" + i + ".txt")).toURI());
                String loadedInput = new String(Files.readAllBytes(path));
                allInputs[i - 1] = loadedInput;
            } catch (IOException | URISyntaxException e) {
                throw new ExceptionInInitializerError("Failed to load input_day_" + i + ".txt");
            }
        }
        INPUT_DAY_1 = allInputs[0];
        INPUT_DAY_2 = allInputs[1];
        INPUT_DAY_3 = allInputs[2];
        INPUT_DAY_4 = allInputs[3];
    }

    public static void main(String[] args) {
        System.out.println("DAY 1:\n" + INPUT_DAY_1);
        System.out.println("DAY 2:\n" + INPUT_DAY_2);
        System.out.println("DAY 3:\n" + INPUT_DAY_3);
        System.out.println("DAY 4:\n" + INPUT_DAY_4);
    }
}
