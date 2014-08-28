package edu.pge_gis.pge.sts.server;

public class TokenAttribute {

    private String name;
    private String nameSpace;
    private String value;

    public TokenAttribute() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TokenAttribute(String name, String nameSpace, String value) {
        super();
        this.name = name;
        this.nameSpace = nameSpace;
        this.value = value;
    }

    @Override
    public String toString() {
        return "TokenAttribute{" + "name=" + name + ", nameSpace=" + nameSpace + ", value=" + value + '}';
    }
    
    
}
