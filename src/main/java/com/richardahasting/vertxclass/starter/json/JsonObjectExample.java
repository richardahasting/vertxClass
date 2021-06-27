package com.richardahasting.vertxclass.starter.json;

import io.vertx.core.json.JsonArray;
import org.junit.Test;
import static org.junit.Assert.*;

import io.vertx.core.json.JsonObject;

import java.util.HashMap;


public class JsonObjectExample {

  @Test
  void jsonObjectCanBeMapped(){
    JsonObject jsonObject = new JsonObject();

    jsonObject.put("id",1);
    jsonObject.put("name","Alice");
    jsonObject.put("Love", "Wins");
    jsonObject.put("link", "Sausages");

    assertEquals("", jsonObject.encode());

  }

  @Test
  void jsonObjectCanBeCreatedFromMap(){
    HashMap<String, Object> mapObject = new HashMap();

    mapObject.put("id",1);
    mapObject.put("name","Alice");
    mapObject.put("Love", "Wins");
    mapObject.put("link", "Sausages");
    JsonObject jsonObject = new JsonObject(mapObject);

    assertEquals(mapObject, jsonObject.getMap());
  }


  @Test
  void jsonArrayCanBeMapped(){
    JsonArray ja = new JsonArray();
    ja.add(new JsonObject().put("id",1))
      .add(new  JsonObject().put("id","cornhole"));

    System.out.println(ja.encode());


  }

  @Test
  void canMapObject(){
    Person p = new Person(1,"Millie", true);
    JsonObject ja = JsonObject.mapFrom(p);
    System.out.println(ja.encode());

    Person ps = ja.mapTo(Person.class);

    System.out.println(ps);

  }

  public static void main(String[] args) {
     JsonObjectExample joe = new JsonObjectExample();
     //joe.jsonObjectCanBeMapped();
     // joe.jsonObjectCanBeCreatedFromMap();
     joe.jsonArrayCanBeMapped();
     joe.canMapObject();

  }
}
