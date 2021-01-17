package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.Marker;

/*
 *  THIS CLASS IS USED TO DISABLE THE DEFAULT JDA LOGGING
 *  IF YOU WANT JDA LOGGING REMOVE
 *  StaticMDCBinder.java and StaticLoggerBinder.java
 */
@SuppressWarnings("ALL")
public class StaticLoggerBinder {


    private static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder();
    private static final String loggerFactoryClassStr = "";
    public static String REQUESTED_API_VERSION = "1.6.99";
    private final ILoggerFactory loggerFactory;

    private StaticLoggerBinder() {
        loggerFactory = name -> new Logger() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public boolean isTraceEnabled() {
                return false;
            }

            @Override
            public void trace(String msg) {

            }

            @Override
            public void trace(String format, Object arg) {

            }

            @Override
            public void trace(String format, Object arg1, Object arg2) {

            }

            @Override
            public void trace(String format, Object... arguments) {

            }

            @Override
            public void trace(String msg, Throwable t) {

            }

            @Override
            public boolean isTraceEnabled(Marker marker) {
                return false;
            }

            @Override
            public void trace(Marker marker, String msg) {

            }

            @Override
            public void trace(Marker marker, String format, Object arg) {

            }

            @Override
            public void trace(Marker marker, String format, Object arg1, Object arg2) {

            }

            @Override
            public void trace(Marker marker, String format, Object... argArray) {

            }

            @Override
            public void trace(Marker marker, String msg, Throwable t) {

            }

            @Override
            public boolean isDebugEnabled() {
                return false;
            }

            @Override
            public void debug(String msg) {

            }

            @Override
            public void debug(String format, Object arg) {

            }

            @Override
            public void debug(String format, Object arg1, Object arg2) {

            }

            @Override
            public void debug(String format, Object... arguments) {

            }

            @Override
            public void debug(String msg, Throwable t) {

            }

            @Override
            public boolean isDebugEnabled(Marker marker) {
                return false;
            }

            @Override
            public void debug(Marker marker, String msg) {

            }

            @Override
            public void debug(Marker marker, String format, Object arg) {

            }

            @Override
            public void debug(Marker marker, String format, Object arg1, Object arg2) {

            }

            @Override
            public void debug(Marker marker, String format, Object... arguments) {

            }

            @Override
            public void debug(Marker marker, String msg, Throwable t) {

            }

            @Override
            public boolean isInfoEnabled() {
                return false;
            }

            @Override
            public void info(String msg) {

            }

            @Override
            public void info(String format, Object arg) {

            }

            @Override
            public void info(String format, Object arg1, Object arg2) {

            }

            @Override
            public void info(String format, Object... arguments) {

            }

            @Override
            public void info(String msg, Throwable t) {

            }

            @Override
            public boolean isInfoEnabled(Marker marker) {
                return false;
            }

            @Override
            public void info(Marker marker, String msg) {

            }

            @Override
            public void info(Marker marker, String format, Object arg) {

            }

            @Override
            public void info(Marker marker, String format, Object arg1, Object arg2) {

            }

            @Override
            public void info(Marker marker, String format, Object... arguments) {

            }

            @Override
            public void info(Marker marker, String msg, Throwable t) {

            }

            @Override
            public boolean isWarnEnabled() {
                return false;
            }

            @Override
            public void warn(String msg) {

            }

            @Override
            public void warn(String format, Object arg) {

            }

            @Override
            public void warn(String format, Object... arguments) {

            }

            @Override
            public void warn(String format, Object arg1, Object arg2) {

            }

            @Override
            public void warn(String msg, Throwable t) {

            }

            @Override
            public boolean isWarnEnabled(Marker marker) {
                return false;
            }

            @Override
            public void warn(Marker marker, String msg) {

            }

            @Override
            public void warn(Marker marker, String format, Object arg) {

            }

            @Override
            public void warn(Marker marker, String format, Object arg1, Object arg2) {

            }

            @Override
            public void warn(Marker marker, String format, Object... arguments) {

            }

            @Override
            public void warn(Marker marker, String msg, Throwable t) {

            }

            @Override
            public boolean isErrorEnabled() {
                return false;
            }

            @Override
            public void error(String msg) {

            }

            @Override
            public void error(String format, Object arg) {

            }

            @Override
            public void error(String format, Object arg1, Object arg2) {

            }

            @Override
            public void error(String format, Object... arguments) {

            }

            @Override
            public void error(String msg, Throwable t) {

            }

            @Override
            public boolean isErrorEnabled(Marker marker) {
                return false;
            }

            @Override
            public void error(Marker marker, String msg) {

            }

            @Override
            public void error(Marker marker, String format, Object arg) {

            }

            @Override
            public void error(Marker marker, String format, Object arg1, Object arg2) {

            }

            @Override
            public void error(Marker marker, String format, Object... arguments) {

            }

            @Override
            public void error(Marker marker, String msg, Throwable t) {

            }
        };
    }

    public static final StaticLoggerBinder getSingleton() {
        return SINGLETON;
    }

    public ILoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    public String getLoggerFactoryClassStr() {
        return loggerFactoryClassStr;
    }
}
