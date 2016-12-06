package com.chaos.riemann.jvm.agent;

import java.lang.management.ManagementFactory;

/**
 * Created by zcfrank1st on 06/12/2016.
 */
public class JVM {
    public static long getUsedHeapMemory () {
        return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getCommitted();
    }

    public static long getNonHeapUsedMemory () {
        return ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getCommitted();
    }

    public static long getThreadCount () {
        return ManagementFactory.getThreadMXBean().getThreadCount();
    }
}
