package Strategy;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;

abstract class DODManager implements DBOptions{

    abstract MongoDatabase getConnection() throws IOException, ParseException;

    private Data processData(Document myDoc){
        Map<String, Object> result = new HashMap<>();
        Set<Map.Entry<String,Object>> entries = myDoc.entrySet();

            for (Map.Entry<String, Object> mapEntry : entries) {
                result.put(mapEntry.getKey(), mapEntry.getValue());
            }
        return new Data(result);
    }

    @Override
    public List<Data> getOne(String source, String clause) {
        List<Data> data = new ArrayList<>();
        try {
            MongoDatabase db = getConnection();
            MongoCollection<Document> collection = db.getCollection(source);
            Document myDoc = collection.find(eq("url", clause)).first();
            data.add(processData(myDoc));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public List<Data> getAll(String source) {
        List<Data> data = new ArrayList<>();

        try {
            MongoDatabase db = getConnection();
            MongoCollection<Document> collection = db.getCollection(source);
            try (MongoCursor<Document> cursor = collection.find().iterator()) {
                while (cursor.hasNext()) {
                    data.add(processData(cursor.next()));
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public List<Data> getByKey(String source, String clause) {
        return this.getOne(source, clause);
    }

    @Override
    public void delete(String source, String clause) {
        try {
            MongoDatabase db = getConnection();
            MongoCollection<Document> collection = db.getCollection(source);
            collection.deleteOne(eq("url", clause));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(String source, Map<String, Object> values) {
        try {
            MongoDatabase db = getConnection();
            MongoCollection<Document> collection = db.getCollection(source);
            collection.insertOne(new Document(values));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String source, Map<String, Object> values, String clause) {
        try {
            MongoDatabase db = getConnection();
            MongoCollection<Document> collection = db.getCollection(source);
            collection.updateOne(eq("url", clause), new Document("$set", new Document(values)));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
