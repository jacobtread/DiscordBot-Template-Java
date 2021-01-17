package org.slf4j.impl;

import org.slf4j.spi.MDCAdapter;

import java.util.Map;

/*
 *  THIS CLASS IS USED TO DISABLE THE DEFAULT JDA LOGGING
 *  IF YOU WANT JDA LOGGING REMOVE
 *  StaticMDCBinder.java and StaticLoggerBinder.java
 */
@SuppressWarnings("ALL")
public class StaticMDCBinder {

    public static final StaticMDCBinder SINGLETON = new StaticMDCBinder();

    private StaticMDCBinder() {
    }

    public static final StaticMDCBinder getSingleton() {
        return SINGLETON;
    }

    public MDCAdapter getMDCA() {
        return new MDCAdapter() {
            @Override
            public void put(String key, String val) {
            }

            @Override
            public String get(String key) {
                return null;
            }

            @Override
            public void remove(String key) {
            }

            @Override
            public void clear() {
            }

            @Override
            public Map<String, String> getCopyOfContextMap() {
                return null;
            }

            @Override
            public void setContextMap(Map<String, String> contextMap) {
            }
        };
    }

    public String getMDCAdapterClassStr() {
        return "";
    }

}
