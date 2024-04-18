package pl.coderslab.model;

import java.time.Instant;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

  public Recipe( String name, String ingredients, String description, int preparationTime, String preparation, int adminId) {

    this.name = name;
    this.ingredients = ingredients;
    this.description = description;
    this.preparationTime = preparationTime;
    this.preparation = preparation;
    this.adminId = adminId;
  }
}
