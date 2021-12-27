package api;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.collections.Lists;
import org.testng.log4testng.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class NonBlockingAPITests {
    private static final Logger logger = Logger.getLogger(NonBlockingAPITests.class);

    @Test(dataProvider = "data-provider")
    public void testNonBlockingAPI(final String uri) throws InterruptedException, ExecutionException {
        HttpClient client = HttpClient.newHttpClient();
        int parallelTasks = 100;
        ExecutorService execService = Executors.newFixedThreadPool(parallelTasks);
        logger.info("Starting execution");
        long startMillis = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        List<CompletableFuture<Void>> completableFutureList = Lists.newArrayList();
        for (int i=0; i < parallelTasks; i++){
            completableFutureList.add(CompletableFuture.runAsync(() -> {
                try {
                    CompletableFuture<HttpResponse<String>> response = client.sendAsync(HttpRequest.newBuilder().uri(URI.create(uri)).build(),
                            BodyHandlers.ofString());
                    response.get();
                } catch (Exception e) {
                    Assert.fail();
                }
            }, execService));
        }
        CompletableFuture<Object> allFutureResult =
                CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[completableFutureList.size()]))
                .thenApply(f -> completableFutureList.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));
        allFutureResult.get();
        long endMillis = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        logger.info("Done executing");
        logger.info(String.format("Total Time Taken -- %d", endMillis - startMillis));

    }

    @DataProvider(name = "data-provider")
    public Object[][] dataProvider(){
        return new String[][]{
                {"http://localhost:8082/non-blocking/resource"},
                {"http://localhost:8082/non-blocking/resource/mono"}
        };
    }

}
