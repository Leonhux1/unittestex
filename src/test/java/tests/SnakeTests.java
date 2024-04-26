package tests;


import animals.AnimalType;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.Skin;
import animals.petstore.pet.types.Snake;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SnakeTests {

    private static Snake actualSnake;

    @BeforeAll
    public static void createAnimals() {
        actualSnake = new Snake(AnimalType.WILD, Skin.SCALES, Gender.FEMALE, Breed.BALL_PYTHON);
    }

    @Test
    @Order(1)
    @DisplayName("Snake Speak hiss Test")
    public void SnakeGoeshissTest()
    {
        createAnimals();
        assertEquals("The Snake goes hiss! hiss!", actualSnake.speak(), "I was expecting hiss! hiss!");
    }


    @Test
    @Order(1)
    @DisplayName("Snake Speak default Test 2")
    public void SnakeGoesSpeakTest()
    {
        actualSnake = new Snake(AnimalType.UNKNOWN, Skin.SCALES, Gender.FEMALE, Breed.BURMESE);
        System.out.println(actualSnake.speak());
        assertEquals("The Snake goes Psss! Psss!", actualSnake.speak(), "I was expecting Psss! Psss!");
    }

    @Test
    @Order(1)
    @DisplayName("Snake Scale is it Hyperallergetic")
    public void SnakeHyperAllergeticTests()
    {
        assertEquals("The Snake is hyperallergetic!", actualSnake.SnakeHypoallergenic(),
                "The Snake is hyperallergetic!");
    }

    @Test
    @Order(1)
    @DisplayName("Snake has legs Test")
    public void legTests()
    {
        Assertions.assertNotNull(actualSnake.getNumberOfLegs());
    }

    @Test
    @Order(2)
    @DisplayName("Snake Gender Test Male")
    public void genderTestMale()
    {
        actualSnake = new Snake(AnimalType.DOMESTIC, Skin.SCALES,Gender.FEMALE, Breed.BALL_PYTHON);
        assertEquals(Gender.FEMALE, actualSnake.getGender(), "Expecting Male Gender!");
    }

    @Test
    @Order(2)
    @DisplayName("Snake Breed Test CopperHead")
    public void genderSnakeBreed() {
        actualSnake = new Snake(AnimalType.DOMESTIC, Skin.SCALES,Gender.MALE, Breed.COPPERHEAD);
        assertEquals(Breed.COPPERHEAD, actualSnake.getBreed(), "Expecting Breed CopperHead!");
    }

    @Test
    @Order(2)
    @DisplayName("Snake Speak Psss Test")
    public void SnakeGoesPsssTest()
    {
        actualSnake = new Snake(AnimalType.DOMESTIC, Skin.SCALES,Gender.FEMALE, Breed.BALL_PYTHON);
        assertEquals("The Snake goes psss! psss!", actualSnake.speak(), "I was expecting psss");
    }

    @Test
    @Order(2)
    @DisplayName("Snake Speak hiss Tests 1")
    public void SnakeGoesHissTest()
    {
        createAnimals();
        assertEquals("The Snake goes hiss! hiss!", actualSnake.speak(), "I was expecting Hiss");
    }

    @Test
    @Order(2)
    @DisplayName("Number of Legs Test 1")
    public void setNumberOfLegsTest () {
        createAnimals();
        int expected = 0;
        actualSnake.setNumberOfLegs(expected);
        assertEquals(expected, actualSnake.getNumberOfLegs());

    }

    @Test
    @Order(3)
    @DisplayName("Test Snake Skin Texture")
    public void testSnakeSkinTexture() {
        actualSnake = new Snake(AnimalType.UNKNOWN, Skin.SCALES, Gender.FEMALE, Breed.BURMESE);
        assertEquals(Skin.SCALES, actualSnake.getTextureOfSkin(), "Expecting Scales Skin Texture!");
    }

    @Test
    @Order(3)
    @DisplayName("Test Snake Cost")
    public void testSnakeCost() {
        actualSnake = new Snake(AnimalType.DOMESTIC, Skin.SCALES, Gender.FEMALE, Breed.BALL_PYTHON);
        double expectedCost = 50.0;
        assertEquals(expectedCost, actualSnake.getSnakeCost(), 0.001);
    }

    @Test
    @Order(2)
    @DisplayName("Returns Expected String Test 1")
    public void ToStringTest()
    {

        createAnimals();
        String expected = "The type of pet is SNAKE!\n" +
                "The SNAKE gender is " + actualSnake.getGender() + "!\n" +
                "The SNAKE cost is $" + actualSnake.getCost() + "!\n" +
                "The Snake is " + actualSnake.getAnimalType() + "!\n" +
                "The Snakes breed is " + actualSnake.getBreed() + "!\n" +
                "The Snake is hyperallergetic!!\n" +
                actualSnake.speak() + "\n" +
                "Snakes have " + actualSnake.getNumberOfLegs() + " legs!";

        assertEquals(expected, actualSnake.toString());
    }

}
