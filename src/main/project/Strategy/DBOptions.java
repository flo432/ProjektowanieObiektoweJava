package Strategy;

import java.util.List;
import java.util.Map;

interface DBOptions {

    List<Data> getOne(String source, String clause);
    List<Data> getAll(String source);
    List<Data> getByKey(String source, String clause);
    void delete(String source, String clause);
    void insert(String source, Map<String, Object> values);
    void update(String source, Map<String, Object> values, String clause);

}
