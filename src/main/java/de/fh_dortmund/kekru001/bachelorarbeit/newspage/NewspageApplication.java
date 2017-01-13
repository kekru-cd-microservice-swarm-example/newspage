package de.fh_dortmund.kekru001.bachelorarbeit.newspage;

import de.fh_dortmund.kekru001.bachelorarbeit.newspage.dao.NewsRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewspageApplication {

	public static void main(String[] args) {
		NewsRepository n = SpringApplication.run(NewspageApplication.class, args).getBean(NewsRepository.class);

		//n.save(new News("news1", "Neuveröffentlichung schon heute!", "Ein Text ist lang", "Herr von Ribbeck von Ribbeck"));
		//n.save(new News("news2",  "Feine Sache", "Kleines Stück Plastik gefunden", "Herr B."));
	}
}
