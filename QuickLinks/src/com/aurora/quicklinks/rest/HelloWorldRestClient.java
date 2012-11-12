package com.aurora.quicklinks.rest;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class HelloWorldRestClient {

 public static void main(String[] args) {
  Client client = new Client();
  WebResource webResource = client.resource("http://localhost:9080/HelloWorldRest");
  String response = webResource.path("resources").path("helloworld")
    .queryParam("world", "World!").get(String.class);
  System.out.println("Response: " + response);
 }
}