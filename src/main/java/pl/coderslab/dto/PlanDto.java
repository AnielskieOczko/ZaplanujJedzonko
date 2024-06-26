package pl.coderslab.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor

/**
 * This is a data transfer object whose data fields will be used to be displayed on the view.
 */
public class PlanDto {
    private int id;
    private String name;
    private String description;
    List<MealDetailsDto> mealDetailsDtoList = new ArrayList<>();
}
