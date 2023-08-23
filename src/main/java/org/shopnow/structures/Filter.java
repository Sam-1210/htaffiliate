package org.shopnow.structures;

public class Filter {
    private String fieldname;
    private String type;
    private String value;
    private String xpathAttribute;
    private String matchMethod;

    public Filter(String fieldName, String type, String value, String xpathAttribute, String matchMethod) {
        this.type = type;
        this.fieldname = fieldName;
        this.value = value;
        this.xpathAttribute = xpathAttribute;
        this.matchMethod = matchMethod;
    }

    public String getFieldname() {
        return fieldname;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getMatchMethod() {
        return matchMethod;
    }

    public String getXpathAttribute() {
        return xpathAttribute;
    }

    @Override
    public boolean equals(Object matchToValue) {
        if(matchToValue == null) return false;
        /*if(matchToValue instanceof String) {
            String strVal = (String) matchToValue;
            if(matchMethod.equals("exact")) {
                return value.equals(strVal);
            } else if(matchMethod.equals("partial")) {
                return value.contains(strVal) || strVal.contains(value);
            }
        } else */if(matchToValue instanceof Filter) {
            Filter another = (Filter) matchToValue;
            return fieldname.equals(another.fieldname)
                    &&  type.equals(another.type)
                    && value.equals(another.value)
                    && matchMethod.equals(another.matchMethod)
                    && xpathAttribute.equals(another.xpathAttribute);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("{fieldname: %s, type: %s, value: %s, matchMethod: %s, atrribute: %s}", fieldname, type, value, matchMethod, xpathAttribute);
    }
}
