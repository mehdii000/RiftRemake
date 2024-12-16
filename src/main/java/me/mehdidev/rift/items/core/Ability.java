package me.mehdidev.rift.items.core;

import me.mehdidev.rift.handlers.User;

public interface Ability
{
    String getAbilityName();
    String getAbilityDescription();
    default void onAbilityUse(User user, SItem sItem) {}
    default int getAbilityCooldownTicks() { return 0; }
    default int getManaCost() { return 0; };
    default AbilityActivation getAbilityActivation()
    {
        return AbilityActivation.RIGHT_CLICK;
    }
}