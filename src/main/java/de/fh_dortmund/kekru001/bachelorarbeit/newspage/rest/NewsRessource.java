package de.fh_dortmund.kekru001.bachelorarbeit.newspage.rest;

import de.fh_dortmund.kekru001.bachelorarbeit.newspage.entity.Kommentar;
import de.fh_dortmund.kekru001.bachelorarbeit.newspage.entity.News;
import org.springframework.web.bind.annotation.*;

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
        return Arrays.asList(new News("news1","Neuveröffentlichung!", "Ein Text", "Herr von Ribbeck"), new News("news2","Feine Sache", "Kleines Stück Plastik gefunden", "Herr von Ribbeck auf Ribbeck"));
    }

    @RequestMapping("/newscarousel")
    public List<News> carouselNews(){
        return Arrays.asList(new News("news3", "Abc!", "Ein Text", "Herr von Ribbeck"), new News("news4", "Feine Sache", "Kleines Stück Plastik gefunden", "Herr von Ribbeck auf Ribbeck"));
    }

    @RequestMapping("/newsfull/{newsid}")
    public News newsFull(@PathVariable("newsid") String newsid){
        News n = new News("news5", "Ein Birnbaum!", "Ein Text des Birnbaums", "Herr von Ribbeck");
        n.setKommentare(Arrays.asList(new Kommentar("Schöne Sache", "Johannes"), new Kommentar("Ich schreibe viel und gerne", "Der Sandmann")));
        return n;
    }
}
