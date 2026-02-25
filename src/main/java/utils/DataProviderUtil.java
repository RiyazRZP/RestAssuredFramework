package utils;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.DataProvider;


import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataProviderUtil {
    @DataProvider(name = "apiDp")
    public Object[][] dp(){
        return new Object[][]{
                {"1", "George"},
                {"2", "Janet"}
        };
    }
    @DataProvider(name="dpJson")
    public Object[][] getData(Method m) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        List<Map<String, Object>> list = mapper.readValue(
                new File("resources/testData/testDataForDp.json"),
                new TypeReference<List<Map<String, Object>>>() {}
        );

        List<Map<String, Object>> filtered = new ArrayList<>();

        for (Map<String, Object> data : list) {
            if (data.get("testCaseName").toString().equalsIgnoreCase(m.getName())) {
                filtered.add(data);
            }
        }
        if (filtered.isEmpty()) {
            throw new RuntimeException("No test data found for method: " + m.getName());
        }

        Object[][] result = new Object[filtered.size()][1];

        for (int i = 0; i < filtered.size(); i++) {
            result[i][0] = filtered.get(i);
        }

        return result;
    }
}
