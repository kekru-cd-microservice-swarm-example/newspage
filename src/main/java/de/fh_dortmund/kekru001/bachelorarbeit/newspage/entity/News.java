package de.fh_dortmund.kekru001.bachelorarbeit.newspage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private String id;
    private String titel;
    private String text;
    private String autor;
    private String bildURL;
    private Date datum = new Date();
    private boolean foreground = false;

    public News(String id, String titel, String text, String autor, String bildURL){
        this.id = id;
        this.titel = titel;
        this.text = text;
        this.autor = autor;
        this.bildURL = bildURL;
    }
}
