package edu.ncsu.csc.CoffeeMaker.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.repositories.IngredientRepository;

@Component
@Transactional
public class IngredientService extends Service {

    @Autowired
    private IngredientRepository repo;

    @Override
    protected JpaRepository getRepository () {
        // TODO Auto-generated method stub
        return repo;
    }

    /**
     * Find a ingredient with the provided name
     *
     * @param name
     *            Name of the recipe to find
     * @return found ingredient, null if none
     */
    public Ingredient findByIngredient ( final String name ) {
        return repo.findByIngredient( name );
    }

}
