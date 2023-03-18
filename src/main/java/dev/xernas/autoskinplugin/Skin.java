package dev.xernas.autoskinplugin;

public class Skin {

    private final String name;
    private final String value;
    private final String signature;

    public Skin(String name, String value, String signature) {
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }

    public String getName() {
        return name;
    }
}
