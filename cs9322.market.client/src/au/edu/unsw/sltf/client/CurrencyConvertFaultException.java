
/**
 * CurrencyConvertFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package au.edu.unsw.sltf.client;

public class CurrencyConvertFaultException extends java.lang.Exception{

    private static final long serialVersionUID = 1414316870777L;
    
    private au.edu.unsw.sltf.client.CurrencyConvertServiceStub.CurrencyConvertFault faultMessage;

    
        public CurrencyConvertFaultException() {
            super("CurrencyConvertFaultException");
        }

        public CurrencyConvertFaultException(java.lang.String s) {
           super(s);
        }

        public CurrencyConvertFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public CurrencyConvertFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(au.edu.unsw.sltf.client.CurrencyConvertServiceStub.CurrencyConvertFault msg){
       faultMessage = msg;
    }
    
    public au.edu.unsw.sltf.client.CurrencyConvertServiceStub.CurrencyConvertFault getFaultMessage(){
       return faultMessage;
    }
}
    