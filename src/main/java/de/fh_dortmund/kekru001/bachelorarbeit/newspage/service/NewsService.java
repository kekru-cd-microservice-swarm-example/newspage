package de.fh_dortmund.kekru001.bachelorarbeit.newspage.service;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.fh_dortmund.kekru001.bachelorarbeit.newspage.entity.News;
import lombok.val;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by krummenauer on 08.01.2017.
 */
@Component
public class NewsService {

    public static final String COLLECTION_ENTITYIDS = "entityids";
    public static final String COLLECTION_NEWS = "news";
    private MongoDatabase db;
    private MongoClient mongoClient;

    @PostConstruct
    public void init(){

        MongoClientOptions options = MongoClientOptions.builder()
                //Secondary Nodes werden bevorzugt genutzt, beim lesenden Zugriff -> Load Balancing
                .readPreference(ReadPreference.secondaryPreferred())
                .build();

        mongoClient = new MongoClient("newspage-mongo", options);

        db = mongoClient.getDatabase("newspageDB");
    }

    @PreDestroy
    public void cleanUp(){
        mongoClient.close();
    }

    private long nextId(){
        MongoCollection<Document> c = db.getCollection(COLLECTION_ENTITYIDS);
        val it = c.find().iterator();
        final String key = "previousid";
        while(it.hasNext()){
            val document = it.next();
            if(document.containsKey(key)){
                long lastId = document.getLong(key);
                long old = lastId;
                lastId++;
                c.replaceOne(new Document(key, old), new Document(key, lastId));
                System.out.println(lastId);
                return lastId;
            }
        }

        long id = 1;
        c.insertOne(new Document(key, id));
        System.out.println("its " + id);
        return id;
    }


    public News save(News n) {
        boolean insert = false;
        if(n.getId() == 0){
            n.setId(nextId());
            insert = true;
        }


        Document document = new Document("id", n.getId())
                .append("titel", n.getTitel())
                .append("text", n.getText())
                .append("autor", n.getAutor())
                .append("bildURL", n.getBildURL())
                .append("datum", n.getDatum())
                .append("carousel", n.isCarousel());

        MongoCollection<Document> c = db.getCollection(COLLECTION_NEWS);

        if(insert){
            c.insertOne(document);
        }else{
            c.replaceOne(new Document("id", n.getId()), document);
        }

        return n;
    }

    public List<News> findLastXNews(int count) {
        return findLastXNews(count, false);
    }

    public List<News> findLastXNews(int count, boolean carouselOnly) {
        Bson filter = carouselOnly ? new BasicDBObject("carousel", true) : new BasicDBObject();

        return readNews(db.getCollection(COLLECTION_NEWS)
                .find(filter)
                //absteigend nach datum sortieren
                .sort(new BasicDBObject("datum", -1))
                .limit(count));
    }


    public News findByIdNews(long id) {
        List<News> result = readNews(db.getCollection(COLLECTION_NEWS).find(new BasicDBObject("id", id)));
        if(result.isEmpty()){
            return null;
        }
        return result.get(0);
    }

    private List<News> readNews(FindIterable<Document> c){
        List<News> result = new LinkedList<News>();
        c.forEach(new Block<Document>() {

            @Override
            public void apply(Document b) {

                News n = new News();
                n.setId(b.getLong("id"));
                n.setTitel(b.getString("titel"));
                n.setText(b.getString("text"));
                n.setAutor(b.getString("autor"));
                n.setBildURL(b.getString("bildURL"));
                n.setDatum(b.getDate("datum"));
                n.setCarousel(b.getBoolean("carousel"));
                result.add(n);
            }
        });
        return result;
    }


}
