package seleniumtests;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.fh_dortmund.kekru001.bachelorarbeit.newspage.entity.News;
import de.fh_dortmund.kekru001.bachelorarbeit.newspage.util.Utils;
import org.bson.Document;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

public class NewsAnsichtTest {

    static final String NEWS_COLLECTION = "news";
    static String newspageURL;
    static WebDriver driver;
    static MongoClient mongoClient;
    static MongoDatabase db;

    @BeforeClass
    public static void initClass() throws MalformedURLException {
        final String seleniumHost = Utils.getPropertOrSystemEnvOrDefault("selenium.host", "localhost");
        final String seleniumPort = Utils.getPropertOrSystemEnvOrDefault("selenium.port", "4444");
        final String hub = "http://" + seleniumHost + ":" + seleniumPort + "/wd/hub";

        driver = new RemoteWebDriver(
                new URL(hub),
                DesiredCapabilities.firefox());

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        initMongoClient();
    }

    public static void main(String[] args) throws InterruptedException {
        initMongoClient();
        new NewsAnsichtTest().resetMongoDB();
    }

    private static void initMongoClient() {
        final String websiteHost = Utils.getPropertOrSystemEnvOrDefault("website.host", "localhost");
        final String websitePort = Utils.getPropertOrSystemEnvOrDefault("website.port", "8081");
        newspageURL = "http://" + websiteHost + ":" + websitePort + "/newspage/";


        String host = Utils.getPropertOrSystemEnvOrDefault("mongo.host", "10.1.6.210");
        int port = Integer.parseInt(Utils.getPropertOrSystemEnvOrDefault("mongo.port", "27017"));
        mongoClient = new MongoClient(host, port);
        assertNotNull(mongoClient);

        db = mongoClient.getDatabase("test");
        assertNotNull(db);
    }

    @Before
    public void resetMongoDB() throws InterruptedException {
        //leeren der Datenbank
        db.getCollection(NEWS_COLLECTION).drop();

        final String loremIpsum = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer";

        save(new News("news-der-kleine-schmetterling", "Der kleine Schmetterling", "Der neue Thriller von Max Mustermann erzählt eine packende Geschichte, ausgelöst durch einen kleinen Schmetterling. " + loremIpsum, "Max Schneemann", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ea/Cethosia_cyane.jpg/220px-Cethosia_cyane.jpg"));
        save(new News("news-gedrosselte-regionalbahn", "Heute wird es später", "Aufgrund von ersten Schneeflocken, werden die Regionalbahnen auf die Hälfte ihrer Geschwindigkeit gedrosselt. " + loremIpsum, "Herr von Ribbeck auf Ribbeck", "http://www.duden.de/_media_/full/E/Eisenbahn-201100279272.jpg"));

        News n = new News("news-schnee", "Schnee!", "Der Winter kommt und es wird Zeit die Skier heraus zu kramen. " + loremIpsum, "Max Schneemann", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6c/Frischer_neuschnee.jpg/220px-Frischer_neuschnee.jpg");
        n.setForeground(true);
        save(n);

        Thread.sleep(3000);

        //Webseite ueber Selenium im Firefox aufrufen
        driver.get(newspageURL);
    }

    private void save(News n) {
        Document document = new Document("id", n.getId())
                .append("titel", n.getTitel())
                .append("text", n.getText())
                .append("autor", n.getAutor())
                .append("bildURL", n.getBildURL())
                .append("datum", n.getDatum())
                .append("foreground", n.isForeground());

        MongoCollection<Document> c = db.getCollection(NEWS_COLLECTION);
        c.insertOne(document);
    }

    @Test
    public void testDetailseiteWirdGeoeffnet() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(By.id("news-button-0")).click();
        Thread.sleep(3000);
        Assert.assertEquals("Heute wird es später", driver.findElement(By.id("artikel-titel")).getText());
    }


    @AfterClass
    public static void tearDown() {
        driver.quit();
        mongoClient.close();
    }


}
