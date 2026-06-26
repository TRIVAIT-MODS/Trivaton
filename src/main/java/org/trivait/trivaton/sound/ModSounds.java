package org.trivait.trivaton.sound;

import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import org.trivait.trivaton.Trivaton;

public class ModSounds {
    public static final SoundEvent TRIVATON = registerSoundEvent("trivaton");
    public static final RegistryKey<JukeboxSong> TRIVATON_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Trivaton.id("trivaton"));

    private static SoundEvent registerSoundEvent(String name) {
        return Registry.register(Registries.SOUND_EVENT, Trivaton.id(name), SoundEvent.of(Trivaton.id(name)));
    }

    public static void register() {

    }
}
