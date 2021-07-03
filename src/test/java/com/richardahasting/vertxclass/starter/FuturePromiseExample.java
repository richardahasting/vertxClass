package com.richardahasting.vertxclass.starter;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
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
}
