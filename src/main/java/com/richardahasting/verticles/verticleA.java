package com.richardahasting.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class verticleA extends AbstractVerticle {

  final static Logger LOG = LoggerFactory.getLogger(MainVerticle.class);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    LOG.debug("Start: " + getClass().getName() + "\t" + Thread.currentThread().getName());
    vertx.deployVerticle(new VerticleAA());
    vertx.deployVerticle(new VerticleAB());
    vertx.deployVerticle(new VerticleAA());
    vertx.deployVerticle(new VerticleAB());
    vertx.deployVerticle(new VerticleAA());
    vertx.deployVerticle(new VerticleAB());
    vertx.deployVerticle(new VerticleAA());
    vertx.deployVerticle(new VerticleAA(), whenDeployed -> {
      vertx.undeploy(whenDeployed.result());});
    startPromise.complete();
  }
}
