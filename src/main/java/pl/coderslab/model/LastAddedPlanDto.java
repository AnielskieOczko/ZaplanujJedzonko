package pl.coderslab.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor

/**
 * This is data transfer object whose data fields will be used to be displayed on the view.
 */
public class LastAddedPlanDto {
    private String planName;
    private String dayName;
    private String meal_name;
    private int recipeId;
    private String recipeName;
}
