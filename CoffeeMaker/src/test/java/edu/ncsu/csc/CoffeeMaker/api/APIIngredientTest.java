package edu.ncsu.csc.CoffeeMaker.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.ncsu.csc.CoffeeMaker.common.TestUtils;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;

@RunWith ( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
public class APIIngredientTest {

    /**
     * MockMvc uses Spring's testing framework to handle requests to the REST
     * API
     */
    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private IngredientService     iservice;

    @Autowired
    private InventoryService      invservice;

    /**
     * Sets up the tests.
     */
    @Before
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();
    }

    @Test
    @Transactional
    public void ensureIngredient () throws Exception {
        iservice.deleteAll();

        final Ingredient i = new Ingredient( "Coffee", 500 );

        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( i ) ) ).andExpect( status().isOk() );

    }

    @Test
    @Transactional
    public void testIngredientAPI () throws Exception {

        iservice.deleteAll();

        final Ingredient ingredient = new Ingredient( "Coffee", 500 );

        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( ingredient ) ) );

        Assert.assertEquals( 1, (int) iservice.count() );

    }

    @Test
    @Transactional
    public void testAddIngredient2 () throws Exception {
        iservice.deleteAll();

        /*
         * Tests a Ingredient with a duplicate name to make sure it's rejected
         */

        Assert.assertEquals( "There should be no ingredients in the CoffeeMaker", 0, iservice.findAll().size() );
        final Ingredient i1 = new Ingredient( "Coffee", 500 );

        iservice.save( i1 );

        final Ingredient i2 = new Ingredient( "new coffee", -500 );
        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( i2 ) ) ).andExpect( status().is4xxClientError() );

        Assert.assertEquals( "There should only one ingredient in the CoffeeMaker", 1, iservice.findAll().size() );
    }

    @Test
    @Transactional
    public void testAddIngredient15 () throws Exception {
        iservice.deleteAll();

        /* Tests to make sure that our cap of 3 Ingredients is enforced */

        Assert.assertEquals( "There should be no Ingredients in the CoffeeMaker", 0, iservice.findAll().size() );

        final Ingredient i1 = new Ingredient( "Coffee", 500 );
        iservice.save( i1 );
        final Ingredient i2 = new Ingredient( "Milk", 500 );
        iservice.save( i2 );
        final Ingredient i3 = new Ingredient( "PumpkinSpice", 500 );
        iservice.save( i3 );

        Assert.assertEquals( "Creating three Ingredients should result in three Ingredients in the database", 3,
                iservice.count() );

        final Ingredient r4 = new Ingredient( "Milk", 500 );

        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( i2 ) ) ).andExpect( status().isOk() );
        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r4 ) ) ).andExpect( status().isConflict() );

        Assert.assertEquals( "Creating a fourth Ingredient should not get saved", 3, iservice.count() );
    }

    @Test
    @Transactional
    public void testDeleteIngredient () throws Exception {

        String ingredient = mvc.perform( get( "/api/v1/ingredients" ) ).andExpect( status().isOk() ).andReturn()
                .getResponse().getContentAsString();
        assertFalse( ingredient.contains( "Ice" ) );

        final Ingredient r = new Ingredient( "Ice", 500 );

        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r ) ) ).andExpect( status().isOk() );

        ingredient = mvc.perform( get( "/api/v1/ingredients" ) ).andExpect( status().isOk() ).andReturn().getResponse()
                .getContentAsString();
        assertTrue( ingredient.contains( "Ice" ) );

        mvc.perform( delete( String.format( "/api/v1/ingredients/%s", "Ice" ) )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( r ) ) )
                .andExpect( status().isOk() );
        ingredient = mvc.perform( get( "/api/v1/ingredients/%s", "Ice" ) ).andExpect( status().isNotFound() )
                .andReturn().getResponse().getContentAsString();
        assertFalse( ingredient.contains( "Ice" ) );
    }

}
