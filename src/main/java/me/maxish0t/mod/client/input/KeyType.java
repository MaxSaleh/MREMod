package me.maxish0t.mod.client.input;

public enum  KeyType {
    AbilitiesMenu("Abilities Menu", 86), // V
    BlastMining("Blast Mining", 340); // LEFT SHIFT

    public String displayName;
    public int keyCode;

    KeyType(String displayName, int keyCode) {
        this.displayName = displayName;
        this.keyCode = keyCode;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
