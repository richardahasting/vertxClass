package com.richardahasting.eventbus;

import com.richardahasting.verticles.MainVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class RequestResponseExample extends AbstractVerticle {
  final static Logger LOG = LoggerFactory.getLogger(RequestResponseExample.class);

  final public static String Address = "my.request.address";

  public static void main(String[] args) {
    var vertx = Vertx.vertx();

    LOG.debug("Starting now ");
    vertx.deployVerticle(new RequestVerticle());
    vertx.deployVerticle(new ResponseVerticle());
  }

   static class RequestVerticle extends AbstractVerticle {

     final static Logger LOG = LoggerFactory.getLogger(RequestVerticle.class);
     public void start(Promise<Void> startPromise) throws Exception {
       LOG.debug("Start");

       startPromise.complete();
       var message = "Hello world!";
       LOG.debug("Sending message: " + message);
       // do not do this in an eventloop
      var eventBus = vertx.eventBus();
      eventBus.<String>request(Address, "Hello World!",
        reply -> {LOG.debug("Response: " + reply.result().body());});


     }

  }

  static class ResponseVerticle extends AbstractVerticle {
    final static Logger LOG = LoggerFactory.getLogger(ResponseVerticle.class);

    public void start(Promise<Void> startPromise) throws Exception {
      LOG.debug("Start");

      startPromise.complete();
      var eventBus = vertx.eventBus();
      vertx.eventBus().<String>consumer(Address, message -> {
        LOG.debug("Got message: " + message.body());
        message.reply("Not today old man!");
      });

    }
  }



}
