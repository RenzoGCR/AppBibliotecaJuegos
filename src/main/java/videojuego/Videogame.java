package videojuego;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Videogame {
    private Integer id;
    private String title;
    private String platform;
    private Integer year;
    private String description;
    private Integer user_id;
    private String image_url;
}
