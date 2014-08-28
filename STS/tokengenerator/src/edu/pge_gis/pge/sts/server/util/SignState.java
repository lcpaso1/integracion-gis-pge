/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pge_gis.pge.sts.server.util;

import java.security.Principal;

public class SignState {

    private final boolean valid;
    private final Principal  principal;

    public SignState(boolean valid, Principal principal) {
        this.valid = valid;
        this.principal = principal;
    }

    public boolean isValid() {
        return valid;
    }

    public Principal getPrincipal() {
        return principal;
    }

    @Override
    public String toString() {
        return "SignState{" + "valid=" + valid + ", principal=" + principal + '}';
    }

    
}
