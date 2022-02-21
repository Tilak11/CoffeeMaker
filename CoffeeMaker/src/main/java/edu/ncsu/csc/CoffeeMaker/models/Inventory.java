package edu.ncsu.csc.CoffeeMaker.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Inventory for the coffee maker. Inventory is tied to the database using
 * Hibernate libraries. See InventoryRepository and InventoryService for the
 * other two pieces used for database support.
 *
 * @author Kai Presler-Marshall
 */
@Entity
public class Inventory extends DomainObject {

    /** id for inventory entry */
    @Id
    @GeneratedValue
    private Long                   id;

    @OneToMany ( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private List<Ingredient> inventoryList;

    /**
     * Empty constructor for Hibernate
     */
    public Inventory () {
        // Intentionally empty so that Hibernate can instantiate
        // Inventory object.
        inventoryList = new ArrayList<Ingredient>();

    }

    /**
     * Use this to create inventory with specified amts.
     *
     * @param coffee
     *            amt of coffee
     * @param milk
     *            amt of milk
     * @param sugar
     *            amt of sugar
     * @param chocolate
     *            amt of chocolate
     */
    // public Inventory ( final Integer coffee, final Integer milk, final
    // Integer sugar, final Integer chocolate ) {
    // setCoffee( coffee );
    // setMilk( milk );
    // setSugar( sugar );
    // setChocolate( chocolate );
    // }

    /**
     * Returns the ID of the entry in the DB
     *
     * @return long
     */
    @Override
    public Long getId () {
        return id;
    }

    /**
     * Set the ID of the Inventory (Used by Hibernate)
     *
     * @param id
     *            the ID
     */
    public void setId ( final Long id ) {
        this.id = id;
    }

    // /**
    // * Returns true if there are enough ingredients to make the beverage.
    // *
    // * @param r
    // * recipe to check if there are enough ingredients
    // * @return true if enough ingredients to make the beverage
    // */
    public boolean enoughIngredients ( final Recipe r ) {
        boolean isEnough = true;
        final List<Ingredient> ingredientList = r.getIngredientsList();
        for ( int k = 0; k < ingredientList.size(); k++ ) {
            for ( int i = 0; i < inventoryList.size(); i++ ) {
                if ( inventoryList.get( i ).getIngredient().equals( ingredientList.get( k ).getIngredient() ) ) {
                    if ( inventoryList.get( i ).getAmount() < ingredientList.get( k ).getAmount() ) {
                        isEnough = false;
                        return isEnough;
                    }
                }
            }
        }

        return isEnough;
    }

    /**
     * Removes the ingredients used to make the specified recipe. Assumes that
     * the user has checked that there are enough ingredients to make
     *
     * @param r
     *            recipe to make
     * @return true if recipe is made.
     */
    public boolean useIngredients ( final Recipe r ) {

        if ( enoughIngredients( r ) ) {
            final List<Ingredient> ingredientList = r.getIngredientsList();
            for ( int k = 0; k < ingredientList.size(); k++ ) {
                for ( int i = 0; i < inventoryList.size(); i++ ) {
                    if ( inventoryList.get( i ).getIngredient().equals( ingredientList.get( k ).getIngredient() ) ) {
                        inventoryList.get( i )
                                .setAmount( inventoryList.get( i ).getAmount() - ingredientList.get( k ).getAmount() );
                    }
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Adds ingredients to the inventory
     *
     * @param coffee
     *            amt of coffee
     * @param milk
     *            amt of milk
     * @param sugar
     *            amt of sugar
     * @param chocolate
     *            amt of chocolate
     * @return true if successful, false if not
     */
    public boolean addIngredient ( final Ingredient ingredient ) {
        try {
            boolean x = false;
            for ( int i = 0; i < inventoryList.size(); i++ ) {
                if ( inventoryList.get( i ).getIngredient().equals( ingredient.getIngredient() ) ) {
                    inventoryList.get( i ).setAmount( ingredient.getAmount() + inventoryList.get( i ).getAmount() );
                    x = true;
                    break;
                }
            }
            if ( !x ) {
                inventoryList.add( ingredient );
            }
            // ingredientsList.add( ingredient );
            return true;
        }
        catch ( final Exception e ) {
            return false;
        }
    }

    /**
     * Getter for ingredient list
     *
     * @return list of ingredients in the inventory.
     */
    public List<Ingredient> getInventoryList () {
        return inventoryList;
    }
    
    
    public void setInventoryList (List<Ingredient> list) {
        this.inventoryList = list;
    }


    /**
     * Returns a string describing the current contents of the inventory.
     *
     * @return String
     */
    @Override
    public String toString () {
        final StringBuilder s = new StringBuilder();
        s.append( "[" );
        for ( int i = 0; i < this.inventoryList.size(); i++ ) {
            s.append( this.inventoryList.get( i ).toString() );
        }
        s.append( "]" );

        return s.toString();
    }

}
