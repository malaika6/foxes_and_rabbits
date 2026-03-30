//package foxes_rabbits_v2;

import java.awt.Color;
import java.util.*;

public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    private int age;
    protected static final Random rand = new Random();
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        this.age=0;
        setLocation(location);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
protected int getAge() {
    return age;
}

protected void setAge(int age) {
    this.age = age;
}
protected abstract int getBreedingAge(); //@malaika: every sub class of animal must implement this as breeding age varies per animals 

protected boolean canBreed() {
    return getAge() >= getBreedingAge();
}

// should ALSO be implemented in every class because it varies
protected abstract int getMaxAge();

//@malaika: moved incrementAge() from Fox and Rabbit to here, since it's shared code.
protected void incrementAge() {
    setAge(getAge() + 1);
    if(getAge() > getMaxAge()) {
        setDead();
    }
}
 protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }
    protected abstract double getBreedingProbability();
    protected abstract int getMaxLitterSize();
    protected void giveBirth(List<Animal> newAnimals) // i changed the name because we will deal with the static type here
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField(); 
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed(); // i alr moved it 
        for(int b = 0; b < births && !free.isEmpty(); b++) {
            Location loc = free.remove(0);
            Animal young = createYoung( field, loc);
            newAnimals.add(young);
        }
    }
    protected abstract Animal createYoung(Field f,Location loc); // every subclass will have a version of this 
    public abstract Color getColor();
    ///to reduce coupling in animal
    public static Animal createRandomAnimal(Field field, Location location) {
    //Random rand = new Random();
    //for a shared rand
     Random rand = Randomizer.getRandom(); 
    double value = rand.nextDouble();

    if(value <= 0.02) {
        return new Fox(true, field, location);
    }
    else if(value <= 0.10 && value > 0.02) {
        return new Rabbit(true, field, location);
    }
    else if(value <= 0.13 && value > 0.10   ) { 
        return new Squirrel(true, field, location);
    }
    return null;
}
}
