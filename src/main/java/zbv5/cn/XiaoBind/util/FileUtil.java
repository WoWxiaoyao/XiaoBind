package zbv5.cn.XiaoBind.util;

import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import zbv5.cn.XiaoBind.Main;

import java.io.File;

public class FileUtil
{
    public static Config lang;
    public static Config config;

    public static void LoadFile()
    {
        try
        {
            File Config_Yml = new File(Main.getInstance().getDataFolder(), "config.yml");
            if (!Config_Yml.exists())
            {
                Main.getInstance().saveResource("config.yml", false);
            }
            config = new Config(new File(Main.getInstance().getDataFolder() + "/config.yml"));


            File Lang_Yml = new File(Main.getInstance().getDataFolder(), "lang.yml");
            if (!Lang_Yml.exists())
            {
                Main.getInstance().saveResource("lang.yml", false);
            }
            lang = new Config(new File(Main.getInstance().getDataFolder() + "/lang.yml"));
            PrintUtil.PrintConsole("&a&l√ &a配置文件加载完成.");

        }
        catch (Exception e)
        {
            PrintUtil.PrintConsole("&c&l× &4加载配置文件出现问题,请检查服务器.");
            e.printStackTrace();
        }
    }
}
