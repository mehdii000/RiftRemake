package me.mehdidev.rift.commands;

import me.mehdidev.rift.Rift;
import me.mehdidev.rift.handlers.DebugHandler;

import java.util.ArrayList;
import java.util.List;

public class CommandRegistry {

    private List<AbstractCommand> commandList = new ArrayList<>();

    public List<AbstractCommand> getCommandList() {
        return commandList;
    }

    public void registerCommand(AbstractCommand command) {
        commandList.add(command);
    }

    public void handleMapping() {
        getCommandList().forEach(command -> {
            Rift.getRift().commandMap.register("", command);
            DebugHandler.log("Registered command: " + command.getClass().getAnnotation(BaseCommand.class).name());
        });
    }
}
