package me.mehdidev.rift.guis;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface GuiParams {
    String id();
    int height() default 1;
}
