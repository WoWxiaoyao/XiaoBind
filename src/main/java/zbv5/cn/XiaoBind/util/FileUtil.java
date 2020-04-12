package zbv5.cn.XiaoBind.util;

import cn.nukkit.utils.Config;
import zbv5.cn.XiaoBind.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil
{
    public static Config lang;
    public static Config config;
    public static Config data;

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

            File Data_Yml = new File(Main.getInstance().getDataFolder(), "data.yml");
            if (!Data_Yml.exists())
            {
                Main.getInstance().saveResource("data.yml", false);
            }
            data = new Config(new File(Main.getInstance().getDataFolder() + "/data.yml"));
            //v1.1.0 检测 √
            Update();

            PrintUtil.PrintConsole("&a&l√ &a配置文件加载完成.");

        }
        catch (Exception e)
        {
            PrintUtil.PrintConsole("&c&l× &4加载配置文件出现问题,请检查服务器.");
            e.printStackTrace();
        }
    }

    public static void Update()
    {
        String Config_version = config.getString("version");
        String Lang_version = lang.getString("version");

        if(Config_version.equals("1.0.0"))
        {
            config.set("Setting.KeepProtect",true);
            config.set("Setting.DropProtect",true);
            config.set("Setting.PickUpProtect",true);
            config.set("Setting.NoSelfSend",true);
            config.set("version","1.1.0");
            config.save();
            PrintUtil.PrintConsole("&a&l√ &a配置文件升级至 1.1.0");
        }
        if(Lang_version.equals("1.0.0"))
        {
            lang.set("KeepOnDeath_Full","{prefix}&c背包已满,剩余物品请手动领取.");
            lang.set("PickUp_Full","{prefix}&c背包已满,剩余物品请手动领取.");

            List<String> list_1 = new ArrayList<String>();
            list_1.add("&f你没有可以找回的绑定物品.");

            lang.set("ClaimShow_noItems",list_1);

            List<String> list_2 = new ArrayList<String>();
            list_2.add("&f你有&a{amount}件&f可找回的物品");

            lang.set("ClaimShow_hasItems",list_2);
            lang.set("Receive_Full","{prefix}&c领取中断,请清理背包后继续领取.");
            lang.set("Receive_Success","{prefix}&a领取完成,本次共取出{amount}件物品.");
            lang.set("version","1.1.0");
            lang.save();
            PrintUtil.PrintConsole("&a&l√ &a语言文件升级至 1.1.0");
        }
    }
}
