// malaika :
//Moved age field from Fox and Rabbit to Animal.
//Initialized it in constructor.
//Replaced all direct access with getAge() and setAge(). 
import java.awt.Color;
import java.util.*;  
/**
 * A simple model of a squirrel.
 * Squirrels age, move, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Squirrel extends Animal
{
    // Characteristics shared by all squirrels (class variables).
    
    // The age at which a squirrel can start to breed.
    private static final int BREEDING_AGE = 9;
    // The age to which a squirrel can live.
    private static final int MAX_AGE = 100;
    // The likelihood of a squirrel breeding.
    private static final double BREEDING_PROBABILITY = 0.50;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 20;
    // The food value for a squirrel (used for hunger logic).
    private static final int Squirrel_FOOD_VALUE = 6;
    // A shared random number generator to control breeding.
    ///////// i dont get this 
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    // Malaika: moved age from Fox/Rabbit into Animal
    // The squirrel's food level, which decreases over time.
    private int foodLevel;

    /**
     * Create a squirrel. A squirrel can be created as a newborn (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the squirrel will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Squirrel(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            foodLevel = rand.nextInt(Squirrel_FOOD_VALUE);
        }
        else {
            setAge(0);
            foodLevel = Squirrel_FOOD_VALUE;
        }
    }
    
    /**
     * This is what the squirrel does most of the time: it moves around.
     * In the process, it might breed, die of hunger, or die of old age.
     * @param newSq A list to receive newly born squirrels.
     */
    public void act(List<Animal> newSq)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newSq);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Make this squirrel more hungry. This could result in the squirrel's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * Look for food adjacent to the current location.
     * Only the first suitable food source is used.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    foodLevel = Squirrel_FOOD_VALUE;
                    // Remove the consumed animal from the field.
                    return where;
                }
            }
        }
        return null;
    }

    /// overrides from Animal class
    protected int getMaxAge() {
        return MAX_AGE;
    }
        
    /**
     * A squirrel can breed if it has reached the breeding age.
     */
    //@malaika
    protected int getBreedingAge() {
        return BREEDING_AGE;
    }

    protected double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    protected int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    protected Animal createYoung(Field field, Location locc){
        return new Squirrel(false, field, locc);
    }
    //for clr
    public Color getColor() {
    return Color.PINK;
}
}