package de.fh_dortmund.kekru001.bachelorarbeit.newspage.dao;

import de.fh_dortmund.kekru001.bachelorarbeit.newspage.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Created by krummenauer on 09.01.2017.
 */
public interface NewsRepository extends MongoRepository<News, String> {

    News findById(String id);

    @Query("{ 'foreground' : true }")
    Page<News> lastNewsForegroundOnly(Pageable pageable);

    @Query("{ 'foreground' : false }")
    Page<News> lastNewsNoForeground(Pageable pageable);
}
