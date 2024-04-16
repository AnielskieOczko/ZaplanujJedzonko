package pl.coderslab.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DayName {
    private int id;
    private String name;
    private int displayOrder;


    public DayName(int id, String name, int displayOrder) {
        this.id = id;
        this.name = name;
        this.displayOrder = displayOrder;
    }

    @Override
    public String toString() {
        return "DayName [id=" + id + ", name=" + name + ", displayOrder=" + displayOrder + "]";
    }
}
