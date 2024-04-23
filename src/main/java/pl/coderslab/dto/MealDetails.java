package pl.coderslab.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MealDetails {

    private String dayName;
    private String meal_name;
    private int recipeId;
    private String recipeName;
}
