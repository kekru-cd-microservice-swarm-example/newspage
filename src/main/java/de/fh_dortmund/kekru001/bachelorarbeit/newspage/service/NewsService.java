package de.fh_dortmund.kekru001.bachelorarbeit.newspage.service;

import de.fh_dortmund.kekru001.bachelorarbeit.newspage.dao.NewsRepository;
import de.fh_dortmund.kekru001.bachelorarbeit.newspage.entity.News;
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
    private NewsRepository newsRepository;

    public List<News> findLastXNews(int count) {
        return newsRepository.lastNews(new PageRequest(0, count, new Sort(Sort.Direction.DESC, "datum"))).getContent();
    }

    public List<News> findLastXNewsCarousel(int count) {
        return newsRepository.lastNewsCarouselOnly(new PageRequest(0, count, new Sort(Sort.Direction.DESC, "datum"))).getContent();
    }


    public News findByIdNews(long id) {
        return newsRepository.findById(id);
    }



}
