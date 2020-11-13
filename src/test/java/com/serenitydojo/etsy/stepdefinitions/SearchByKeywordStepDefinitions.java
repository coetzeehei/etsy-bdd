package com.serenitydojo.etsy.stepdefinitions;

import com.serenitydojo.etsy.basket.ShoppingBasket;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import com.serenitydojo.etsy.navigation.Navigate;
import com.serenitydojo.etsy.search.DisplayedItems;
import com.serenitydojo.etsy.search.SearchForProducts;
import com.serenitydojo.etsy.viewitem.ViewItemDetails;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;

import java.util.List;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.assertj.core.api.Assertions.assertThat;

public class SearchByKeywordStepDefinitions {
    @Given("{actor} is looking for products on Esty")
    public void lookingForProductsOnEtsy(Actor actor) {
        actor.attemptsTo(
                Navigate.toTheHomePage()
        );
    }

    @When("{actor} searches for {string}")
    public void searchesForProduct(Actor actor, String keyword) {
        actor.attemptsTo(
              SearchForProducts.withKeyword(keyword)
        );
    }

    @Then("{actor} should only be shown products related to {string}")
    public void shouldSeeProductsAbout(Actor actor, String keyword) {
        List<String> displayedDescriptions =
                actor.asksFor(DisplayedItems.descriptions());

        assertThat(displayedDescriptions).allMatch(
                description -> description.toLowerCase()
                                          .contains(keyword.toLowerCase())
        );
    }

    @Given("{actor} has performed a search for {string}")
    public void hasPerformedASearchFor(Actor actor, String keyword) {
        actor.attemptsTo(
            Navigate.toTheHomePage(),
            SearchForProducts.withKeyword(keyword)
        );
    }

    @When("{actor} views the details for the first listed item")
    public void viewsTheDetailsForTheFirstListedItem(Actor actor) {
        actor.attemptsTo(
                ViewItemDetails.forOneOfTheDisplayedItems()
        );
    }

    @When("{actor} views her shopping basket")
    public void sheViewsHerShoppingBasket(Actor actor) {
        // TODO: Finish me
    }

    @Then("the basket should be empty")
    public void theBasketShouldBeEmpty() {
        theActorInTheSpotlight().attemptsTo(
                Ensure.that(ShoppingBasket.CONTENT).textContent().contains("Your basket is empty.")
        );
    }
}