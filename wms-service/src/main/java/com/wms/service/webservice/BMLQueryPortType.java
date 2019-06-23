/**
 * BMLQueryPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wms.service.webservice;

public interface BMLQueryPortType extends java.rmi.Remote {
    public java.lang.String rejectOrderRX(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.String cancelOrderRX(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.String cancelLdlRX(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public java.lang.String custToWms(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.String ldlToWms(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.String ansToWms(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.String skuToWms(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.String soToWms(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.String cancelAsnRX(java.lang.String in0) throws java.rmi.RemoteException;
}
