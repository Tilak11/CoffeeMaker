package edu.ncsu.csc.CoffeeMaker.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@Entity
public class Ingredient extends DomainObject {

    @Id
    @GeneratedValue
    private Long    id;

    // @Enumerated ( EnumType.STRING )
    // private IngredientType ingredient;
    private String  ingredient;

    @Min ( 0 )
    private Integer amount;

    public Ingredient ( final String ingredient, final int amount ) {
        super();
        this.ingredient = ingredient;
        this.amount = amount;
    }

    @Override
    public String toString () {
        return "Ingredient [ ingredient=" + ingredient + ", amount=" + amount + "]";
    }

    public Ingredient () {

    }

    public String getIngredient () {
        return ingredient;
    }

    public void setIngredient ( final String ingredient ) {
        this.ingredient = ingredient;
    }

    public int getAmount () {
        return amount;
    }

    public void setAmount ( final int amount ) {
        this.amount = amount;
    }

    public void setId ( final Long id ) {
        this.id = id;
    }

    @Override
    public Serializable getId () {
        // TODO Auto-generated method stub
        return id;
    }

}
