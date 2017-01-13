package unittests;

import de.fh_dortmund.kekru001.bachelorarbeit.newspage.NewspageApplication;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=NewspageApplication.class)
public class NewspageApplicationTest {

    @Test
    public void contextLoads() {
    }

}
