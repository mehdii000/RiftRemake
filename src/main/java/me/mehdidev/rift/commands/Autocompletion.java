package me.mehdidev.rift.commands;

import java.util.List;
import java.util.Map;

public interface Autocompletion {
    default Map<Integer, List<String>> mapArgumentToCompletion() {
        return null;
    }
}
