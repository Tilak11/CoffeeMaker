package edu.ncsu.csc.CoffeeMaker.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        ivt.addIngredient( new Ingredient( "COFFEE", 500 ) );

        ivt.addIngredient( new Ingredient( "MILK", 500 ) );
        ivt.addIngredient( new Ingredient( "PUMPKIN_SPICE", 500 ) );

        inventoryService.save( ivt );
        assertEquals( 3, ivt.getInventoryList().size() );

    }

    @Test
    @Transactional
    public void testInventoryObject () {
        final Inventory i = new Inventory();
        assertEquals( 0, i.getInventoryList().size() );

    }

    @Test
    @Transactional
    public void testConsumeInventory () {
        final Inventory i = inventoryService.getInventory();

        final Recipe recipe = new Recipe();
        recipe.setName( "Delicious Not-Coffee" );
        recipe.addIngredient( new Ingredient( "COFFEE", 10 ) );

        recipe.addIngredient( new Ingredient( "MILK", 20 ) );
        recipe.addIngredient( new Ingredient( "PUMPKIN_SPICE", 5 ) );

        recipe.setPrice( 5 );
        // for ( int j = 0; j < i.getIngredientsList().size(); j++ ) {
        // System.out.println( i.getIngredientsList().get( j ).)
        // }

        assertEquals(
                "[Ingredient [ ingredient=COFFEE, amount=500]Ingredient [ ingredient=MILK, amount=500]Ingredient [ ingredient=PUMPKIN_SPICE, amount=500]]",
                i.toString() );
        i.useIngredients( recipe );
        assertEquals(
                "[Ingredient [ ingredient=COFFEE, amount=490]Ingredient [ ingredient=MILK, amount=480]Ingredient [ ingredient=PUMPKIN_SPICE, amount=495]]",
                i.toString() );

        // Recipe #2
        final Recipe recipe2 = new Recipe();
        recipe2.setName( "Delicious Not-Coffee2" );
        recipe2.addIngredient( new Ingredient( "COFFEE", 10 ) );

        recipe2.addIngredient( new Ingredient( "MILK", 20 ) );
        recipe2.addIngredient( new Ingredient( "PUMPKIN_SPICE", 5 ) );

        recipe2.setPrice( 15 );
        i.useIngredients( recipe2 );

        // Recipe #3
        final Recipe recipe3 = new Recipe();
        recipe3.setName( "Delicious Not-Coffee3" );
        recipe3.addIngredient( new Ingredient( "COFFEE", 10 ) );

        recipe3.addIngredient( new Ingredient( "MILK", 20 ) );
        recipe3.addIngredient( new Ingredient( "PUMPKIN_SPICE", 5 ) );

        recipe3.setPrice( 50 );
        i.useIngredients( recipe3 );

        assertEquals(
                "[Ingredient [ ingredient=COFFEE, amount=470]Ingredient [ ingredient=MILK, amount=440]Ingredient [ ingredient=PUMPKIN_SPICE, amount=485]]",
                i.toString() );

    }

    @Test
    @Transactional
    public void testAddInventory1 () {
        final Inventory ivt = inventoryService.getInventory();

        ivt.addIngredient( new Ingredient( "COFFEE", 10 ) );

        ivt.addIngredient( new Ingredient( "MILK", 20 ) );
        ivt.addIngredient( new Ingredient( "PUMPKIN_SPICE", 5 ) );
        assertEquals( 510, ivt.getInventoryList().get( 0 ).getAmount() );
        assertEquals( 520, ivt.getInventoryList().get( 1 ).getAmount() );
        assertEquals( 505, ivt.getInventoryList().get( 2 ).getAmount() );

    }

    @Test
    @Transactional
    public void testToString () {
        final Inventory i = inventoryService.getInventory();
        final String expected = "[Ingredient [ ingredient=COFFEE, amount=500]Ingredient [ ingredient=MILK, amount=500]Ingredient [ ingredient=PUMPKIN_SPICE, amount=500]]";
        assertEquals( expected, i.toString() );

    }

}
