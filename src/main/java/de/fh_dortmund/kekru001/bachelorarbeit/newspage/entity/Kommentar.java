package de.fh_dortmund.kekru001.bachelorarbeit.newspage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by krummenauer on 02.01.2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kommentar {
    private String text;
    private String autor;
}
