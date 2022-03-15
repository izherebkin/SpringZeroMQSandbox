package org.izherebkin.integration.zeromq;

@FunctionalInterface
public interface SubscriptionListener {

    void onReceive(String s);

}
