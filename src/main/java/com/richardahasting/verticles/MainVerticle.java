package com.richardahasting.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.UUID;

public class MainVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new com.richardahasting.verticles.MainVerticle());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("Start: " + getClass().getName() + "\t" + Thread.currentThread().getName());
    vertx.deployVerticle(new verticleB());
    vertx.deployVerticle(new verticleB());
    vertx.deployVerticle(new verticleA());
    vertx.deployVerticle(new verticleB());
    vertx.deployVerticle(new verticleB());

    vertx.deployVerticle(new verticleA());
    vertx.deployVerticle(new verticleB());
    vertx.deployVerticle(new verticleB());
    vertx.deployVerticle(new verticleA());
    vertx.deployVerticle(new verticleB());
    vertx.deployVerticle(new verticleB());
    vertx.deployVerticle(VerticleN.class.getName(),
      new DeploymentOptions().setInstances(6).setConfig( new JsonObject()
      .put("ID", UUID.randomUUID().toString()).put("Name",VerticleN.class.getName())));
    startPromise.complete();
  }
}
