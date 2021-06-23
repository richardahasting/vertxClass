package com.richardahasting.eventloop;

import io.vertx.core.*;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class WorkerVerticle extends AbstractVerticle {

  final static Logger LOG = LoggerFactory.getLogger(WorkerVerticle.class);
  static Vertx vertx = Vertx.vertx(new VertxOptions().setMaxEventLoopExecuteTime(500).
    setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS).
    setBlockedThreadCheckInterval(1).
    setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS).
    setEventLoopPoolSize(20));

  public static void main(String[] args) {

    vertx.deployVerticle(WorkerVerticle.class.getName(),
      new DeploymentOptions().setInstances(1));
  }


  public void start(Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle( new WorkerVerticle(), new DeploymentOptions().setWorker(true).setWorkerPoolSize(1).setWorkerPoolName("Ricks"));
    LOG.debug("Deployed as a worker verticle.");
    Thread.sleep(4999);
    startPromise.complete();
    /* vertx.executeBlocking( event -> {
      LOG.debug("Executing blocking code");
      try {
        Thread.sleep(4999);
        event.fail("Sucks man!");
      } catch (Exception e) {
        LOG.debug("caught Exception " + e.getMessage());
        event.fail(e);
      }
    }, result -> {
      if (result.succeeded())
        LOG.debug("Blocking call completed");
      else
        LOG.debug("Blocking call failed because " + result.cause());
    }); */

  }
}
