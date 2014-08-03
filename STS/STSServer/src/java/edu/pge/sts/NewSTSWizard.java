/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pge.sts;

import javax.annotation.Resource;
import javax.xml.transform.Source;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.handler.MessageContext;

/**
 *
 * @author Alejandro
 */
@WebServiceProvider(serviceName = "NewSTSWizard", portName = "INewSTSWizard_Port", targetNamespace = "http://tempuri.org/", wsdlLocation = "WEB-INF/wsdl/NewSTSWizard/NewSTSWizard.wsdl")
@ServiceMode(value = Service.Mode.PAYLOAD)
public class NewSTSWizard extends com.sun.xml.ws.security.trust.sts.BaseSTSImpl implements Provider<Source> {
    @Resource
    WebServiceContext context;

    public Source invoke(Source rstElement) {
        System.out.println("+++++++++JAVA HOME "+System.getProperty("java.home"));
        return super.invoke(rstElement);
    }

    protected MessageContext getMessageContext() {
        System.out.println("+++++++++JAVA HOME "+System.getProperty("java.home"));
        MessageContext msgCtx = context.getMessageContext();
        return msgCtx;
    }
    
}
