package edu.pge_gis.pge.sts.server.mappingmodule;

import edu.pge_gis.pge.sts.server.STSMappingModule;
import edu.pge_gis.pge.sts.server.TokenAttribute;
import edu.pge_gis.pge.sts.server.WSTrustRequestContext;

public class MappingModuleEdad implements STSMappingModule {

    @Override
    public void process(WSTrustRequestContext r) {

        //System.out.println("MAPPING MODULE");

        //creo atributo de edad
        String principalName = r.getPrincipalName();
        int edad = 0;

        if (principalName != null && principalName.equals("admin")) {
            edad = 21;
        }
        TokenAttribute a = new TokenAttribute("Edad", "urn:mins", Integer.toString(edad));
        r.getTokenAttributes().add(a);


        TokenAttribute a2 = new TokenAttribute("Telefono", "urn:mins", "1234 5678");
        r.getTokenAttributes().add(a2);

    }
}
