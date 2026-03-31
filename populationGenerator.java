
import java.util.*;
public class populationGenerator {

    public static Animal createRandomAnimal(Field field, Location location) {
        Random rand = Randomizer.getRandom();
        double value = rand.nextDouble();

        if (value <= 0.02) {
            return new Fox(true, field, location);
        } 
        else if (value <= 0.10) {
            return new Rabbit(true, field, location);
        } 
        else if (value <= 0.13) {
            return new Squirrel(true, field, location);
        }
        return null;
    }
}