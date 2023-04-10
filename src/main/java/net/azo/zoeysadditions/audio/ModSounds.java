package net.azo.zoeysadditions.audio;

import net.azo.zoeysadditions.ZoeysAdditions;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class ModSounds {
    public static SoundEvent SOYA_SEPARATOR_AMBIENT = registerSoundEvent
            ("soya_separator_ambient");

    public static SoundEvent SOYA_SEPARATOR_FINISHES = registerSoundEvent
            ("soya_separator_finishes");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(ZoeysAdditions.MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent((id)));
    }

    public static void registerModSounds() {
        ZoeysAdditions.LOGGER.debug("Registering ModSounds for " + ZoeysAdditions.MOD_ID);
    }
}
