package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReader {private String path;
    private String separator;

    public CSVReader(String path, String separator) {
        this.path = path;
        this.separator = separator;
    }

    public List<List<String>> read() throws IOException {
        List<List<String>> result = new ArrayList<>();

        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(this.path))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(this.separator);
                List<String> record = new ArrayList<>(Arrays.asList(data));
                result.add(record);
            }
        }

        return result;
    }
}