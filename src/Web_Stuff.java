import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
            HttpRequest request = HttpRequest.newBuilder(URI.create(adresse + "/api/get-view"))
                    .header("Content-Type", "application/json")
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
    public HttpResponse<String> get_response() throws ExecutionException, InterruptedException {
            return responseFuture.get();
    }

    public boolean send_data(String pData)
    {
        HttpRequest request = HttpRequest.newBuilder(URI.create(adresse + "/api/push-view"))
                .POST(HttpRequest.BodyPublishers.ofString(pData))
                .header("content-type","application/json")
                .build();
        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        try {
            return futureResponse.get().statusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }
}
