package edu.ncsu.csc.CoffeeMaker.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc.CoffeeMaker.TestConfig;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.models.enums.IngredientType;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@RunWith ( SpringRunner.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class RecipeTest {

    @Autowired
    private RecipeService service;

    @Before
    public void setup () {
        service.deleteAll();
    }

    @Test
    @Transactional
    public void testAddRecipe () {

        final Recipe r1 = createRecipe( "Black Coffee", 1, 1, 0, 0, 0 );
        r1.setName( "Black Coffee" );
        service.save( r1 );

        final Recipe r2 = createRecipe( "Mocha", 1, 1, 1, 1, 1 );
        r2.setName( "Mocha" );

        service.save( r2 );

        final List<Recipe> recipes = service.findAll();
        Assert.assertEquals( "Creating two recipes should result in two recipes in the database", 2, recipes.size() );

        Assert.assertEquals( "The retrieved recipe should match the created one", r1, recipes.get( 0 ) );
    }

    @Test
    @Transactional
    public void testNoRecipes () {
        Assert.assertEquals( "There should be no Recipes in the CoffeeMaker", 0, service.findAll().size() );

        final Recipe r1 = createRecipe( "Tasty Drink", 12, -12, 0, 0, 0 );

        final Recipe r2 = createRecipe( "Mocha", 1, 1, 1, 1, 1 );

        final List<Recipe> recipes = List.of( r1, r2 );

        try {
            service.saveAll( recipes );
            Assert.assertEquals(
                    "Trying to save a collection of elements where one is invalid should result in neither getting saved",
                    0, service.count() );
        }
        catch ( final Exception e ) {
            Assert.assertTrue( e instanceof ConstraintViolationException );
        }

    }

    @Test
    @Transactional
    public void testAddRecipe1 () {

        Assert.assertEquals( "There should be no Recipes in the CoffeeMaker", 0, service.findAll().size() );
        final String name = "Coffee";
        final Recipe r1 = createRecipe( name, 50, 3, 1, 1, 0 );

        service.save( r1 );

        Assert.assertEquals( "There should only one recipe in the CoffeeMaker", 1, service.findAll().size() );
        Assert.assertNotNull( service.findByName( name ) );

    }

    /* Test2 is done via the API for different validation */

    @Test
    @Transactional
    public void testAddRecipe3 () {
        Assert.assertEquals( "There should be no Recipes in the CoffeeMaker", 0, service.findAll().size() );
        final String name = "Coffee";
        final Recipe r1 = createRecipe( name, -50, 3, 1, 1, 0 );

        try {
            service.save( r1 );

            Assert.assertNull( "A recipe was able to be created with a negative price", service.findByName( name ) );
        }
        catch ( final ConstraintViolationException cvee ) {
            // expected
        }

    }

    @Test
    @Transactional
    public void testAddRecipe4 () {
        Assert.assertEquals( "There should be no Recipes in the CoffeeMaker", 0, service.findAll().size() );
        final String name = "Coffee";
        final Recipe r1 = createRecipe( name, 50, -3, 1, 1, 2 );

        try {
            service.save( r1 );

            Assert.assertNull( "A recipe was able to be created with a negative amount of coffee",
                    service.findByName( name ) );
        }
        catch ( final ConstraintViolationException cvee ) {
            // expected
        }

    }

    @Test
    @Transactional
    public void testAddRecipe5 () {
        Assert.assertEquals( "There should be no Recipes in the CoffeeMaker", 0, service.findAll().size() );
        final String name = "Coffee";
        final Recipe r1 = createRecipe( name, 50, 3, -1, 1, 2 );

        try {
            service.save( r1 );

            Assert.assertNull( "A recipe was able to be created with a negative amount of milk",
                    service.findByName( name ) );
        }
        catch ( final ConstraintViolationException cvee ) {
            // expected
        }

    }

    @Test
    @Transactional
    public void testAddRecipe6 () {
        Assert.assertEquals( "There should be no Recipes in the CoffeeMaker", 0, service.findAll().size() );
        final String name = "Coffee";
        final Recipe r1 = createRecipe( name, 50, 3, 1, -1, 2 );

        try {
            service.save( r1 );

            Assert.assertNull( "A recipe was able to be created with a negative amount of sugar",
                    service.findByName( name ) );
        }
        catch ( final ConstraintViolationException cvee ) {
            // expected
        }

    }

    @Test
    @Transactional
    public void testAddRecipe7 () {
        Assert.assertEquals( "There should be no Recipes in the CoffeeMaker", 0, service.findAll().size() );
        final String name = "Coffee";
        final Recipe r1 = createRecipe( name, 50, 3, 1, -1, 0 );

        try {
            service.save( r1 );

            Assert.assertNull( "A recipe was able to be created with a negative amount of chocolate",
                    service.findByName( name ) );
        }
        catch ( final ConstraintViolationException cvee ) {
            // expected
        }

    }

    @Test
    @Transactional
    public void testAddRecipe13 () {
        Assert.assertEquals( "There should be no Recipes in the CoffeeMaker", 0, service.findAll().size() );

        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50, 3, 1, 1, 2 );
        service.save( r2 );

        Assert.assertEquals( "Creating two recipes should result in two recipes in the database", 2, service.count() );

    }

    @Test
    @Transactional
    public void testAddRecipe14 () {
        Assert.assertEquals( "There should be no Recipes in the CoffeeMaker", 0, service.findAll().size() );

        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50, 3, 1, 1, 2 );
        service.save( r2 );
        final Recipe r3 = createRecipe( "Latte", 60, 3, 2, 2, 0 );
        service.save( r3 );

        Assert.assertEquals( "Creating three recipes should result in three recipes in the database", 3,
                service.count() );

    }

    @Test
    @Transactional
    public void testDeleteRecipe1 () {
        Assert.assertEquals( "There should be no Recipes in the CoffeeMaker", 0, service.findAll().size() );

        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );
        service.save( r1 );

        Assert.assertEquals( "There should be one recipe in the database", 1, service.count() );

        service.delete( r1 );
        Assert.assertEquals( "There should be no Recipes in the CoffeeMaker", 0, service.findAll().size() );
    }

    @Test
    @Transactional
    public void testDeleteRecipe2 () {
        Assert.assertEquals( "There should be no Recipes in the CoffeeMaker", 0, service.findAll().size() );

        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50, 3, 1, 1, 2 );
        service.save( r2 );
        final Recipe r3 = createRecipe( "Latte", 60, 3, 2, 2, 0 );
        service.save( r3 );

        Assert.assertEquals( "There should be three recipes in the database", 3, service.count() );

        service.deleteAll();

        Assert.assertEquals( "`service.deleteAll()` should remove everything", 0, service.count() );

    }

    @Test
    @Transactional
    public void testEditRecipe1 () {
        Assert.assertEquals( "There should be no Recipes in the CoffeeMaker", 0, service.findAll().size() );

        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );
        service.save( r1 );

        r1.setPrice( 70 );

        service.save( r1 );

        final Recipe retrieved = service.findByName( "Coffee" );

        Assert.assertEquals( 70, (int) retrieved.getPrice() );
        final String expected = "Coffee 70 with ingredients [Ingredient [ ingredient=COFFEE, amount=3]Ingredient [ ingredient=MILK, amount=1]Ingredient [ ingredient=PUMPKIN_SPICE, amount=1]]";

        assertEquals( expected, retrieved.toString() );
        Assert.assertEquals( "Editing a recipe shouldn't duplicate it", 1, service.count() );

    }

    private Recipe createRecipe ( final String name, final Integer price, final Integer coffee, final Integer milk,
            final Integer pumpkinSpice, final Integer chocolate ) {
        final Recipe recipe = new Recipe();
        recipe.setName( name );
        recipe.setPrice( price );
        recipe.addIngredient( new Ingredient( IngredientType.COFFEE, coffee ) );
        recipe.addIngredient( new Ingredient( IngredientType.MILK, milk ) );
        recipe.addIngredient( new Ingredient( IngredientType.PUMPKIN_SPICE, pumpkinSpice ) );

        return recipe;
    }

    @Test
    @Transactional
    public void testUpdateRecipe () {

        Assert.assertEquals( "There should be no Recipes in the CoffeeMaker", 0, service.findAll().size() );

        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );
        service.save( r1 );

        Recipe retrieved = service.findByName( "Coffee" );

        Assert.assertEquals( 50, (int) retrieved.getPrice() );

        final String expected = "Coffee 50 with ingredients [Ingredient [ ingredient=COFFEE, amount=3]Ingredient [ ingredient=MILK, amount=1]Ingredient [ ingredient=PUMPKIN_SPICE, amount=1]]";

        assertEquals( expected, retrieved.toString() );

        final Recipe r2 = createRecipe( "newCoffee", 70, 5, 2, 2, 1 );
        r1.updateRecipe( r2 );
        service.save( r1 );
        retrieved = service.findByName( "newCoffee" );
        Assert.assertEquals( 70, (int) retrieved.getPrice() );
        final String expected1 = "newCoffee 70 with ingredients [Ingredient [ ingredient=COFFEE, amount=5]Ingredient [ ingredient=MILK, amount=2]Ingredient [ ingredient=PUMPKIN_SPICE, amount=2]]";

        assertEquals( expected1, retrieved.toString() );

        Assert.assertEquals( "Updating a recipe shouldn't duplicate it", 1, service.count() );

    }

    @Test
    @Transactional
    public void testToString () {

        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );
        Assert.assertEquals(
                "Coffee 50 with ingredients [Ingredient [ ingredient=COFFEE, amount=3]Ingredient [ ingredient=MILK, amount=1]Ingredient [ ingredient=PUMPKIN_SPICE, amount=1]]",
                r1.toString() );

    }

    @Test
    @Transactional
    public void testEquals () {

        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );
        Assert.assertFalse( r1.equals( service.findAll() ) );
        Assert.assertFalse( r1.equals( null ) );

        final Recipe r2 = createRecipe( null, 50, 3, 1, 1, 2 );
        Assert.assertFalse( r2.equals( r1 ) );

        r2.setName( "Mocha" );
        Assert.assertFalse( r1.equals( r2 ) );

        final Recipe r3 = createRecipe( "Coffee", 70, 4, 1, 1, 2 );
        Assert.assertTrue( r1.equals( r3 ) );

    }

    // @Test
    // @Transactional
    // public void testCheckRecipe () {
    //
    // final Recipe r1 = createRecipe( "Coffee", 50, 0, 0, 0, 0 );
    // Assert.assertTrue( r1.checkRecipe() );
    //
    // r1.setMilk( 3 );
    // Assert.assertFalse( r1.checkRecipe() );
    //
    // }

    @Test
    @Transactional
    public void testCheckRecipeByID () {

        final Recipe r1 = createRecipe( "test 1", 50, 0, 0, 0, 0 );
        service.save( r1 );

        final Recipe r2 = createRecipe( "test 2", 50, 0, 0, 0, 0 );
        service.save( r2 );
        final long temp_id_1 = service.findAll().get( 0 ).getId();
        final long temp_id_2 = service.findAll().get( 1 ).getId();
        Assert.assertTrue( service.existsById( temp_id_1 ) );
        Assert.assertTrue( service.existsById( temp_id_2 ) );
        service.delete( r1 );
        Assert.assertFalse( service.existsById( 123L ) );

    }

    @Test
    @Transactional
    public void testFindRecipeByID () {

        final Recipe r1 = createRecipe( "test 1", 50, 0, 0, 0, 0 );
        service.save( r1 );

        final Recipe r2 = createRecipe( "test 2", 50, 0, 0, 0, 0 );
        service.save( r2 );
        final long temp_id_1 = service.findAll().get( 0 ).getId();
        final long temp_id_2 = service.findAll().get( 1 ).getId();

        Assert.assertEquals( "test 1", service.findById( temp_id_1 ).getName() );
        Assert.assertEquals( "test 2", service.findById( temp_id_2 ).getName() );
        service.delete( r1 );
        Assert.assertNull( service.findById( (long) 1 ) );

    }

}
