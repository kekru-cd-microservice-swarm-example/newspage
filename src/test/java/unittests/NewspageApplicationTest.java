package unittests;

import de.fh_dortmund.kekru001.bachelorarbeit.newspage.NewspageApplication;
import de.fh_dortmund.kekru001.bachelorarbeit.newspage.service.NewsService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=NewspageApplication.class)
public class NewspageApplicationTest {

    @Autowired
    NewsService newsService;

    @Test
    public void contextLoads() {
        Assert.assertNotNull(newsService);
    }

}
