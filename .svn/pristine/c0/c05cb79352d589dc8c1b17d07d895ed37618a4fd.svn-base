/**
 * HelloWorldServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.moodykettle.webservice.helloworld;

public class HelloWorldServiceLocator extends org.apache.axis.client.Service implements com.moodykettle.webservice.helloworld.HelloWorldService {

    public HelloWorldServiceLocator() {
    }


    public HelloWorldServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public HelloWorldServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for HelloWorld
    private java.lang.String HelloWorld_address = "http://localhost:8080/WebService/services/HelloWorld";

    public java.lang.String getHelloWorldAddress() {
        return HelloWorld_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String HelloWorldWSDDServiceName = "HelloWorld";

    public java.lang.String getHelloWorldWSDDServiceName() {
        return HelloWorldWSDDServiceName;
    }

    public void setHelloWorldWSDDServiceName(java.lang.String name) {
        HelloWorldWSDDServiceName = name;
    }

    public com.moodykettle.webservice.helloworld.HelloWorld getHelloWorld() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(HelloWorld_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getHelloWorld(endpoint);
    }

    public com.moodykettle.webservice.helloworld.HelloWorld getHelloWorld(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.moodykettle.webservice.helloworld.HelloWorldSoapBindingStub _stub = new com.moodykettle.webservice.helloworld.HelloWorldSoapBindingStub(portAddress, this);
            _stub.setPortName(getHelloWorldWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setHelloWorldEndpointAddress(java.lang.String address) {
        HelloWorld_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.moodykettle.webservice.helloworld.HelloWorld.class.isAssignableFrom(serviceEndpointInterface)) {
                com.moodykettle.webservice.helloworld.HelloWorldSoapBindingStub _stub = new com.moodykettle.webservice.helloworld.HelloWorldSoapBindingStub(new java.net.URL(HelloWorld_address), this);
                _stub.setPortName(getHelloWorldWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("HelloWorld".equals(inputPortName)) {
            return getHelloWorld();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://helloworld.webservice.moodykettle.com", "HelloWorldService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://helloworld.webservice.moodykettle.com", "HelloWorld"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("HelloWorld".equals(portName)) {
            setHelloWorldEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
