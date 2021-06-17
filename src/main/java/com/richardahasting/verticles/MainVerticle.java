package com.richardahasting.verticles;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new com.richardahasting.verticles.MainVerticle());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("Start: " + getClass().getName());
    vertx.deployVerticle(new verticleB());
    vertx.deployVerticle(new verticleA());
    startPromise.complete();
  }
}
