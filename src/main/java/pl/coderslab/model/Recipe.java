package pl.coderslab.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@ToString

public class Recipe {

  private int id;

  private String name;

  private String ingredients;

  private String description;

  private Instant created = Instant.now();

  private Instant updated = Instant.now();

  private int preparationTime;

  private String preparation;

  private int adminId;

  public Recipe() {
  }

  public Recipe(String name, String ingredients, String description, int preparationTime, String preparation, int adminId) {

    this.name = name;
    this.ingredients = ingredients;
    this.description = description;
    this.preparationTime = preparationTime;
    this.preparation = preparation;
    this.adminId = adminId;
  }

  public List<String> getIngredientList() {
    String noWhiteSpaceIngredients = ingredients.replaceAll("\\s", ",");

//    By this process we just split on ",", and get rid of all commas
    List<String> ingredientsList = new ArrayList<>(List.of(noWhiteSpaceIngredients.split(",")));

//    filtrating process - using method reference, removing every element that is empty from the list
    ingredientsList.removeIf(String::isEmpty);

    return ingredientsList;
  }


}
