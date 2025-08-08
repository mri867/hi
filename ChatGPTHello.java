import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatGPTHello {
    public static String chatGPT(String prompt) throws IOException {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "YOUR_API_KEY";  // replace with your actual key
        String model = "gpt-3.5-turbo";

        URL endpoint = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) endpoint.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + apiKey);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String body = "{\"model\":\"" + model + "\",\"messages\":[{\"role\":\"user\",\"content\":\"" + prompt + "\"}]}";
        try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
            writer.write(body);
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }

        //  Extract the content from the JSON response simply:
        return extractContent(response.toString());
    }

    private static String extractContent(String json) {
        int start = json.indexOf("content") + 11;
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(chatGPT("Hi, ChatGPT!"));
    }
}
