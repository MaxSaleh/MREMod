package me.maxish0t.mod.client.input;

import me.maxish0t.mod.utilities.ModReference;
import net.minecraft.client.KeyMapping;

public class KeyEntry {
    public KeyType keyType;
    public KeyMapping keyBinding;

    public KeyEntry(KeyType keyType) {
        this.keyType = keyType;
        this.keyBinding = new KeyMapping(keyType.displayName, keyType.keyCode, ModReference.MODNAME);
    }
}
