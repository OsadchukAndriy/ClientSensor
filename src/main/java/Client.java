import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Client {
    public static void main(String[] args) {
        final String name = "Sensor1";

        register(name);

        Random random = new Random();
        double max = 45.0;
        Boolean isRaining;
        for (int i = 0; i <= 500; i++) {
            System.out.println(i);
         sendMeasurement(random.nextDouble() * max, random.nextBoolean(), name);


        }

    }

    private static void sendMeasurement(double value, boolean raining, String name ){
        final String url = "http://localhost:8080/measurements/add";
        Map<String, Object> json = new HashMap();

        json.put("value", value);
        json.put("raining", raining);
        json.put("sensor", Map.of("name", name));

        makePostRequestWithJSONData(url, json);

    }

    private static void register(String name) {
        String url = "http://localhost:8080/sensors/registration";

        Map<String, Object> json = new HashMap();
        json.put("name", name);

        makePostRequestWithJSONData(url, json);
    }

    private static void makePostRequestWithJSONData(String url, Map<String, Object> jsonData) {
        RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(jsonData, httpHeaders);
        try {
            restTemplate.postForObject(url, request, String.class);
            System.out.println("Измерение успешно отправлено на сервер!");
        } catch (HttpClientErrorException e) {
            System.out.println("ОШИБКА!");
            System.out.println(e.getMessage());
        }
    }
}
