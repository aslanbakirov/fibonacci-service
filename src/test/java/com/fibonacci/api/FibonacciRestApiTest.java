package com.fibonacci.api;

import com.fibonacci.main.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.HashMap;
import org.json.JSONObject;

import static org.junit.Assert.assertEquals;

/**
 * Created by aslan on 12/06/16.
 */
public class FibonacciRestApiTest  {

    public static final int TEST_SERVER_PORT = 5555;

    private FibonacciHttpService service;
    FibonacciRestApi api;
    private Client client;

    @Before
    public void setUp() throws Exception {
        service = new FibonacciHttpService();
        service.start();
        api = new FibonacciRestApi();
        client = ClientBuilder.newClient();
    }

    @After
    public void tearDown() throws Exception {
        service.stop();
    }

    private UriBuilder newRequestBuilder(String path) {
        return UriBuilder.fromPath(path).host(Main.host()).port(TEST_SERVER_PORT).scheme("http");

    }

    @Test
    public void testFibonacciCalculationType() throws Exception {
        URI uri = newRequestBuilder("/fibonacci/nonrecursive/5").build();
        Response res = client.target(uri).request().get();
        assertEquals(200, res.getStatus());
        URI uri2 = newRequestBuilder("/fibonacci/recursive/5").build();
        Response res2 = client.target(uri2).request().get();
        assertEquals(200, res2.getStatus());
        URI uri3 = newRequestBuilder("/fibonacci/something/5").build();
        Response res3 = client.target(uri3).request().get();
        assertEquals(403, res3.getStatus());
    }

    @Test
    public void testFibonacciNegativeNumber() throws Exception {
        URI uri = newRequestBuilder("/fibonacci/nonrecursive/-5").build();
        Response res = client.target(uri).request().get();
        assertEquals(403, res.getStatus());
    }

    @Test
    public void testFibonacciValidRequest() throws Exception {
        URI uri = newRequestBuilder("/fibonacci/nonrecursive/4").build();
        Response res = client.target(uri).request().get();
        String entity = res.readEntity(String.class);
        JSONObject jsonObject = new JSONObject(entity);
        JSONObject message = jsonObject.getJSONObject("message");
        assertEquals(message.getJSONArray("result").toString(), "[0,1,1,2]");
    }
}
