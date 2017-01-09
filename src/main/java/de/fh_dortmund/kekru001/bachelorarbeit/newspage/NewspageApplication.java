package de.fh_dortmund.kekru001.bachelorarbeit.newspage;

import de.fh_dortmund.kekru001.bachelorarbeit.newspage.dao.NewsRepository;
import de.fh_dortmund.kekru001.bachelorarbeit.newspage.entity.News;
import de.fh_dortmund.kekru001.bachelorarbeit.newspage.service.NewsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewspageApplication {

	public static void main(String[] args) {
		NewsRepository n = SpringApplication.run(NewspageApplication.class, args).getBean(NewsRepository.class);

		//n.save(new News(0, "Neuveröffentlichung schon heute!", "Ein Text ist lang", "Herr von Ribbeck von Ribbeck"));
		//n.save(new News(0,  "Feine Sache", "Kleines Stück Plastik gefunden", "Herr B."));
	}
}
