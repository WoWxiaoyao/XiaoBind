package zbv5.cn.XiaoBind.lang;


import zbv5.cn.XiaoBind.util.FileUtil;
import zbv5.cn.XiaoBind.util.PrintUtil;

import java.util.ArrayList;
import java.util.List;

public class Lang
{
    public static String Prefix = "&6[&bXiaoBind&6]";
    public static String NoPermission = "{prefix}&c你没有权限这样做";
    public static String SuccessReload = "{prefix}&a重载完成!";
    public static String FailReload = "{prefix}&c重载失败!请前往控制台查看报错.";
    public static String Executed = "{prefix}&a执行成功";
    public static String NeedPlayer = "{prefix}&c只有玩家才能执行该操作.";
    public static String NullPlayer = "{prefix}&c玩家{player}不在线或不存在.";
    public static String NullCommand = "{prefix}&c未知指令.";
    public static String Drop = "{prefix}&c你不能丢弃绑定物品.";
    public static String Bind_AlreadyBind = "{prefix}&c绑定失败,该物品已经绑定了.";
    public static String Bind_AlreadyTag = "{prefix}&c添加Tag失败,该物品已经存在相同的Tag.&7({tag}&7)";
    public static String Bind_FailNullItem = "{prefix}&c绑定失败,玩家需要手持一个有效物品.";
    public static String Bind_SuccessPickUp = "{prefix}&a捡起的物品已绑定.";
    public static String Bind_SuccessUse = "{prefix}&a使用的物品已绑定.";
    public static String Bind_SuccessEquip = "{prefix}&a点击/穿戴的物品已绑定.";
    public static String Bind_NoSelf_Held = "{prefix}&c你所手持的物品不是你的.";
    public static String Bind_NoSelf_Inv = "{prefix}&c你所点击的物品不是你的.";
    public static String Bind_NoSelf_PickUP = "{prefix}&c你正在捡取的物品不是你的.";

    public static String unBind_Success = "{prefix}&a解绑成功.";
    public static String unBind_FailNullItem = "{prefix}&c解绑失败,玩家需要手持一个有效物品.";
    public static String unBind_FailNoBind = "{prefix}&c解绑失败,该物品未绑定.";

    public static String KeepOnDeath = "{prefix}&a已恢复绑定物品.";
    public static String KeepOnDeath_Full = "{prefix}&c背包已满,剩余物品请手动领取.";
    public static String PickUp_Full = "{prefix}&c背包已满,剩余物品请手动领取.";
    public static List<String> ClaimShow_noItems = new ArrayList<String>();
    public static List<String> ClaimShow_hasItems = new ArrayList<String>();
    public static String Receive_Full = "{prefix}&c领取中断,请清理背包后继续领取.";
    public static String Receive_Success = "{prefix}&a领取完成,本次共取出{amount}件物品.";
    public static void LoadLang()
    {
        try
        {
            Prefix = FileUtil.lang.getString("Prefix");
            NoPermission = FileUtil.lang.getString("NoPermission");
            SuccessReload = FileUtil.lang.getString("SuccessReload");
            FailReload = FileUtil.lang.getString("FailReload");
            Executed = FileUtil.lang.getString("Executed");
            NeedPlayer = FileUtil.lang.getString("NeedPlayer");
            NullPlayer = FileUtil.lang.getString("NullPlayer");
            NullCommand = FileUtil.lang.getString("NullCommand");
            Drop = FileUtil.lang.getString("Drop");
            Bind_AlreadyBind = FileUtil.lang.getString("Bind_AlreadyBind");
            Bind_AlreadyTag = FileUtil.lang.getString("Bind_AlreadyTag");
            Bind_FailNullItem = FileUtil.lang.getString("Bind_FailNullItem");
            Bind_SuccessPickUp = FileUtil.lang.getString("Bind_SuccessPickUp");
            Bind_SuccessUse = FileUtil.lang.getString("Bind_SuccessUse");
            Bind_SuccessEquip = FileUtil.lang.getString("Bind_SuccessEquip");
            Bind_NoSelf_Held = FileUtil.lang.getString("Bind_NoSelf_Held");
            Bind_NoSelf_Inv = FileUtil.lang.getString("Bind_NoSelf_Inv");
            Bind_NoSelf_PickUP = FileUtil.lang.getString("Bind_NoSelf_PickUP");
            unBind_Success = FileUtil.lang.getString("unBind_Success");
            unBind_FailNullItem = FileUtil.lang.getString("unBind_FailNullItem");
            unBind_FailNoBind= FileUtil.lang.getString("unBind_FailNoBind");
            KeepOnDeath = FileUtil.lang.getString("KeepOnDeath");
            KeepOnDeath_Full  = FileUtil.lang.getString("KeepOnDeath_Full");
            PickUp_Full  = FileUtil.lang.getString("PickUp_Full");
            ClaimShow_noItems = FileUtil.lang.getStringList("ClaimShow_noItems");
            ClaimShow_hasItems = FileUtil.lang.getStringList("ClaimShow_hasItems");
            Receive_Full  = FileUtil.lang.getString("Receive_Full");
            Receive_Success = FileUtil.lang.getString("Receive_Success");
            PrintUtil.PrintConsole("&a&l√ &a语言文件加载完成.");
        }
        catch (Exception e)
        {
            PrintUtil.PrintConsole("&c&l× &4读取语言文件出现问题,请检查服务器.");
            e.printStackTrace();
        }
    }
}
