package edu.ncsu.csc.CoffeeMaker.unit;

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
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
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

        final Recipe r1 = new Recipe();
        r1.setName( "Black Coffee" );
        r1.setPrice( 1 );
        r1.setCoffee( 1 );
        r1.setMilk( 0 );
        r1.setSugar( 0 );
        r1.setChocolate( 0 );
        service.save( r1 );

        final Recipe r2 = new Recipe();
        r2.setName( "Mocha" );
        r2.setPrice( 1 );
        r2.setCoffee( 1 );
        r2.setMilk( 1 );
        r2.setSugar( 1 );
        r2.setChocolate( 1 );
        service.save( r2 );

        final List<Recipe> recipes = service.findAll();
        Assert.assertEquals( "Creating two recipes should result in two recipes in the database", 2, recipes.size() );

        Assert.assertEquals( "The retrieved recipe should match the created one", r1, recipes.get( 0 ) );
    }

    @Test
    @Transactional
    public void testNoRecipes () {
        Assert.assertEquals( "There should be no Recipes in the CoffeeMaker", 0, service.findAll().size() );

        final Recipe r1 = new Recipe();
        r1.setName( "Tasty Drink" );
        r1.setPrice( 12 );
        r1.setCoffee( -12 );
        r1.setMilk( 0 );
        r1.setSugar( 0 );
        r1.setChocolate( 0 );

        final Recipe r2 = new Recipe();
        r2.setName( "Mocha" );
        r2.setPrice( 1 );
        r2.setCoffee( 1 );
        r2.setMilk( 1 );
        r2.setSugar( 1 );
        r2.setChocolate( 1 );

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
        final Recipe r1 = createRecipe( name, 50, 3, 1, 1, -2 );

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
        Assert.assertEquals( 3, (int) retrieved.getCoffee() );
        Assert.assertEquals( 1, (int) retrieved.getMilk() );
        Assert.assertEquals( 1, (int) retrieved.getSugar() );
        Assert.assertEquals( 0, (int) retrieved.getChocolate() );

        Assert.assertEquals( "Editing a recipe shouldn't duplicate it", 1, service.count() );

    }

    private Recipe createRecipe ( final String name, final Integer price, final Integer coffee, final Integer milk,
            final Integer sugar, final Integer chocolate ) {
        final Recipe recipe = new Recipe();
        recipe.setName( name );
        recipe.setPrice( price );
        recipe.setCoffee( coffee );
        recipe.setMilk( milk );
        recipe.setSugar( sugar );
        recipe.setChocolate( chocolate );

        return recipe;
    }

}
