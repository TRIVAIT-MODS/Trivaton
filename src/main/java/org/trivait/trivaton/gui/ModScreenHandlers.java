package org.trivait.trivaton.gui;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;
import org.trivait.trivaton.Trivaton;
import org.trivait.trivaton.gui.custom.SieveScreenHandler;

public class ModScreenHandlers {
    public static final ScreenHandlerType<SieveScreenHandler> SIEVE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Trivaton.id("sieve_screen_handler"),
                    new ExtendedScreenHandlerType<>(SieveScreenHandler::new, BlockPos.PACKET_CODEC));

    public static void register() {

    }
}
