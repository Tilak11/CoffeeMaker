package edu.ncsu.csc.CoffeeMaker.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

import edu.ncsu.csc.CoffeeMaker.models.enums.IngredientType;

@Entity
public class Ingredient extends DomainObject {

    @Id
    @GeneratedValue
    private Long           id;

    @Enumerated ( EnumType.STRING )
    private IngredientType ingredient;

    @Min ( 0 )
    private Integer        amount;

    public Ingredient ( IngredientType ingredient, int amount ) {
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

    public IngredientType getIngredient () {
        return ingredient;
    }

    public void setIngredient ( IngredientType ingredient ) {
        this.ingredient = ingredient;
    }

    public int getAmount () {
        return amount;
    }

    public void setAmount ( int amount ) {
        this.amount = amount;
    }

    public void setId ( Long id ) {
        this.id = id;
    }

    @Override
    public Serializable getId () {
        // TODO Auto-generated method stub
        return null;
    }

}
