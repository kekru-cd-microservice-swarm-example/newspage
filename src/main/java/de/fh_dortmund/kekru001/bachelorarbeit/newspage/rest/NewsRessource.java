package de.fh_dortmund.kekru001.bachelorarbeit.newspage.rest;

import de.fh_dortmund.kekru001.bachelorarbeit.newspage.entity.News;
import de.fh_dortmund.kekru001.bachelorarbeit.newspage.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    NewsService newsService;

    @RequestMapping("/newslist")
    public List<News> startseiteNews(){
        return newsService.findLastXNews(10);
        // return Arrays.asList(new News("news1","Neuveröffentlichung!", "Ein Text", "Herr von Ribbeck"), new News("news2","Feine Sache", "Kleines Stück Plastik gefunden", "Herr von Ribbeck auf Ribbeck"));
    }

    @RequestMapping("/newscarousel")
    public List<News> carouselNews(){
        return newsService.findLastXNews(3, true);
        //return Arrays.asList(new News("news3", "Abc!", "Ein Text", "Herr von Ribbeck"), new News("news4", "Feine Sache", "Kleines Stück Plastik gefunden", "Herr von Ribbeck auf Ribbeck"));
    }

    @RequestMapping("/newsfull/{newsid}")
    public News newsFull(@PathVariable("newsid") long newsid){
        return newsService.findByIdNews(newsid);
    }
}
