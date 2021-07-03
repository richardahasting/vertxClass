package com.richardahasting.vertxclass.starter.json;

import com.richardahasting.vertxclass.starter.json.codec.LocalMessageCodec;
import io.vertx.core.*;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class PingPongExample extends AbstractVerticle {
  final public static String Address = "my.request.address";
  final static Logger LOG = LoggerFactory.getLogger(PingPongExample.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx();

    LOG.debug("Starting now ");
    vertx.deployVerticle(new PingVerticle(), logOnError());
    vertx.deployVerticle(new PongVerticle(), logOnError());
  }

  private static Handler<AsyncResult<String>> logOnError() {
    return ar-> {
      if (ar.failed())
        LOG.error("Error! ", ar.cause());
    };
  }

  static class PingVerticle extends AbstractVerticle {

    final static Logger LOG = LoggerFactory.getLogger(PingVerticle.class);

    Integer count = 0;
    boolean enabled = true;
    public void start(Promise<Void> startPromise) throws Exception {
      LOG.debug("Start");


      final Ping message = new Ping("Message " + count++, enabled = ! enabled);
      LOG.debug("Sending message: " + message);
      // do not do this in an eventloop
      var eventBus = vertx.eventBus();
      // register only ONCE
      eventBus.registerDefaultCodec(Ping.class, new LocalMessageCodec<>(Ping.class));
      eventBus.<Pong>request(Address, "Hello World!",
        reply -> {
          if (reply.failed())
            LOG.error("FAILED! ", reply.cause());
          else
            LOG.debug("Response: " + reply.result().body());
        });
      startPromise.complete();

    }

  }

  static class PongVerticle extends AbstractVerticle {
    final static Logger LOG = LoggerFactory.getLogger(PongVerticle.class);
    static int count = 0;

    public void start(Promise<Void> startPromise) throws Exception {
      LOG.debug("Start");
      var eventBus = vertx.eventBus();
      eventBus.registerDefaultCodec(Pong.class, new LocalMessageCodec<>(Pong.class));

      vertx.eventBus().<Ping>consumer(Address, message -> {
        LOG.debug("Got message: " + message.body());
        message.reply(new Pong(count++));
      });
      startPromise.complete();
    }
  }
}
