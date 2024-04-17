package pl.coderslab.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class Plan {
    private int id;
    private String name;
    private String description;
    private String created;
    private int adminId;

    public Plan(String name, String description, int adminId) {
        this.name = name;
        this.description = description;
        this.adminId = adminId;
        this.created = java.time.Instant.now().toString().split("\\.")[0].replace("T", " ");
    }

}


