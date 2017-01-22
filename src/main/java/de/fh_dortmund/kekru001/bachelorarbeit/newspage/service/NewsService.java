package de.fh_dortmund.kekru001.bachelorarbeit.newspage.service;

import de.fh_dortmund.kekru001.bachelorarbeit.newspage.dao.NewsRepository;
import de.fh_dortmund.kekru001.bachelorarbeit.newspage.entity.News;
import de.fh_dortmund.kekru001.bachelorarbeit.newspage.util.Utils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by krummenauer on 08.01.2017.
 */
@Component
public class NewsService {

    @Autowired
    NewsRepository newsRepository;

    public List<News> findLastXNews(int count) {
        return shortenText(newsRepository.lastNewsNoForeground(new PageRequest(0, count, new Sort(Sort.Direction.DESC, "datum"))).getContent());
    }

    public List<News> findLastXNewsForeground(int count) {
        return shortenText(newsRepository.lastNewsForegroundOnly(new PageRequest(0, count, new Sort(Sort.Direction.DESC, "datum"))).getContent());
    }


    public News findByIdNews(String id) {
        return newsRepository.findById(id);
    }

    private List<News> shortenText(List<News> news){
        news.stream().forEach(n -> n.setText(Utils.shorten(n.getText(), 100) + "..."));
        return news;
    }


    public List<News> createTestdata() {
        newsRepository.deleteAll();

        final String loremIpsum = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer";
        newsRepository.save(new News("news-der-kleine-schmetterling", "Der kleine Schmetterling", "Der neue Thriller von Max Mustermann erzählt eine packende Geschichte, ausgelöst durch einen kleinen Schmetterling. " + loremIpsum, "Max Schneemann", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ea/Cethosia_cyane.jpg/220px-Cethosia_cyane.jpg"));
        newsRepository.save(new News("news-gedrosselte-regionalbahn", "Heute wird es später", "Aufgrund von ersten Schneeflocken, werden die Regionalbahnen auf die Hälfte ihrer Geschwindigkeit gedrosselt. " + loremIpsum, "Herr von Ribbeck auf Ribbeck", "http://www.duden.de/_media_/full/E/Eisenbahn-201100279272.jpg"));

        News n = new News("news-schnee", "Schnee!", "Der Winter kommt und es wird Zeit die Skier heraus zu kramen. " + loremIpsum, "Max Schneemann", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6c/Frischer_neuschnee.jpg/220px-Frischer_neuschnee.jpg");
        n.setForeground(true);
        newsRepository.save(n);

        return newsRepository.findAll();
    }
}
