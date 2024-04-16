package pl.coderslab.model;

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

  private LocalDateTime created;

  private LocalDateTime updated;

  private int preparationTime;

  private String preparation;

  private int adminId;

  public Recipe() {
  }

  public Recipe(int id, String name, String ingredients, String description, LocalDateTime created,
      LocalDateTime updated, int preparationTime, String preparation, int adminId) {
    this.id = id;
    this.name = name;
    this.ingredients = ingredients;
    this.description = description;
    this.created = created;
    this.updated = updated;
    this.preparationTime = preparationTime;
    this.preparation = preparation;
    this.adminId = adminId;
  }
}
