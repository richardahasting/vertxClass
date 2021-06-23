package com.richardahasting.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class PointToPoint extends AbstractVerticle {

  final public static String Address = "my.request.address";
  final static Logger LOG = LoggerFactory.getLogger(PointToPoint.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx();

    LOG.debug("Starting now ");
    vertx.deployVerticle(new Sender());
    vertx.deployVerticle(new Receiver());
  }


  static class Sender extends AbstractVerticle {

    final static Logger LOG = LoggerFactory.getLogger(RequestResponseExample.RequestVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      LOG.debug("Start");

      startPromise.complete();
      var message = "Hello world!";
      LOG.debug("Sending message: " + message);
      // do not do this in an eventloop
      var eventBus = vertx.eventBus();
      vertx.setPeriodic(100, id -> {
        LOG.debug("Sending: " + message);
        eventBus.send(Sender.class.getName(), message);
      });
    }

  }

  static class Receiver extends AbstractVerticle {
    final static Logger LOG = LoggerFactory.getLogger(RequestResponseExample.ResponseVerticle.class);

    public void start(Promise<Void> startPromise) throws Exception {
      LOG.debug("Start");

      startPromise.complete();
      var eventBus = vertx.eventBus();
      vertx.eventBus().<String>consumer(Sender.class.getName(), message -> {
        LOG.debug("Got message: " + message.body());
        message.reply("Not today old man!");
      });

    }
  }
}
