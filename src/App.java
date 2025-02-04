import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        File file = new File("file.txt");
        Scanner reader = new Scanner(file);
        parkingSpots garage = new parkingSpots();
        Gate gate1 = new Gate(1, garage);
        Gate gate2 = new Gate(2, garage);
        Gate gate3 = new Gate(3, garage);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            line = line.replaceAll("[^0-9,]", "");
            String[] lines = line.split(",");

            Thread car = new Thread(
                    new Car(Integer.parseInt(lines[1]), Integer.parseInt(lines[2]),
                            returnGate(Integer.parseInt(lines[0]), gate1, gate2, gate3),
                            Integer.parseInt(lines[3]), garage));
            car.start();
        }
        reader.close();
    }

    public static Gate returnGate(int num, Gate g1, Gate g2, Gate g3) {
        if (num == 1)
            return g1;
        else if (num == 2)
            return g2;
        else
            return g3;
    }
}
