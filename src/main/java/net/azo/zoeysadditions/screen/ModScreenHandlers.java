package net.azo.zoeysadditions.screen;

import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers{
    public static ScreenHandlerType<SoyaSeparatorScreenHandler> SOYA_SEPARATOR_SCREEN_HANDLER;

    public static void registerAllScreenHandlers(){
        SOYA_SEPARATOR_SCREEN_HANDLER = new ScreenHandlerType<>(SoyaSeparatorScreenHandler::new);
    }

}
