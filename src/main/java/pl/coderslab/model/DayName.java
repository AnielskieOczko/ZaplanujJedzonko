package pl.coderslab.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DayName {
    private int id;
    private String name;
    private int displayOrder;


    public DayName(int id, String name, int displayOrder) {
        this.id = id;
        this.name = name;
        this.displayOrder = displayOrder;
    }

}
