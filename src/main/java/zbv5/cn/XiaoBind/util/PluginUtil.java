package zbv5.cn.XiaoBind.util;

import zbv5.cn.XiaoBind.Main;
import zbv5.cn.XiaoBind.command.*;
import zbv5.cn.XiaoBind.lang.Lang;
import zbv5.cn.XiaoBind.listener.PlayerListener;
import zbv5.cn.XiaoBind.listener.WindowsListener;

public class PluginUtil
{
    public static void LoadPlugin()
    {
        PrintUtil.PrintConsole("&e======== &bXiaoBind &e> &d开始加载 &e========");
        FileUtil.LoadFile();
        Lang.LoadLang();
        BindUtil.load();
        PlayerListener.load();
        //注册命令
        Main.getInstance().getServer().getCommandMap().register("XiaoBind", new MainCommand(Main.getInstance()));
        Main.getInstance().getServer().getCommandMap().register("Bind", new BindCommand(Main.getInstance()));
        Main.getInstance().getServer().getCommandMap().register("BindOnEquip", new BindOnEquipCommand(Main.getInstance()));
        Main.getInstance().getServer().getCommandMap().register("BindOnPickup", new BindOnPickupCommand(Main.getInstance()));
        Main.getInstance().getServer().getCommandMap().register("BindOnUse", new BindOnUseCommand(Main.getInstance()));
        Main.getInstance().getServer().getCommandMap().register("UnBind", new UnBindCommand(Main.getInstance()));
        Main.getInstance().getServer().getPluginManager().registerEvents(new PlayerListener(), Main.getInstance());
        Main.getInstance().getServer().getPluginManager().registerEvents(new WindowsListener(), Main.getInstance());
        //定时任务启动
        if(PlayerListener.CheckOffHand)
        {
            TaskUtil.checkOffHand(PlayerListener.CheckOffHand_Delay);
            PrintUtil.PrintConsole("&a&l√ &a启动对于OffHand定时检测任务.");
        } else {
            PrintUtil.PrintConsole("&c&l× &4禁用对于OffHand定时检测任务.");
        }
        PrintUtil.PrintConsole("&e======== &bXiaoBind &e> &a加载成功 &e========");
    }

    public static void DisablePlugin()
    {
        PrintUtil.PrintConsole("&e======== &bXiaoBind &e> &d开始卸载 &e========");
        PrintUtil.PrintConsole("&e> 感谢您的使用,期待下次运行~");
        PrintUtil.PrintConsole("&e======== &bXiaoBind &e> &c卸载完成 &e========");
    }

    public static void reloadLoadPlugin()
    {
        PrintUtil.PrintConsole("&e======== &bXiaoBind &e> &d开始重载 &e========");
        if(PlayerListener.CheckOffHand)
        {
            Main.getInstance().getServer().getScheduler().cancelTask(Main.getInstance());
            PrintUtil.PrintConsole("&e> &7停止对于OffHand定时检测任务.");
        }
        FileUtil.LoadFile();
        Lang.LoadLang();
        BindUtil.load();
        PlayerListener.load();
        if(PlayerListener.CheckOffHand)
        {
            TaskUtil.checkOffHand(PlayerListener.CheckOffHand_Delay);
            PrintUtil.PrintConsole("&a&l√ &a启动对于OffHand定时检测任务.");
        } else {
            PrintUtil.PrintConsole("&c&l× &4禁用对于OffHand定时检测任务.");
        }
        PrintUtil.PrintConsole("&e======== &bXiaoBind &e> &a重载成功 &e========");
    }
}
