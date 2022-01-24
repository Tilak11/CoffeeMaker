package edu.ncsu.csc.CoffeeMaker.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

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

import com.fasterxml.jackson.databind.json.JsonMapper;

import edu.ncsu.csc.CoffeeMaker.common.TestUtils;
import edu.ncsu.csc.CoffeeMaker.models.Inventory;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;

@RunWith ( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
public class APITest {

    /**
     * MockMvc uses Spring's testing framework to handle requests to the REST
     * API
     */
    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    /**
     * Sets up the tests.
     */
    @Before
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();
    }

    @Test
    @Transactional
    public void testGettingRecipes () throws Exception {

        // final Recipe r = new Recipe();
        // r.setChocolate( 5 );
        // r.setCoffee( 3 );
        // r.setMilk( 4 );
        // r.setSugar( 8 );
        // r.setPrice( 10 );
        // r.setName( "Mocha" );
        //
        // mvc.perform( post( "/api/v1/recipes" ).contentType(
        // MediaType.APPLICATION_JSON )
        // .content( TestUtils.asJsonString( r ) ) ).andExpect( status().isOk()
        // );
        // final String recipe = mvc.perform( get( "/api/v1/recipes" ) ).andDo(
        // print() ).andExpect( status().isOk() )
        // .andReturn().getResponse().getContentAsString();
        // assertTrue( recipe.contains( "Mocha" ) );

        String recipe = mvc.perform( get( "/api/v1/recipes" ) ).andDo( print() ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();

        /* Figure out if the recipe we want is present */
        if ( !recipe.contains( "Mocha" ) ) {
            final Recipe r = new Recipe();
            r.setChocolate( 5 );
            r.setCoffee( 3 );
            r.setMilk( 4 );
            r.setSugar( 8 );
            r.setPrice( 10 );
            r.setName( "Mocha" );

            mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                    .content( TestUtils.asJsonString( r ) ) ).andExpect( status().isOk() );

            recipe = mvc.perform( get( "/api/v1/recipes" ) ).andDo( print() ).andExpect( status().isOk() ).andReturn()
                    .getResponse().getContentAsString();
        }

        assertTrue( recipe.contains( "Mocha" ) );

    }

    @Test
    @Transactional
    public void testGettingRecipesByName () throws Exception {

        String recipe = mvc.perform( get( "/api/v1/recipes" ) ).andDo( print() ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();

        final Recipe r = new Recipe();
        r.setChocolate( 5 );
        r.setCoffee( 3 );
        r.setMilk( 4 );
        r.setSugar( 8 );
        r.setPrice( 10 );
        r.setName( "Mocha" );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r ) ) ).andExpect( status().isOk() );

        final Recipe r1 = new Recipe();
        r1.setChocolate( 2 );
        r1.setCoffee( 5 );
        r1.setMilk( 7 );
        r1.setSugar( 9 );
        r1.setPrice( 8 );
        r1.setName( "Tea" );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r1 ) ) ).andExpect( status().isOk() );

        recipe = mvc.perform( get( String.format( "/api/v1/recipes/%s", "Mocha" ) ) ).andDo( print() )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();

        assertTrue( recipe.contains( "Mocha" ) );
        recipe = mvc.perform( get( String.format( "/api/v1/recipes/%s", "Tea" ) ) ).andDo( print() )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();

        assertTrue( recipe.contains( "Tea" ) );

        recipe = mvc.perform( get( String.format( "/api/v1/recipes/%s", "Coffee" ) ) ).andDo( print() )
                .andExpect( status().isNotFound() ).andReturn().getResponse().getContentAsString();
        assertEquals( "{\"status\":\"failed\",\"message\":\"No recipe found with name Coffee\"}", recipe );
    }

    @Test
    @Transactional
    public void testAddingInventory () throws Exception {

        final Inventory r = new Inventory();
        r.setChocolate( 50 );
        r.setCoffee( 50 );
        r.setMilk( 50 );
        r.setSugar( 50 );
        final JsonMapper mapper = new JsonMapper();

        mvc.perform( put( "/api/v1/inventory" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r ) ) ).andExpect( status().isOk() );
        final String inventory = mvc.perform( get( "/api/v1/inventory" ) ).andDo( print() ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();
        final Inventory i = mapper.readValue( inventory, Inventory.class );

        assertEquals( r.getChocolate(), i.getChocolate() );
        assertEquals( r.getCoffee(), i.getCoffee() );
        assertEquals( r.getMilk(), i.getMilk() );
        assertEquals( r.getSugar(), i.getSugar() );

    }

    @Test
    @Transactional
    public void testMakeCoffee () throws Exception {

        final Recipe r = new Recipe();
        r.setChocolate( 5 );
        r.setCoffee( 3 );
        r.setMilk( 4 );
        r.setSugar( 8 );
        r.setPrice( 10 );
        r.setName( "Mocha" );

        final Inventory inv = new Inventory();
        inv.setChocolate( 50 );
        inv.setCoffee( 50 );
        inv.setMilk( 50 );
        inv.setSugar( 50 );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r ) ) ).andExpect( status().isOk() );
        mvc.perform( put( "/api/v1/inventory" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( inv ) ) ).andExpect( status().isOk() );
        mvc.perform( post( String.format( "/api/v1/makecoffee/%s", "Mocha" ) ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( 20 ) ) ).andExpect( status().isOk() )
                .andExpect( jsonPath( "$.message" ).value( 10 ) );

    }

    @Test
    @Transactional
    public void testDeleteRecipe () throws Exception {

        String recipe = mvc.perform( get( "/api/v1/recipes" ) ).andDo( print() ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();
        assertFalse( recipe.contains( "Mocha" ) );

        final Recipe r = new Recipe();
        r.setChocolate( 5 );
        r.setCoffee( 3 );
        r.setMilk( 4 );
        r.setSugar( 8 );
        r.setPrice( 10 );
        r.setName( "Mocha" );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r ) ) ).andExpect( status().isOk() );

        recipe = mvc.perform( get( "/api/v1/recipes" ) ).andDo( print() ).andExpect( status().isOk() ).andReturn()
                .getResponse().getContentAsString();
        assertTrue( recipe.contains( "Mocha" ) );

        mvc.perform( delete( String.format( "/api/v1/recipes/%s", "Mocha" ) ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r ) ) ).andExpect( status().isOk() );
        recipe = mvc.perform( get( "/api/v1/recipes" ) ).andDo( print() ).andExpect( status().isOk() ).andReturn()
                .getResponse().getContentAsString();
        assertFalse( recipe.contains( "Mocha" ) );

    }

}