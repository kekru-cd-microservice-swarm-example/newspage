package de.fh_dortmund.kekru001.bachelorarbeit.newspage.rest;

import de.fh_dortmund.kekru001.bachelorarbeit.newspage.entity.News;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by krummenauer on 02.01.2017.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class NewsRessource {

    @RequestMapping("/newslist")
    public List<News> startseiteNews(){
        return Arrays.asList(new News("Neuveröffentlichung!", "Ein Text", "Herr von Ribbeck"), new News("Feine Sache", "Kleines Stück Plastik gefunden", "Herr von Ribbeck auf Ribbeck"));
    }

    @RequestMapping("/newscarousel")
    public List<News> carouselNews(){
        return Arrays.asList(new News("Abc!", "Ein Text", "Herr von Ribbeck"), new News("Feine Sache", "Kleines Stück Plastik gefunden", "Herr von Ribbeck auf Ribbeck"));
    }
}
