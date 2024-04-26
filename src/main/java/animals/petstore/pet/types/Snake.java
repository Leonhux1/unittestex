package animals.petstore.pet.types;

import animals.AnimalType;
import animals.petstore.pet.Pet;
import animals.petstore.pet.*;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.PetType;
import animals.petstore.pet.attributes.Skin;
import animals.petstore.pet.types.PetImpl;

import java.math.BigDecimal;

public class Snake extends Pet implements PetImpl {

    /* Properties */
    private int numberOfLegs;
    private Breed breed;

    /**
     * Constructor
     * @param animalType {@link AnimalType} that defines if it is a  domestic Snake or a wild Snake
     * @param skinType The {@link Skin} of the Snake
     * @param gender The {@link Gender} of the Snake
     * @param breed The type of Snake {@link Breed}
     */
    public Snake(AnimalType animalType, Skin skinType, Gender gender, Breed breed)
    {
        this(animalType, skinType, gender, breed, new BigDecimal(0));
    }

    /**
     * Constructor
     * @param animalType {@link AnimalType} that defines if it is a  domestic Snake or a wild Snake
     * @param skinType The {@link Skin} of the Snake
     * @param gender The {@link Gender} of the Snake
     * @param breed The type of Snake {@link Breed}
     * @param cost The cost of the Snake
     */
    public Snake(AnimalType animalType, Skin skinType, Gender gender, Breed breed, BigDecimal cost)
    {
        this(animalType, skinType, gender, breed, cost, 0);
    }

    /**
     * Constructor
     * @param animalType {@link AnimalType} that defines if it is a  domestic Snake or a wild Snake
     * @param skinType The {@link Skin} of the Snake
     * @param gender The {@link Gender} of the Snake
     * @param breed The type of Snake {@link Breed}
     * @param cost The cost of the Snake
     * @param petStoreId The pet store id
     */
    public Snake(AnimalType animalType, Skin skinType, Gender gender, Breed breed, BigDecimal cost, int petStoreId)
    {
        super(PetType.SNAKE, cost, gender, petStoreId);
        super.skinType = skinType;
        super.animalType = animalType;
        this.numberOfLegs = 4;
        this.breed = breed;
    }

    /**
     * Is the Snake allergy friendly determined by skin type
     * @return A message that tells if the Snake is hypoallergenic
     */
    public String SnakeHypoallergenic() {
        return super.petHypoallergenic(this.skinType).replaceAll("pet", "Snake");
    }

    /**
     * Depending if the Snake is domestic, wild, or neither what can the say
     * @return what Snakes would speak
     */
    public String speak() {
        String language;
        switch (this.animalType) {
            case DOMESTIC:
                language = "The Snake goes psss! psss!";
                break;
            case WILD:
                language = "The Snake goes hiss! hiss!";
                break;
            default:
                language = "The Snake goes " + super.getPetType().speak + "! " + super.getPetType().speak + "!";
                break;
        }
        return language;
    }

    private String numberOfLegs() {
        return "Snakes have " + numberOfLegs + " legs!";
    }

    public int getNumberOfLegs() {
        return numberOfLegs;
    }

    private String textureOfSkin() {
        return this.skinType.toString();
    }

    public String getTextureOfSkin() {
        return textureOfSkin();
    }

    public void setNumberOfLegs(int numberOfLegs) {
        this.numberOfLegs = numberOfLegs;
    }

    public short getSnakeCost() {
        return this.getCost().toBigInteger().shortValueExact();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Breed getBreed() {
        return this.breed;
    }

    public String typeOfPet() {
        return "The type of pet is " + petType + "!";
    }

    public AnimalType getAnimalType() {
        return super.animalType;
    }

    @Override
    public String toString() {
        return super.toString() +
                "The Snake is " + this.animalType + "!\n" +
                "The Snake breed is " + this.getBreed() + "!\n" +
                this.SnakeHypoallergenic() + "!\n" +
                this.speak() + "\n" +
                this.numberOfLegs();
    }
}
