import model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        String URL = "http://94.198.50.185:7081/api/users";

        // Получить список пользователей
        List<User> allUsers = restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        }).getBody();
        System.out.println("Список пользователей:");
        System.out.println(allUsers);

        // Получить заголовок Set-Cookie из ответа
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null, String.class);
        String setCookie = responseEntity.getHeaders().getFirst("Set-Cookie");

        // Устанавливаем заголовок
        HttpHeaders headers = new HttpHeaders();
        System.out.println(responseEntity.getHeaders());
        headers.set("Cookie", setCookie);

        // Сохранить пользователя
        User user1 = new User(3L, "James", "Brown", (byte) 40);
        HttpEntity<User> httpEntity1 = new HttpEntity<>(user1, headers);
        ResponseEntity<String> response1 = restTemplate.exchange(URL, HttpMethod.POST, httpEntity1, String.class);
        System.out.println("Ответ на сохранение пользователя:");
        System.out.println(response1.getBody());

        // Обновить пользователя
        User user2 = new User(3L, "Thomas", "Shelby", (byte) 40);
        HttpEntity<User> httpEntity2 = new HttpEntity<>(user2, headers);
        ResponseEntity<String> response2 = restTemplate.exchange(URL, HttpMethod.PUT, httpEntity2, String.class);
        System.out.println("Ответ на обновление пользователя:");
        System.out.println(response2.getBody());

        // Удалить пользователя
        HttpEntity<User> httpEntity3 = new HttpEntity<>(null, headers);
        ResponseEntity<String> response3 = restTemplate.exchange(URL + "/" + 3L, HttpMethod.DELETE, httpEntity3, String.class);
        System.out.println("Ответ на удаление пользователя:");
        System.out.println(response3.getBody());

    }

}

