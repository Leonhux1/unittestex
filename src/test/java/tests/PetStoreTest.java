package tests;

import animals.AnimalType;
import animals.petstore.pet.Pet;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.Skin;
import animals.petstore.pet.types.Cat;
import animals.petstore.pet.types.Dog;
import animals.petstore.pet.types.Snake;
import animals.petstore.store.DuplicatePetStoreRecordException;
import animals.petstore.store.PetNotFoundSaleException;
import animals.petstore.store.PetStore;
import number.Numbers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class PetStoreTest {
    private static PetStore petStore;

    @BeforeEach
    public void loadThePetStoreInventory() {
        petStore = new PetStore();
        petStore.init();
    }

    @Test
    @DisplayName("Inventory Count Test")
    public void validateInventory() {
        assertEquals(7, petStore.getPetsForSale().size(), "Inventory counts are off!");
    }

    @Test
    @DisplayName("Print Inventory Test")
    public void printInventoryTest() {
        petStore.printInventory();
    }

    @Test
    @DisplayName("Sale of Poodle Remove Item Test")
    public void poodleSoldTest() throws DuplicatePetStoreRecordException, PetNotFoundSaleException {
        int inventorySize = petStore.getPetsForSale().size() - 1;
        Dog poodle = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                new BigDecimal("650.00"), 1);

        // Validation
        petStore.soldPetItem(poodle);
        assertEquals(inventorySize, petStore.getPetsForSale().size(), "Expected inventory does not match actual");
    }

    @Test
    @DisplayName("Poodle Duplicate Record Exception Test")
    public void poodleDupRecordExceptionTest() {
        petStore.addPetInventoryItem(new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                new BigDecimal("650.00"), 1));
        Dog poodle = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                new BigDecimal("650.00"), 1);

        // Validation
        String expectedMessage = "Duplicate Dog record store id [1]";
        Exception exception = assertThrows(DuplicatePetStoreRecordException.class, () -> {
            petStore.soldPetItem(poodle);
        });
        assertEquals(expectedMessage, exception.getMessage(), "DuplicateRecordExceptionTest was NOT encountered!");

    }

    @Test
    @DisplayName("Sale of Sphynx Remove Item Test")
    public void sphynxSoldTest() throws DuplicatePetStoreRecordException, PetNotFoundSaleException {
        int inventorySize = petStore.getPetsForSale().size() - 1;

        Cat sphynx = new Cat(AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.FEMALE, Breed.SPHYNX,
                new BigDecimal("100.00"), 2);
        Cat removedItem = (Cat) petStore.soldPetItem(sphynx);

        // Validation
        assertEquals(inventorySize, petStore.getPetsForSale().size(), "Expected inventory does not match actual");
        assertEquals(sphynx.getPetStoreId(), removedItem.getPetStoreId(), "The cat items are identical");
    }

    /**
     * Limitations to test factory as it does not instantiate before all
     *
     * @return list of {@link DynamicNode} that contains the test results
     * @throws DuplicatePetStoreRecordException if duplicate pet record is found
     * @throws PetNotFoundSaleException         if pet is not found
     */
    @TestFactory
    @DisplayName("Sale of Sphynx Remove Item Test2")
    public Stream<DynamicNode> sphynxSoldTest2() throws DuplicatePetStoreRecordException, PetNotFoundSaleException {
        int inventorySize = petStore.getPetsForSale().size() - 1;

        Cat sphynx = new Cat(AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.FEMALE, Breed.SPHYNX,
                new BigDecimal("100.00"), 2);
        Cat removedItem = (Cat) petStore.soldPetItem(sphynx);

        // Validation
        List<DynamicNode> nodes = new ArrayList<>();
        List<DynamicTest> dynamicTests = Arrays.asList(
                dynamicTest("Inventory Check Size Test ", () -> assertEquals(inventorySize,
                        petStore.getPetsForSale().size())),
                dynamicTest("The cat objects match ", () -> assertEquals(sphynx.toString(),
                        removedItem.toString()))
        );
        nodes.add(dynamicContainer("Cat Item 2 Test", dynamicTests));//dynamicNode("", dynamicContainers);

        return nodes.stream();
    }


    @Test
    @DisplayName("Sale of Snake Remove Item Test")


    public void snakeSoldTest() throws DuplicatePetStoreRecordException, PetNotFoundSaleException {
        int inventorySize = petStore.getPetsForSale().size() - 1;
        Snake snake = new Snake(AnimalType.WILD, Skin.SCALES, Gender.MALE, Breed.COPPERHEAD,
                new BigDecimal("120.00"), 1);
        Snake removeItem = (Snake) petStore.soldPetItem(snake);

        assertEquals(inventorySize, petStore.getPetsForSale().size() - 1, "Expected does not match actual");
        assertEquals(snake.getPetStoreId(), removeItem.getPetStoreId(), "the snake item are identical");
    }

    /**
     * Example of parameterized test
     *
     * @param number to be tested
     */
    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, -10, 128, Integer.MIN_VALUE})
    // six numbers
    void isNumberEven(int number) {
        assertTrue(Numbers.isEven(number));
    }

    @Test
    @DisplayName("Pet Not Found in The Pet Store Test - Dog")
    public void dogNotFoundInPetStoreTest() {
        Dog dogNotInPetStore = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.CORAL,
                new BigDecimal("800.00"), 0);
        PetNotFoundSaleException exception = assertThrows(PetNotFoundSaleException.class, () -> {
            petStore.soldPetItem(dogNotInPetStore);
        });
        assertEquals("The Pet is not part of the pet store!!", exception.getMessage(),
                "Exception message does not match");
    }

    @Test
    @DisplayName("Pet Not Found in The Pet Store Test - Cat")
    public void catNotFoundInPetStoreTest() {
        Cat catNotInPetStore = new Cat(AnimalType.DOMESTIC, Skin.HAIR, Gender.FEMALE, Breed.RAGDOLL,
                new BigDecimal("150.00"), 0);
        PetNotFoundSaleException exception = assertThrows(PetNotFoundSaleException.class, () -> {
            petStore.soldPetItem(catNotInPetStore);
        });
        assertEquals("The Pet is not part of the pet store!!", exception.getMessage(),
                "Exception message does not match");
    }

    @Test
    @DisplayName("Pet Not Found in The Pet Store Test - Snake")
    public void snakeNotFoundInPetStoreTest() {
        Snake snakeNotInPetStore = new Snake(AnimalType.WILD, Skin.SCALES, Gender.FEMALE, Breed.CORAL,
                new BigDecimal("200.00"), 0);
        PetNotFoundSaleException exception = assertThrows(PetNotFoundSaleException.class, () -> {
            petStore.soldPetItem(snakeNotInPetStore);
        });
        assertEquals("The Pet is not part of the pet store!!", exception.getMessage(),
                "Exception message does not match");
    }

    @Test
    @DisplayName("Pet Not Found in The Pet Store Test - Snake")
    public void snakeNotFoundInPetStoreTest2() {
        Snake snake = new Snake(AnimalType.DOMESTIC, Skin.SCALES, Gender.MALE, Breed.BURMESE);
        Snake SoldSnake = null;
        assertNull(SoldSnake);
    }

    @Test
    @DisplayName("Sold Pet Item - Duplicate Cat Record Test")
    public void soldDuplicateCatRecordTest() {
        Cat duplicateCat = new Cat(AnimalType.DOMESTIC, Skin.HAIR, Gender.MALE, Breed.BURMESE,
                new BigDecimal("65.00"), 1);
        petStore.addPetInventoryItem(duplicateCat);
        assertThrows(DuplicatePetStoreRecordException.class, () -> {
            try {
                petStore.soldPetItem(duplicateCat);
            } catch (PetNotFoundSaleException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    @DisplayName("Sold Pet Item - Cat Not Found")
    public void soldPetItem_CatNotFound() throws DuplicatePetStoreRecordException, PetNotFoundSaleException {
        Cat catNotInPetStore = new Cat(AnimalType.DOMESTIC, Skin.HAIR, Gender.FEMALE, Breed.RAGDOLL,
                new BigDecimal("150.00"), 0);
        Exception exception = assertThrows(PetNotFoundSaleException.class, () -> {
            petStore.soldPetItem(catNotInPetStore);
        });
        assertEquals("The Pet is not part of the pet store!!", exception.getMessage(),
                "Exception message should indicate that the pet is not found");
    }

    @Test
    @DisplayName("Remove Non-Existing Pet Item Test")
    public void removeNonExistingPetItemTest() {
        int initialInventorySize = petStore.getPetsForSale().size();
        Cat cat = new Cat(AnimalType.DOMESTIC, Skin.HAIR, Gender.FEMALE, Breed.RAGDOLL,
                new BigDecimal("150.00"), 0);

        assertThrows(PetNotFoundSaleException.class, () -> {
            petStore.soldPetItem(cat);
        });

        assertEquals(initialInventorySize, petStore.getPetsForSale().size(), "Inventory size should not change");
    }

    @Test
    @DisplayName("Sale of Cat with Duplicate Record Exception Test")
    public void catSoldDuplicateRecordTest() throws DuplicatePetStoreRecordException {
        Cat duplicateCat = new Cat(AnimalType.DOMESTIC, Skin.HAIR, Gender.MALE, Breed.BURMESE,
                new BigDecimal("65.00"), 1);
        petStore.addPetInventoryItem(duplicateCat);

        Exception exception = assertThrows(DuplicatePetStoreRecordException.class, () -> {
            petStore.soldPetItem(duplicateCat);
        });

        assertEquals("Duplicate Cat record store id [1]", exception.getMessage(), "Duplicate record exception message does not match");
    }

    @Test
    @DisplayName("Sale of Snake with Duplicate Record Exception Test")
    public void snakeSoldDuplicateRecordTest() throws DuplicatePetStoreRecordException {
        Snake duplicateSnake = new Snake(AnimalType.WILD, Skin.SCALES, Gender.MALE, Breed.BALL_PYTHON,
                new BigDecimal("65.00"), 1);
        petStore.addPetInventoryItem(duplicateSnake);

        Exception exception = assertThrows(DuplicatePetStoreRecordException.class, () -> {
            petStore.soldPetItem(duplicateSnake);
        });

        assertEquals("Duplicate Snake record store id [1]", exception.getMessage(), "Duplicate record exception message does not match");
    }

    @Test
    @DisplayName("cat Sold Empty Record Test")
    public void testIdentifySoldCatFromInventoryWhenEmpty() {
        petStore = new PetStore();
        Cat cat = new Cat(AnimalType.DOMESTIC, Skin.HAIR, Gender.MALE, Breed.BURMESE,
                new BigDecimal("65.00"), 1);
        //ACT
        Pet SoldPet = null;
        try {
            SoldPet = petStore.soldPetItem(cat);

        } catch (DuplicatePetStoreRecordException | PetNotFoundSaleException e) {
            throw new RuntimeException(e);
        }
        assertNull(SoldPet);
    }
    @Test
    @DisplayName("Dog Sold Empty Record Test")
    public void testIdentifySoldDogFromInventoryWhenEmpty() {
        petStore = new PetStore();
        Dog dog = new Dog(AnimalType.DOMESTIC, Skin.HAIR, Gender.MALE, Breed.POODLE,
                new BigDecimal("65.00"), 1);
        //ACT
        Pet SoldPet = null;
        try {
            SoldPet = petStore.soldPetItem(dog);

        } catch (DuplicatePetStoreRecordException | PetNotFoundSaleException e) {
            throw new RuntimeException(e);
        }
        assertNull(SoldPet);
    }
    @Test
    @DisplayName("Snake Sold Empty Record Test")
    public void testIdentifySoldSnakeFromInventoryWhenEmpty() {
        petStore = new PetStore();
        Snake snake = new Snake(AnimalType.WILD, Skin.SCALES, Gender.MALE, Breed.BALL_PYTHON,
                new BigDecimal("65.00"), 1);
        //ACT
        Pet SoldPet = null;
        try {
            SoldPet = petStore.soldPetItem(snake);

        } catch (DuplicatePetStoreRecordException | PetNotFoundSaleException e) {
            throw new RuntimeException(e);
        }
        assertNull(SoldPet);
    }

}