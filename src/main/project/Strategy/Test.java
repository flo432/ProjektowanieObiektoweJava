package Strategy;

import Utils.ConfigReader;
import Utils.DatabaseConfig;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

public class Test {
    public static void main(String[] args) throws IOException, ParseException {
        ConfigReader reader = new ConfigReader();

        DatabaseConfig databaseConfig = null;
        try {
            databaseConfig = reader.getDatabaseConfig();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.OFF);

        DatabaseHandler handler = new DatabaseHandler(Objects.requireNonNull(databaseConfig));
        handler.getOne("Articles", "in-quis-magna");
//        MongoHandler mongoHandler = MongoHandler.getInstance();
//        MongoDatabase db = mongoHandler.getConnection();
//        Map<String,Object> map = new HashMap<>();
//        map.put("title", "aaa");
//        map.put("description", "bbb");
//        map.put("paragraph", "ccc");
//        map.put("thumbinail", "ddd");
//        map.put("image", "eee");
//
//
//
//        collection.updateOne(eq("title", "e-title"), new Document("$set", new Document(map)));
//        handler.update("Articles", map, "a-url");


//        MongoCollection<Document> collection = db.getCollection("About");
//        Document doc = new Document("fullname", "Bogdan Chwalinski")
//                .append("photo", "http://wstaw.org/m/2018/02/05/12_1.jpg")
//                .append("abouth1", "Proin non lorem augue.")
//                .append("aboutp1", "\n" +
//                        "\n" +
//                        "Ut nec nunc velit. Etiam in augue diam. Integer lacinia scelerisque massa, quis volutpat risus scelerisque nec. Nam consequat, risus vel dictum bibendum, velit enim tristique tellus, id hendrerit tellus turpis eget tortor. Duis ac volutpat orci, at varius nisi. Phasellus et sollicitudin ante, ut sollicitudin mauris. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.\n" +
//                        "\n" +
//                        "Duis varius massa eget gravida aliquam. Pellentesque aliquet vulputate odio, sed dapibus nulla ornare non. Ut sed eros libero. Donec id faucibus augue, in elementum ligula. Phasellus luctus ornare mi. Curabitur ac posuere dolor. Aenean sit amet ante et augue facilisis sagittis. Sed vehicula, augue a condimentum hendrerit, dolor neque imperdiet massa, in semper dui augue porttitor nunc. Vestibulum enim nulla, maximus volutpat nibh vel, scelerisque dapibus ipsum. Mauris felis nisi, euismod nec ex eget, efficitur porta velit. Pellentesque in dui aliquam, posuere tellus quis, suscipit risus. Nulla ut leo risus. Pellentesque non condimentum orci. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.\n" +
//                        "\n" +
//                        "Fusce scelerisque hendrerit urna eleifend tincidunt. Praesent quis finibus lorem. Pellentesque sollicitudin risus maximus nisi iaculis aliquet. Vestibulum quis tristique diam. Mauris porta velit eget purus vehicula, vitae pharetra nisi pellentesque. Phasellus ac faucibus magna, aliquam porttitor leo. Nulla sollicitudin commodo eros sit amet vestibulum. Nunc viverra dolor sit amet urna lobortis, eget faucibus orci dignissim.\n" +
//                        "\n" +
//                        "Mauris vulputate, risus id malesuada commodo, odio leo posuere libero, ac dapibus felis risus a magna. Pellentesque iaculis semper ornare. Sed nec fermentum neque. Suspendisse nec lorem ullamcorper, dapibus augue a, vehicula ex. Curabitur sit amet tincidunt dui. Ut viverra elementum nibh, in tincidunt diam varius sed. Maecenas consectetur sollicitudin tristique. Sed nunc lorem, euismod ut ex vel, semper malesuada dolor. Curabitur eleifend porta dolor ut volutpat. Suspendisse pretium rhoncus elementum. Phasellus vitae ullamcorper odio. Donec et auctor felis, vel tincidunt purus. Etiam nec justo metus. Integer lacinia vulputate ligula, sed efficitur elit maximus vitae. Cras convallis faucibus eros, sed tempor ipsum faucibus eu.\n" +
//                        "\n" +
//                        "Duis quis molestie tortor. Fusce iaculis, elit ac mollis cursus, nulla ex imperdiet tellus, ac eleifend lacus sem a ante. Integer at molestie sem. Nullam vehicula tristique felis, vitae suscipit urna tempus vitae. Aenean non ex bibendum, auctor dui ac, posuere justo. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Praesent mattis lacinia egestas. Nulla dictum lorem bibendum condimentum aliquet. Aenean vel tellus diam. Duis vel justo quis nulla mattis viverra.")
//                .append("abouth2", "Sed tempus gravida turpis ac tristique.")
//                .append("aboutp2", "Sed neque dolor, placerat vitae congue laoreet, varius id ante. Donec congue, nisi ut venenatis fermentum, mauris nibh ultrices nisl, at eleifend est nisi sed magna. Etiam a tempor mi, eget congue sem. Donec fringilla quis ex id tempus. Aenean ornare quis mauris eget mollis. Aliquam condimentum luctus magna eget consequat. In hac habitasse platea dictumst. Sed aliquam eros id condimentum condimentum. In a tortor vel dolor aliquet cursus. Duis vehicula tincidunt posuere. Nulla dictum justo ac tellus tristique, non facilisis mauris venenatis. Fusce et justo urna. Nullam vitae elit et dolor aliquet hendrerit non ut ipsum. Proin varius magna quis sem semper tristique. Phasellus sollicitudin tellus et ex cursus pretium.\n" +
//                        "\n" +
//                        "Curabitur pretium congue neque. In vel ante in arcu aliquet pulvinar ultrices quis nisi. Nulla faucibus nibh sit amet turpis sagittis, quis posuere nulla malesuada. Vestibulum suscipit facilisis tincidunt. Sed tristique ante quis augue sollicitudin convallis. Mauris ultricies risus quis lacinia congue. Proin quis enim risus. Nullam sollicitudin odio vel libero bibendum, nec pretium dui viverra.\n" +
//                        "\n" +
//                        "Quisque lobortis laoreet orci, venenatis tincidunt ligula vehicula nec. Nam at mi placerat, viverra elit in, scelerisque urna. Ut pulvinar a sapien at pharetra. Phasellus vel pulvinar enim. Pellentesque pretium nunc sit amet ipsum varius pharetra. Duis eleifend fringilla lacus, condimentum tempus elit commodo et. Vestibulum rhoncus semper dolor, et dignissim arcu. Suspendisse in interdum sem. Duis a mattis massa, vel euismod purus. In hac habitasse platea dictumst.\n" +
//                        "\n" +
//                        "Suspendisse potenti. Proin ut ipsum rutrum, interdum lorem ac, faucibus ante. Vivamus finibus luctus nisl vitae porttitor. Etiam faucibus aliquet facilisis. Aenean euismod laoreet leo. Cras mattis aliquam dolor, ac hendrerit orci dapibus tempor. Quisque cursus ut nulla in ultrices. Duis mattis felis dictum tortor tempor facilisis. Morbi eu ipsum congue augue dictum vestibulum nec vel ligula. Vivamus gravida sed eros id faucibus. Proin faucibus faucibus nisl, eu luctus odio. Mauris porttitor diam sit amet purus accumsan interdum.")
//                .append("url","about");
//
//        collection.insertOne(doc);

//        handler.getOne("test", "1");
//        MongoCollection<Document> collection = db.getCollection("test");
//        Document myDoc = collection.find().first();
//        System.out.println(myDoc.toJson());

//        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
//        MongoDatabase db = mongoClient.getDatabase("mydb");
//        MongoCollection<Document> collection = db.getCollection("test");
//
////        Document doc = new Document("name", "vd")
////                .append("type", "df")
////                .append("count", 1)
////                .append("info", new Document("x", 203).append("y", 102));
////        collection.insertOne(doc);
//        System.out.println(collection.count());
//        Document myDoc = collection.find().first();
//        System.out.println(myDoc.toJson());
//
//        MongoCursor<Document> cursor = collection.find().iterator();
//        try {
//            while (cursor.hasNext()) {
//                System.out.println(cursor.next().containsKey("name"));
////                System.out.println(cursor.next().toJson());
//            }
//        } finally {
//            cursor.close();
//        }



//        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
//        mongoLogger.setLevel(Level.SEVERE);
////        MongoCollection<Document> coll = db.getCollection("Articles");
//        MongoCollection<Document> dbCollection = db1.getCollection("Articles");
//
//        Document myDoc = dbCollection.find().first();
//
//        System.out.println(myDoc.toJson());
    }
}
