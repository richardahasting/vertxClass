package com.richardahasting.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class verticleB extends AbstractVerticle {

  final static Logger LOG = LoggerFactory.getLogger(verticleB.class);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    LOG.debug("Start: " + getClass().getName() + "\t" + Thread.currentThread().getName());
    startPromise.complete();
  }

  @Override
  public void stop(final Promise<Void> stopPromise)
  {
    LOG.debug("Stop: "  + getClass().getName() + "\t" + Thread.currentThread().getName());
    stopPromise.complete();
  }
}
