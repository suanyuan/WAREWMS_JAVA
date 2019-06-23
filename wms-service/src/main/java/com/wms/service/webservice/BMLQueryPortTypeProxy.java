package com.wms.service.webservice;

public class BMLQueryPortTypeProxy implements com.wms.service.webservice.BMLQueryPortType {
  private String _endpoint = null;
  private com.wms.service.webservice.BMLQueryPortType bMLQueryPortType = null;
  
  public BMLQueryPortTypeProxy() {
    _initBMLQueryPortTypeProxy();
  }
  
  public BMLQueryPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initBMLQueryPortTypeProxy();
  }
  
  private void _initBMLQueryPortTypeProxy() {
    try {
      bMLQueryPortType = (new com.wms.service.webservice.BMLQueryLocator()).getBMLQueryHttpPort();
      if (bMLQueryPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)bMLQueryPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)bMLQueryPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (bMLQueryPortType != null)
      ((javax.xml.rpc.Stub)bMLQueryPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.wms.service.webservice.BMLQueryPortType getBMLQueryPortType() {
    if (bMLQueryPortType == null)
      _initBMLQueryPortTypeProxy();
    return bMLQueryPortType;
  }
  
  public java.lang.String rejectOrderRX(java.lang.String in0) throws java.rmi.RemoteException{
    if (bMLQueryPortType == null)
      _initBMLQueryPortTypeProxy();
    return bMLQueryPortType.rejectOrderRX(in0);
  }
  
  public java.lang.String cancelOrderRX(java.lang.String in0) throws java.rmi.RemoteException{
    if (bMLQueryPortType == null)
      _initBMLQueryPortTypeProxy();
    return bMLQueryPortType.cancelOrderRX(in0);
  }
  
  public java.lang.String cancelLdlRX(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException{
    if (bMLQueryPortType == null)
      _initBMLQueryPortTypeProxy();
    return bMLQueryPortType.cancelLdlRX(in0, in1, in2);
  }
  
  public java.lang.String custToWms(java.lang.String in0) throws java.rmi.RemoteException{
    if (bMLQueryPortType == null)
      _initBMLQueryPortTypeProxy();
    return bMLQueryPortType.custToWms(in0);
  }
  
  public java.lang.String ldlToWms(java.lang.String in0) throws java.rmi.RemoteException{
    if (bMLQueryPortType == null)
      _initBMLQueryPortTypeProxy();
    return bMLQueryPortType.ldlToWms(in0);
  }
  
  public java.lang.String ansToWms(java.lang.String in0) throws java.rmi.RemoteException{
    if (bMLQueryPortType == null)
      _initBMLQueryPortTypeProxy();
    return bMLQueryPortType.ansToWms(in0);
  }
  
  public java.lang.String skuToWms(java.lang.String in0) throws java.rmi.RemoteException{
    if (bMLQueryPortType == null)
      _initBMLQueryPortTypeProxy();
    return bMLQueryPortType.skuToWms(in0);
  }
  
  public java.lang.String soToWms(java.lang.String in0) throws java.rmi.RemoteException{
    if (bMLQueryPortType == null)
      _initBMLQueryPortTypeProxy();
    return bMLQueryPortType.soToWms(in0);
  }
  
  public java.lang.String cancelAsnRX(java.lang.String in0) throws java.rmi.RemoteException{
    if (bMLQueryPortType == null)
      _initBMLQueryPortTypeProxy();
    return bMLQueryPortType.cancelAsnRX(in0);
  }
  
  
}