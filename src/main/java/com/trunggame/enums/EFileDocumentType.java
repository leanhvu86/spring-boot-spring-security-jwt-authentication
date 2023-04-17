package com.trunggame.enums;

public enum EFileDocumentType {
    NORMAL,
    BANNER,
    THUMBNAIL;

    static public boolean isValid(String type) {
        for (EFileDocumentType myVar : EFileDocumentType.values()) {
            if(myVar.toString().equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }


}
