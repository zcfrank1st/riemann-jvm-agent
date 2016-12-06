package com.chaos.riemann.jvm.agent;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.riemann.riemann.client.RiemannClient;

import java.lang.instrument.Instrumentation;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created by zcfrank1st on 06/12/2016.
 */
public class Agent {
    private static Config conf = ConfigFactory.load();
    private static String serviceName = Optional.of(System.getProperty("service.name")).orElse("none");

    public static void premain(String args, Instrumentation inst) {
        RiemannClient c = null;
        int timeInterval = conf.getInt("riemann.interval.time");
        try {
            c = RiemannClient.tcp(conf.getString("riemann.server.host"), 5555);
            c.connect();


            c.event()
                    .service(serviceName + "-jvm-heap-memory")
                    .state("ok")
                    .metric(JVM.getUsedHeapMemory())
                    .send()
                    .deref(timeInterval, TimeUnit.SECONDS);

            c.event()
                    .service(serviceName + "-non-jvm-heap-memory")
                    .state("ok")
                    .metric(JVM.getNonHeapUsedMemory())
                    .send()
                    .deref(timeInterval, TimeUnit.SECONDS);

            c.event()
                    .service(serviceName + "-thread-count")
                    .state("ok")
                    .metric(JVM.getThreadCount())
                    .send()
                    .deref(timeInterval, TimeUnit.SECONDS);

        } catch (Exception e) {
            if (c != null) {
                c.close();
            }
        }
    }
}
