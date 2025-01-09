package me.mehdidev.rift.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BaseCommand {
    String name();
    String description();
    String[] aliases() default {};
    int getMaxArguments() default 0;
}
