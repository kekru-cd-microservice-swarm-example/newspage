package de.fh_dortmund.kekru001.bachelorarbeit.newspage.selenium;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.fh_dortmund.kekru001.bachelorarbeit.newspage.entity.News;
import de.fh_dortmund.kekru001.bachelorarbeit.newspage.service.Utils;
import org.bson.Document;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by krummenauer on 09.01.2017.
 */
public class NewsAnsichtTest {

    static final String NEWS_COLLECTION = "news";
    static WebDriver driver;
    MongoClient mongoClient;
    MongoDatabase db;

    @BeforeClass
    public static void initClass() throws MalformedURLException {
        final String seleniumHost = Utils.getPropertOrSystemEnvOrDefault("selenium.host", "10.1.6.210");
        final String seleniumPort = Utils.getPropertOrSystemEnvOrDefault("selenium.port", "30002");
        final String hub = "http://" + seleniumHost + ":" + seleniumPort + "/wd/hub";

        driver = new RemoteWebDriver(
                new URL(hub),
                DesiredCapabilities.firefox());

        driver.manage().window().maximize();

        final String url = "http://newspage:8081/";
        //Webseite ueber Selenium im Firefox aufrufen
        driver.get(url);
    }

    @Before
    public void resetMongoDB(){
        String host =  Utils.getPropertOrSystemEnvOrDefault("mongo.host", "10.1.6.210");
        int port = Integer.parseInt(Utils.getPropertOrSystemEnvOrDefault("mongo.port", "27017"));
        mongoClient = new MongoClient(host, port);
        assertNotNull(mongoClient);

        db = mongoClient.getDatabase("test");
        assertNotNull(db);

        //leeren der Datenbank
        db.getCollection(NEWS_COLLECTION).drop();

        save(new News("news1", "Neuveröffentlichung!", "Ein Text", "Herr von Ribbeck"));
        save(new News("news2","Feine Sache", "Kleines Stück Plastik gefunden", "Herr von Ribbeck auf Ribbeck"));


    }

    private void save(News n){
        Document document = new Document("id", n.getId())
                .append("titel", n.getTitel())
                .append("text", n.getText())
                .append("autor", n.getAutor())
                .append("bildURL", n.getBildURL())
                .append("datum", n.getDatum())
                .append("carousel", n.isCarousel());

        MongoCollection<Document> c = db.getCollection(NEWS_COLLECTION);
        c.insertOne(document);
    }

    @Test
    public void testDetailseiteWirdGeoeffnet() throws InterruptedException {
        Thread.sleep(5000);
    }


    @AfterClass
    public static void tearDown(){
        driver.quit();
    }


}
