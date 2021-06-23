package com.richardahasting.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class VerticleN extends AbstractVerticle {

  final static Logger LOG = LoggerFactory.getLogger(VerticleN.class);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    LOG.debug("Start: " + getClass().getName() + "\t" + Thread.currentThread().getName()
      +
      " with config " + config().toString());
    startPromise.complete();
  }
  @Override
  public void stop(final Promise<Void> stopPromise)
  {
    LOG.debug("Stop: " + getClass().getName() + "\t" + Thread.currentThread().getName() +
      " with config " + config().toString());
    stopPromise.complete();
  }
}
