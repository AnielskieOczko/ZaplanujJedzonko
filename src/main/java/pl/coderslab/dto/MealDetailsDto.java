package pl.coderslab.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MealDetailsDto {
    private int recipePlanId;
    private String dayName;
    private String mealName;
    private int recipeId;
    private String recipeName;
}
