package com.richardahasting.eventloop;

import com.richardahasting.verticles.MainVerticle;
import io.vertx.core.*;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

import java.sql.Time;
import java.util.concurrent.TimeUnit;


public class EventLoopExample extends AbstractVerticle {
  final static Logger LOG = LoggerFactory.getLogger(EventLoopExample.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx(new VertxOptions().setMaxEventLoopExecuteTime(500).
      setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS).
      setBlockedThreadCheckInterval(1).
        setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS).
      setEventLoopPoolSize(20));
    vertx.deployVerticle(EventLoopExample.class.getName(),
      new DeploymentOptions().setInstances(4));

  }

  public void start(Promise<Void> startPromise) throws Exception {
    LOG.debug("Start");

    startPromise.complete();
    // do not do this in an eventloop
    Thread.sleep(5000);

  }

}
