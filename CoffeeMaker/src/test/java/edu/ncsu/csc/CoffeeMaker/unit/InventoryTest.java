package edu.ncsu.csc.CoffeeMaker.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

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
import edu.ncsu.csc.CoffeeMaker.models.Inventory;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;

@RunWith ( SpringRunner.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class InventoryTest {

    @Autowired
    private InventoryService inventoryService;

    @Before
    public void setup () {
        final Inventory ivt = inventoryService.getInventory();

        ivt.setChocolate( 500 );
        ivt.setCoffee( 500 );
        ivt.setMilk( 500 );
        ivt.setSugar( 500 );

        inventoryService.save( ivt );
    }

    @Test
    @Transactional
    public void testInventoryObject () {
        final Inventory i = new Inventory( 10, 10, 10, 10 );
        assertEquals( 10, i.getChocolate() );
        assertEquals( 10, i.getCoffee() );
        assertEquals( 10, i.getMilk() );
        assertEquals( 10, i.getSugar() );

        // check to see if trying to set the values to negative changes the
        // actual values
        i.setChocolate( -10 );
        assertEquals( 10, i.getChocolate() );
        i.setCoffee( -10 );
        assertEquals( 10, i.getCoffee() );
        i.setMilk( -10 );
        assertEquals( 10, i.getMilk() );
        i.setSugar( -10 );
        assertEquals( 10, i.getSugar() );
    }

    @Test
    @Transactional
    public void testCheckMilk () {
        final Inventory i = new Inventory( 10, 10, 10, 10 );
        // try with random negative number
        try {
            i.checkMilk( "-10" );
            fail( "The method should throw IAE when a negative number is given." );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( 10, i.getMilk() );
        }

        // try with random input.
        try {
            i.checkMilk( "ABC" );
            fail( "The method should throw IAE when a negative number is given." );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( 10, i.getMilk() );
        }

        // try with valid value
        try {

            assertEquals( 10, i.checkMilk( "10" ) );
        }
        catch ( final IllegalArgumentException e ) {
            fail();
        }

    }

    @Test
    @Transactional
    public void testCheckChocolate () {
        final Inventory i = new Inventory( 10, 10, 10, 10 );
        // try with random negative number
        try {
            i.checkChocolate( "-10" );
            fail( "The method should throw IAE when a negative number is given." );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( 10, i.getChocolate() );
        }

        // try with random input.
        try {
            i.checkChocolate( "ABC" );
            fail( "The method should throw IAE when a negative number is given." );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( 10, i.getChocolate() );
        }

        // try with valid value
        try {

            assertEquals( 10, i.checkChocolate( "10" ) );
        }
        catch ( final IllegalArgumentException e ) {
            fail();
        }

    }

    @Test
    @Transactional
    public void testCheckCoffee () {
        final Inventory i = new Inventory( 10, 10, 10, 10 );
        // try with random negative number
        try {
            i.checkCoffee( "-10" );
            fail( "The method should throw IAE when a negative number is given." );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( 10, i.getCoffee() );
        }

        // try with random input.
        try {
            i.checkCoffee( "ABC" );
            fail( "The method should throw IAE when a negative number is given." );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( 10, i.getCoffee() );
        }

        // try with valid value
        try {

            assertEquals( 10, i.checkCoffee( "10" ) );
        }
        catch ( final IllegalArgumentException e ) {
            fail();
        }

    }

    @Test
    @Transactional
    public void testCheckSugar () {
        final Inventory i = new Inventory( 10, 10, 10, 10 );
        // try with random negative number
        try {
            i.checkSugar( "-10" );
            fail( "The method should throw IAE when a negative number is given." );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( 10, i.getSugar() );
        }

        // try with random input.
        try {
            i.checkSugar( "ABC" );
            fail( "The method should throw IAE when a negative number is given." );
        }
        catch ( final IllegalArgumentException e ) {
            assertEquals( 10, i.getSugar() );
        }

        // try with valid value
        try {

            assertEquals( 10, i.checkSugar( "10" ) );
        }
        catch ( final IllegalArgumentException e ) {
            fail();
        }

    }

    @Test
    @Transactional
    public void testConsumeInventory () {
        final Inventory i = inventoryService.getInventory();

        final Recipe recipe = new Recipe();
        recipe.setName( "Delicious Not-Coffee" );
        recipe.setChocolate( 10 );
        recipe.setMilk( 20 );
        recipe.setSugar( 5 );
        recipe.setCoffee( 1 );

        recipe.setPrice( 5 );

        // Recipe #2
        final Recipe recipe2 = new Recipe();
        recipe2.setName( "Delicious Not-Coffee2" );
        recipe2.setChocolate( 10 );
        recipe2.setMilk( 20 );
        recipe2.setSugar( 5 );
        recipe2.setCoffee( 1 );

        recipe2.setPrice( 5 );

        // Recipe #3
        final Recipe recipe3 = new Recipe();
        recipe3.setName( "Delicious Not-Coffee3" );
        recipe3.setChocolate( 10 );
        recipe3.setMilk( 20 );
        recipe3.setSugar( 5 );
        recipe3.setCoffee( 1 );

        recipe3.setPrice( 5 );

        i.useIngredients( recipe );

        i.useIngredients( recipe2 );

        i.useIngredients( recipe3 );

        /*
         * Make sure that all of the inventory fields are now properly updated
         */

        Assert.assertEquals( 470, (int) i.getChocolate() );
        Assert.assertEquals( 440, (int) i.getMilk() );
        Assert.assertEquals( 485, (int) i.getSugar() );
        Assert.assertEquals( 497, (int) i.getCoffee() );

        // check to see if we can still make the recipes if there is not enough
        // of any one of the ingredients.
        final Inventory invtLessCoffee = new Inventory( 0, 20, 5, 10 );
        assertFalse( invtLessCoffee.useIngredients( recipe3 ) );

        final Inventory invtLessMilk = new Inventory( 1, 10, 5, 10 );
        assertFalse( invtLessMilk.useIngredients( recipe3 ) );

        final Inventory invtLessSugar = new Inventory( 1, 20, 4, 10 );
        assertFalse( invtLessSugar.useIngredients( recipe3 ) );

        final Inventory invtLessChocolate = new Inventory( 1, 20, 5, 9 );
        assertFalse( invtLessChocolate.useIngredients( recipe3 ) );

    }

    @Test
    @Transactional
    public void testAddInventory1 () {
        Inventory ivt = inventoryService.getInventory();

        ivt.addIngredients( 5, 3, 7, 2 );

        /* Save and retrieve again to update with DB */
        inventoryService.save( ivt );

        ivt = inventoryService.getInventory();

        Assert.assertEquals( "Adding to the inventory should result in correctly-updated values for coffee", 505,
                (int) ivt.getCoffee() );
        Assert.assertEquals( "Adding to the inventory should result in correctly-updated values for milk", 503,
                (int) ivt.getMilk() );
        Assert.assertEquals( "Adding to the inventory should result in correctly-updated values sugar", 507,
                (int) ivt.getSugar() );
        Assert.assertEquals( "Adding to the inventory should result in correctly-updated values chocolate", 502,
                (int) ivt.getChocolate() );

    }

    @Test
    @Transactional
    public void testAddInventory2 () {
        final Inventory ivt = inventoryService.getInventory();

        try {
            ivt.addIngredients( -5, 3, 7, 2 );
        }
        catch ( final IllegalArgumentException iae ) {
            Assert.assertEquals(
                    "Trying to update the Inventory with an invalid value for coffee should result in no changes -- coffee",
                    500, (int) ivt.getCoffee() );
            Assert.assertEquals(
                    "Trying to update the Inventory with an invalid value for coffee should result in no changes -- milk",
                    500, (int) ivt.getMilk() );
            Assert.assertEquals(
                    "Trying to update the Inventory with an invalid value for coffee should result in no changes -- sugar",
                    500, (int) ivt.getSugar() );
            Assert.assertEquals(
                    "Trying to update the Inventory with an invalid value for coffee should result in no changes -- chocolate",
                    500, (int) ivt.getChocolate() );
        }
    }

    @Test
    @Transactional
    public void testAddInventory3 () {
        final Inventory ivt = inventoryService.getInventory();

        try {
            ivt.addIngredients( 5, -3, 7, 2 );
        }
        catch ( final IllegalArgumentException iae ) {
            Assert.assertEquals(
                    "Trying to update the Inventory with an invalid value for milk should result in no changes -- coffee",
                    500, (int) ivt.getCoffee() );
            Assert.assertEquals(
                    "Trying to update the Inventory with an invalid value for milk should result in no changes -- milk",
                    500, (int) ivt.getMilk() );
            Assert.assertEquals(
                    "Trying to update the Inventory with an invalid value for milk should result in no changes -- sugar",
                    500, (int) ivt.getSugar() );
            Assert.assertEquals(
                    "Trying to update the Inventory with an invalid value for milk should result in no changes -- chocolate",
                    500, (int) ivt.getChocolate() );

        }

    }

    @Test
    @Transactional
    public void testAddInventory4 () {
        final Inventory ivt = inventoryService.getInventory();

        try {
            ivt.addIngredients( 5, 3, -7, 2 );
        }
        catch ( final IllegalArgumentException iae ) {
            Assert.assertEquals(
                    "Trying to update the Inventory with an invalid value for sugar should result in no changes -- coffee",
                    500, (int) ivt.getCoffee() );
            Assert.assertEquals(
                    "Trying to update the Inventory with an invalid value for sugar should result in no changes -- milk",
                    500, (int) ivt.getMilk() );
            Assert.assertEquals(
                    "Trying to update the Inventory with an invalid value for sugar should result in no changes -- sugar",
                    500, (int) ivt.getSugar() );
            Assert.assertEquals(
                    "Trying to update the Inventory with an invalid value for sugar should result in no changes -- chocolate",
                    500, (int) ivt.getChocolate() );

        }

    }

    @Test
    @Transactional
    public void testAddInventory5 () {
        final Inventory ivt = inventoryService.getInventory();

        try {
            ivt.addIngredients( 5, 3, 7, -2 );
        }
        catch ( final IllegalArgumentException iae ) {
            Assert.assertEquals(
                    "Trying to update the Inventory with an invalid value for chocolate should result in no changes -- coffee",
                    500, (int) ivt.getCoffee() );
            Assert.assertEquals(
                    "Trying to update the Inventory with an invalid value for chocolate should result in no changes -- milk",
                    500, (int) ivt.getMilk() );
            Assert.assertEquals(
                    "Trying to update the Inventory with an invalid value for chocolate should result in no changes -- sugar",
                    500, (int) ivt.getSugar() );
            Assert.assertEquals(
                    "Trying to update the Inventory with an invalid value for chocolate should result in no changes -- chocolate",
                    500, (int) ivt.getChocolate() );

        }

    }

    @Test
    @Transactional
    public void testToString () {
        final Inventory i = new Inventory( 10, 10, 10, 10 );
        final String expected = "Coffee: 10\nMilk: 10\nSugar: 10\nChocolate: 10\n";
        assertEquals( expected, i.toString() );

    }

}
