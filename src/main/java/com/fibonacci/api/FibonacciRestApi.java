package com.fibonacci.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aslan on 11/06/16.
 */

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class FibonacciRestApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(FibonacciRestApi.class);

    public  FibonacciRestApi(){}

    @GET
    @Path("/fibonacci/{type}/{number}")
    public Response fibonacciSeries(@Context UriInfo uriInfo , @PathParam("number") int number, @PathParam("type") String type) {
        final List<Integer> result;
        final String calculationType = type;

        if(number < 0){
            return Response.status(Response.Status.FORBIDDEN).entity(new GenericAPIResponse() {
                @Override
                public int getStatus() {
                    return Response.Status.FORBIDDEN.getStatusCode();
                }
                @Override
                public Object getMessage() {
                    return "Negative numbers do not have fibonacci series, please use positive numbers";
                }
            }).build();
        }
        else {
            if (type.equals("recursive")) {
                result = calculateFibonacciRecursively(number);
            }else if (type.equals("nonrecursive")){
                result = calculateFibonacciNonRecursively(number);
            }
            else{
                return Response.status(Response.Status.FORBIDDEN).entity(new GenericAPIResponse() {
                    @Override
                    public int getStatus() {
                        return Response.Status.FORBIDDEN.getStatusCode();
                    }
                    @Override
                    public Object getMessage() {
                        return "Not supported calculation type. Please use recursive or nonrecursive";
                    }
                }).build();
            }

            return Response.ok().entity(new GenericAPIResponse() {
                @Override
                public Object getMessage() {
                    return new HashMap<String, Object>() {
                        {
                            put("calculationType", calculationType);
                            put("result", result);

                        }
                    };
                }
            }).build();
        }
    }

    private static List<Integer> calculateFibonacciRecursively(int a){
        List<Integer> result = new ArrayList<Integer>();
        for(int i=0;i<a;i++){
            result.add(calculateFibonacciRecursive(i));
        }
        return  result;
    }

    private static int calculateFibonacciRecursive(int n){
        if(n == 0)
            return 0;
        else if(n == 1)
            return 1;
        else
            return calculateFibonacciRecursive(n - 1) + calculateFibonacciRecursive(n - 2);
    }

    private  static List<Integer> calculateFibonacciNonRecursively(int n){
        List<Integer> fibonacciList = new ArrayList<Integer>();

        int f = 0, g = 1;

        for (int i = 0; i < n; i++) {
            fibonacciList.add(f);
            f = f + g;
            g = f - g;
        }
        return fibonacciList;
    }
}
