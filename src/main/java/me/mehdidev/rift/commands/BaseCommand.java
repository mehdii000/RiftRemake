package me.mehdidev.rift.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
public @interface BaseCommand {
    String name();
    String description();
    String[] aliases() default {};
}
