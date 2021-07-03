package com.richardahasting.vertxclass.starter;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(VertxExtension.class)
public class FuturePromiseExample {
  final static Logger LOG = LoggerFactory.getLogger(FuturePromiseExample.class);

  @Test
  void promise_success(Vertx vertx, VertxTestContext context)
  {
    final Promise<String> promise = Promise.promise();
    LOG.debug("Start");
    vertx.setTimer(500, id -> {
      promise.complete("Success");
      LOG.debug("Success");
      context.completeNow();
    });
    LOG.debug("Ending");
  }

  @Test
  void promise_failure(Vertx vertx, VertxTestContext context)
  {
    final Promise<String> promise = Promise.promise();
    LOG.debug("Start");
    vertx.setTimer(500, id -> {
      promise.complete("Failed");
      LOG.debug("Failure!");
      context.completeNow();
    });
    LOG.debug("Ending");
  }


  @Test
  void future_success(Vertx vertx, VertxTestContext context)
  {
    {
      final Promise<String> promise = Promise.promise();
      LOG.debug("Start");
      vertx.setTimer(500, id -> {
        promise.complete("Success");
        LOG.debug("Success");
        context.completeNow();
      });
      final Future<String> future = promise.future();

      future.onSuccess(result -> {
        LOG.debug("Result: " + result);
        context.completeNow();
      }).onFailure(context::failNow);

      LOG.debug("Ending");
    }
  }


  @Test
  void future_map(Vertx vertx, VertxTestContext context)
  {
    {
      final Promise<String> promise = Promise.promise();
      LOG.debug("Start");
      vertx.setTimer(500, id -> {
        promise.complete("Success");
        LOG.debug("Success");
        context.completeNow();
      });
      final Future<String> future = promise.future();

      future.map(s -> {
        LOG.debug("Map String to JSon object: " + s);
        return new JsonObject().put("Key", s); }
      ).map (jsonObject -> new JsonArray().add(jsonObject)).
        onSuccess(result -> {
        LOG.debug("Result: " + result);
        context.completeNow();
      }).onFailure(context::failNow);

      LOG.debug("Ending");
    }
  }


  @Test
  void future_coordination(Vertx vertx, VertxTestContext context)
  {
     vertx.createHttpServer().requestHandler(request -> {LOG.debug("Request: " + request);}).listen(10000).
        compose(server -> {
          LOG.info("Another task!");
          return Future.succeededFuture(server);
          }).
      compose(server -> {
        LOG.info("Even more tasks!");
        return Future.succeededFuture(server);
      }).
        onFailure(context::failNow).
      onSuccess(server -> {
        LOG.debug("Server started on port: " + server.actualPort());
        context.completeNow();
      });
  }


  @Test
  void future_composition(Vertx vertx, VertxTestContext context)
  {
    var one = Promise.<Void>promise();
    var two  = Promise.<Void>promise();
    var three = Promise.<Void>promise();
    var future1 = one.future();
    var future2 = two.future();
    var future3 = three.future();

    CompositeFuture.any(future1, future2, future3).onFailure(context::failNow)
      .onSuccess(result -> {
        LOG.debug("Success");
        context.completeNow();
      });
    // complete futures
    vertx.setTimer(540 , id -> {
      one.complete();
      two.complete();
      three.fail("three");
    });
  }
}
