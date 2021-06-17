package com.richardahasting.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleAB extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("Start: " + getClass().getName() + "\t" + Thread.currentThread().getName());
    startPromise.complete();
  }
}