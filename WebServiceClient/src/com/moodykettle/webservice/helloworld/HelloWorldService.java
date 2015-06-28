/**
 * HelloWorldService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.moodykettle.webservice.helloworld;

public interface HelloWorldService extends javax.xml.rpc.Service {
    public java.lang.String getHelloWorldAddress();

    public com.moodykettle.webservice.helloworld.HelloWorld getHelloWorld() throws javax.xml.rpc.ServiceException;

    public com.moodykettle.webservice.helloworld.HelloWorld getHelloWorld(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
