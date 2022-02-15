package edu.ncsu.csc.CoffeeMaker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;

@SuppressWarnings ( { "unchecked", "rawtypes" } )
@RestController
public class APIIngredientController extends APIController {

    /**
     * IngredientService object, to be autowired in by Spring to allow for
     * manipulating the Ingredient model
     */
    @Autowired
    private IngredientService ingredientService;

    /**
     * REST API method to provide GET access to all ingredients in the system
     *
     * @return JSON representation of all ingredients
     */
    @GetMapping ( BASE_PATH + "/ingredients" )
    public List<Ingredient> getIngredients () {
        return ingredientService.findAll();
    }

    /**
     * REST API method to provide GET access to a specific ingredient, as
     * indicated by the path variable provided (the name of the ingredient
     * desired)
     *
     * @param name
     *            ingredient name
     * @return response to the request
     */
    @GetMapping ( BASE_PATH + "/ingredients/{name}" )
    public ResponseEntity getIngredient ( @PathVariable final String name ) {

        final Ingredient ingr = ingredientService.findByIngredient( name );

        if ( null == ingr ) {
            return new ResponseEntity( errorResponse( "No ingredient found with name " + name ), HttpStatus.NOT_FOUND );
        }

        return new ResponseEntity( ingr, HttpStatus.OK );
    }

    /**
     * REST API method to provide POST access to the Ingredient model. This is
     * used to create a new Ingredient by automatically converting the JSON
     * RequestBody provided to a Ingredient object. Invalid JSON will fail.
     *
     * @param ingredient
     *            The valid Ingredient to be saved.
     * @return ResponseEntity indicating success if the Ingredient could be
     *         saved to the inventory, or an error if it could not be
     */
    @PostMapping ( BASE_PATH + "/ingredients" )
    public ResponseEntity createIngredient ( @RequestBody final Ingredient ingredient ) {

        final Ingredient db = ingredientService.findByIngredient( ingredient.getIngredient().name() );

        if ( null != db ) {
            return new ResponseEntity(
                    errorResponse(
                            "Ingredient with the name " + ingredient.getIngredient().name() + " already exists" ),
                    HttpStatus.CONFLICT );
        }

        try {
            ingredientService.save( ingredient );
            return new ResponseEntity( successResponse( ingredient.getIngredient().name() + " successfully created" ),
                    HttpStatus.CREATED );
        }
        catch ( final Exception e ) {
            return new ResponseEntity(
                    errorResponse( ingredient.getIngredient().name() + " cannot be created due to a client error" ),
                    HttpStatus.BAD_REQUEST ); // HttpStatus.FORBIDDEN
            // would be OK
            // too.
        }

    }

    /**
     * REST API method to allow deleting a Ingredient from the CoffeeMaker's
     * Inventory, by making a DELETE request to the API endpoint and indicating
     * the ingredient to delete (as a path variable)
     *
     * @param name
     *            The name of the Ingredient to delete
     * @return Success if the ingredient could be deleted; an error if the
     *         ingredient does not exist
     */
    @DeleteMapping ( BASE_PATH + "/ingredients/{name}" )
    public ResponseEntity deleteIngredient ( @PathVariable final String name ) {
        final Ingredient ingredient = ingredientService.findByIngredient( name );
        if ( null == ingredient ) {
            return new ResponseEntity( errorResponse( "No ingredient found for name " + name ), HttpStatus.NOT_FOUND );
        }
        ingredientService.delete( ingredient );

        return new ResponseEntity( successResponse( name + " was deleted successfully" ), HttpStatus.OK );
    }

    // @GetMapping ( BASE_PATH + "{object}/{field}/{value}" )
    // public ResponseEntity getObject ( @PathVariable final String object,
    // @PathVariable final String field,
    // @PathVariable final String value ) {
    // try {
    // Object obj = null;
    // switch ( object ) {
    // case "Ingredient": // go do something
    // break;
    //
    // case "Recipe": // go do something else
    // if (field.equals( "name" )) {
    // obj =
    // }
    // break;
    // }
    //
    // if ( null == obj ) {
    // return new ResponseEntity( HttpStatus.NOT_FOUND );
    // }
    //
    // return new ResponseEntity( obj, HttpStatus.OK );
    //
    // }
    // catch ( final Exception e ) {
    // return new ResponseEntity( "Something went wrong with your request :: " +
    // e.getCause(),
    // HttpStatus.BAD_REQUEST );
    // }
    //
    // }

}
