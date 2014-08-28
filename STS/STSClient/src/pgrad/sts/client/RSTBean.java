/**
 * 
 */
//package uy.gub.agesic.beans;
package pgrad.sts.client;

import org.w3c.dom.Element;

/**
 * @author Guzman Llambias
 * cambio hecho por emilio: le agregue el elemento base para el pedido al sts de pge
 *
 */
@Deprecated
public class RSTBean {

	private String issuer;
	private String role;
	private String policyName;
	private String userName;
	private String service;
	private Element base;
	
	/**
	 * @return the service
	 */
	public String getService() {
		return service;
	}
	/**
	 * @param service the service to set
	 */
	public void setService(String service) {
		this.service = service;
	}
	/**
	 * @return the issuer
	 */
	public String getIssuer() {
		return issuer;
	}
	/**
	 * @param issuer the issuer to set
	 */
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * @return the policyName
	 */
	public String getPolicyName() {
		return policyName;
	}
	/**
	 * @param policyName the policyName to set
	 */
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setBase(Element base) {
		this.base = base;
	}
	public Element getBase() {
		return base;
	}

}
