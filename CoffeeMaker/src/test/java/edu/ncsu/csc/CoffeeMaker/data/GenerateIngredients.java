// package edu.ncsu.csc.CoffeeMaker.data;
//
// import java.util.ArrayList;
//
// import javax.transaction.Transactional;
//
// import org.junit.Assert;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit4.SpringRunner;
//
// import edu.ncsu.csc.CoffeeMaker.TestConfig;
// import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
// import edu.ncsu.csc.CoffeeMaker.models.enums.IngredientType;
// import edu.ncsu.csc.CoffeeMaker.services.IngredientService;
//
// @RunWith ( SpringRunner.class )
// @EnableAutoConfiguration
// @SpringBootTest ( classes = TestConfig.class )
// public class GenerateIngredients {
//
// @Autowired
// private IngredientService ingredientService;
//
// @SuppressWarnings ( "unchecked" )
// @Test
// @Transactional
// public void testCreateIngredients () {
// ingredientService.deleteAll();
//
// final Ingredient i1 = new Ingredient( IngredientType.COFFEE, 5 );
//
// ingredientService.save( i1 );
//
// final Ingredient i2 = new Ingredient( IngredientType.MILK, 3 );
//
// ingredientService.save( i2 );
//
// final ArrayList<Ingredient> list = (ArrayList<Ingredient>)
// ingredientService.findAll();
// for ( int i = 0; i < list.size(); i++ ) {
// System.out.println( list.get( i ).toString() );
// }
//
// Assert.assertEquals( 2, ingredientService.count() );
//
// }
// }
