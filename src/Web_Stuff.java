import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.*;
import java.util.concurrent.CompletableFuture;

public class Web_Stuff {
    private CompletableFuture<HttpResponse<String>> responseFuture;
    private HttpClient client;
    private String adresse;

    public Web_Stuff(String pAdresse) {
        try {
            // create a client
            client = HttpClient.newHttpClient();

            adresse = pAdresse;

            // create a request
            HttpRequest request = HttpRequest.newBuilder(URI.create(adresse + "/get-view"))
                    .header("accept", "application/json")
                    .build();

            // use the client to send the request
            responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            // We can do other things here while the request is in-flight
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /** This blocks until the request is complete
     *
     * @return
     */
    public HttpResponse<String> get_response() {
        try {
            return responseFuture.get();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean send_data(String pData)
    {
        HttpRequest request = HttpRequest.newBuilder(URI.create(adresse + "/push-view"))
                .POST(HttpRequest.BodyPublishers.ofString(pData))
                .header("content-type","application/json")
                .build();
        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Text");
        try {
            System.out.println(futureResponse.get().headers());
            System.out.println(futureResponse.get().statusCode());
            return futureResponse.get().statusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }
}
