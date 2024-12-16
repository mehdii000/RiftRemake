package me.mehdidev.rift.handlers;

import me.mehdidev.rift.Rift;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Config extends YamlConfiguration
{
    private final Rift plugin;
    private final File file;

    public Config(File parent, String name)
    {
        this.plugin = Rift.getRift();
        this.file = new File(parent, name);

        if (!file.exists())
        {
            options().copyDefaults(true);
            plugin.saveResource(name, false);
        }
        load();
    }

    public Config(String name)
    {
        this(Rift.getRift().getDataFolder(), name);
    }

    public void load()
    {
        try
        {
            super.load(file);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void save()
    {
        try
        {
            super.save(file);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}