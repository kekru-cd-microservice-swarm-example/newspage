package de.fh_dortmund.kekru001.bachelorarbeit.newspage.rest;

import de.fh_dortmund.kekru001.bachelorarbeit.newspage.entity.News;
import de.fh_dortmund.kekru001.bachelorarbeit.newspage.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    }

    @RequestMapping("/newsforeground")
    public List<News> foregroundNews(){
        return newsService.findLastXNewsForeground(3);
    }

    @RequestMapping("/newsfull/{newsid}")
    public News newsFull(@PathVariable("newsid") String newsid){
        return newsService.findByIdNews(newsid);
    }

    @RequestMapping("/createTestdata")
    public List<News> createTestdata(){
        return newsService.createTestdata();
    }
}
