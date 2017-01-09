package de.fh_dortmund.kekru001.bachelorarbeit.newspage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.springframework.data.annotation.Id;

/**
 * Created by krummenauer on 02.01.2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {

    @Id
    private String idMongo;
    private long id = 0;
    private String titel;
    private String text;
    private String autor;
    private String bildURL = "http://www.greensoul.de/wp-content/uploads/2015/09/Plastikente.jpg";
    private Date datum;
    private boolean carousel = false;
    private List<Kommentar> kommentare = new LinkedList<Kommentar>();

    public News(long id, String titel, String text, String autor){
        this.id = id;
        this.titel = titel;
        this.text = text;
        this.autor = autor;
    }
}
