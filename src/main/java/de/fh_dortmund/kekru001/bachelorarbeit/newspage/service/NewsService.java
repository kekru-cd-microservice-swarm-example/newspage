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



}
