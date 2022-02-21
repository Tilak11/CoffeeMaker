// package edu.ncsu.csc.CoffeeMaker;
//
// import static org.junit.Assert.assertEquals;
//
// import java.util.Iterator;
// import java.util.List;
//
// import javax.transaction.Transactional;
//
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit4.SpringRunner;
//
// import edu.ncsu.csc.CoffeeMaker.models.Recipe;
// import edu.ncsu.csc.CoffeeMaker.services.RecipeService;
//
// @RunWith ( SpringRunner.class )
// @EnableAutoConfiguration
// @SpringBootTest ( classes = TestConfig.class )
// public class TestDatabaseInteraction {
//
// @Autowired
// private RecipeService recipeService;
//
// @Test
// @Transactional
// public void testRecipes () {
//
// recipeService.deleteAll();
// final Recipe recipe1 = new Recipe();
//
// recipe1.setChocolate( 10 );
// recipe1.setCoffee( 5 );
// recipe1.setMilk( 10 );
// recipe1.setName( "Recipe1" );
// recipe1.setPrice( 5 );
// recipe1.setSugar( 10 );
//
// recipeService.save( recipe1 );
//
// final List<Recipe> dbRecipes = recipeService.findAll();
// assertEquals( 1, dbRecipes.size() );
//
// final Recipe dbRecipe = dbRecipes.get( 0 );
//
// assertEquals( recipe1.getName(), dbRecipe.getName() );
// assertEquals( recipe1.getChocolate(), dbRecipe.getChocolate() );
// assertEquals( recipe1.getCoffee(), dbRecipe.getCoffee() );
// assertEquals( recipe1.getMilk(), dbRecipe.getMilk() );
// assertEquals( recipe1.getPrice(), dbRecipe.getPrice() );
// assertEquals( recipe1.getSugar(), dbRecipe.getSugar() );
//
// }
//
// @Test
// @Transactional
// public void testFindByName () {
//
// recipeService.deleteAll();
//
// final Recipe recipe1 = new Recipe();
// final Recipe recipe2 = new Recipe();
// final Recipe recipe3 = new Recipe();
//
// recipe1.setChocolate( 10 );
// recipe1.setCoffee( 5 );
// recipe1.setMilk( 10 );
// recipe1.setName( "Recipe1" );
// recipe1.setPrice( 5 );
// recipe1.setSugar( 10 );
//
// recipeService.save( recipe1 );
//
// recipe2.setChocolate( 10 );
// recipe2.setCoffee( 5 );
// recipe2.setMilk( 10 );
// recipe2.setName( "Recipe2" );
// recipe2.setPrice( 5 );
// recipe2.setSugar( 10 );
//
// recipeService.save( recipe2 );
//
// recipe3.setChocolate( 10 );
// recipe3.setCoffee( 5 );
// recipe3.setMilk( 10 );
// recipe3.setName( "Recipe3" );
// recipe3.setPrice( 5 );
// recipe3.setSugar( 10 );
//
// recipeService.save( recipe3 );
//
// final List<Recipe> dbRecipes = recipeService.findAll();
// assertEquals( 3, dbRecipes.size() );
//
// assertEquals( "Recipe1", recipeService.findByName( "Recipe1" ).getName() );
// assertEquals( "Recipe2", recipeService.findByName( "Recipe2" ).getName() );
// assertEquals( "Recipe3", recipeService.findByName( "Recipe3" ).getName() );
//
// }
//
// @Test
// @Transactional
// public void testEditRecipe () {
//
// recipeService.deleteAll();
//
// final Recipe recipe1 = new Recipe();
//
// recipe1.setChocolate( 10 );
// recipe1.setCoffee( 5 );
// recipe1.setMilk( 10 );
// recipe1.setName( "Recipe1" );
// recipe1.setPrice( 5 );
// recipe1.setSugar( 10 );
//
// recipeService.save( recipe1 );
//
// final List<Recipe> dbRecipes = recipeService.findAll();
// assertEquals( 1, dbRecipes.size() );
//
// final Recipe dbRecipe = dbRecipes.get( 0 );
//
// assertEquals( recipe1.getPrice(), dbRecipe.getPrice() );
// assertEquals( recipe1.getSugar(), dbRecipe.getSugar() );
//
// dbRecipe.setPrice( 15 );
// dbRecipe.setSugar( 12 );
// dbRecipe.setMilk( 20 );
// dbRecipe.setChocolate( 20 );
// dbRecipe.setCoffee( 20 );
// recipeService.save( dbRecipe );
//
// assertEquals( 15, (int) dbRecipe.getPrice() );
// assertEquals( 12, (int) dbRecipe.getSugar() );
// assertEquals( 20, (int) dbRecipe.getChocolate() );
// assertEquals( 20, (int) dbRecipe.getCoffee() );
// assertEquals( 20, (int) dbRecipe.getMilk() );
//
// assertEquals( 1, recipeService.count() );
//
// }
//
// @Test
// @Transactional
// public void testDeleteRecipe () {
//
// recipeService.deleteAll();
//
// final Recipe recipe1 = new Recipe();
// recipe1.setChocolate( 10 );
// recipe1.setCoffee( 5 );
// recipe1.setMilk( 10 );
// recipe1.setName( "Recipe1" );
// recipe1.setPrice( 5 );
// recipe1.setSugar( 10 );
// recipeService.save( recipe1 );
//
// final Recipe recipe2 = new Recipe();
// recipe2.setChocolate( 10 );
// recipe2.setCoffee( 5 );
// recipe2.setMilk( 10 );
// recipe2.setName( "Recipe2" );
// recipe2.setPrice( 5 );
// recipe2.setSugar( 10 );
// recipeService.save( recipe2 );
//
// final Recipe recipe3 = new Recipe();
// recipe3.setChocolate( 10 );
// recipe3.setCoffee( 5 );
// recipe3.setMilk( 10 );
// recipe3.setName( "Recipe3" );
// recipe3.setPrice( 5 );
// recipe3.setSugar( 10 );
// recipeService.save( recipe3 );
//
// final Recipe recipe4 = new Recipe();
// recipe4.setChocolate( 10 );
// recipe4.setCoffee( 5 );
// recipe4.setMilk( 10 );
// recipe4.setName( "Recipe4" );
// recipe4.setPrice( 5 );
// recipe4.setSugar( 10 );
// recipeService.save( recipe4 );
//
// final Recipe recipe5 = new Recipe();
// recipe5.setChocolate( 10 );
// recipe5.setCoffee( 5 );
// recipe5.setMilk( 10 );
// recipe5.setName( "Recipe5" );
// recipe5.setPrice( 5 );
// recipe5.setSugar( 10 );
// recipeService.save( recipe5 );
//
// assertEquals( 5, recipeService.count() );
// Iterator<Recipe> dbRecipes = recipeService.findAll().iterator();
// assertEquals( "Recipe1", dbRecipes.next().getName() );
// assertEquals( "Recipe2", dbRecipes.next().getName() );
// assertEquals( "Recipe3", dbRecipes.next().getName() );
// assertEquals( "Recipe4", dbRecipes.next().getName() );
// assertEquals( "Recipe5", dbRecipes.next().getName() );
//
// recipeService.delete( recipe3 );
//
// assertEquals( 4, recipeService.count() );
// dbRecipes = recipeService.findAll().iterator();
// assertEquals( "Recipe1", dbRecipes.next().getName() );
// assertEquals( "Recipe2", dbRecipes.next().getName() );
// assertEquals( "Recipe4", dbRecipes.next().getName() );
// assertEquals( "Recipe5", dbRecipes.next().getName() );
//
// recipeService.delete( recipe1 );
// recipeService.delete( recipe4 );
//
// assertEquals( 2, recipeService.count() );
// dbRecipes = recipeService.findAll().iterator();
// assertEquals( "Recipe2", dbRecipes.next().getName() );
// assertEquals( "Recipe5", dbRecipes.next().getName() );
//
// recipeService.deleteAll();
// assertEquals( 0, recipeService.count() );
//
// }
//
// }
