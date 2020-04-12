package zbv5.cn.XiaoBind.windows;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import zbv5.cn.XiaoBind.lang.Lang;
import zbv5.cn.XiaoBind.util.DataUtil;
import zbv5.cn.XiaoBind.util.FileUtil;
import zbv5.cn.XiaoBind.util.ItemUtil;
import zbv5.cn.XiaoBind.util.PrintUtil;

import java.util.ArrayList;
import java.util.List;

public class ClaimWindow
{
    public static FormWindowSimple getClaimWindow(Player p)
    {
        FormWindowSimple ui = null;
        List<String> list = DataUtil.getNameList(p);
        if(list.isEmpty())
        {
            ui = new FormWindowSimple(PrintUtil.cc("&4&l绑定丢失物品找回"),PrintUtil.cc(buildInfo(p,true)));
        } else {
            ui = new FormWindowSimple(PrintUtil.cc("&4&l绑定丢失物品找回"),PrintUtil.cc(buildInfo(p,false).replace("{amount}",String.valueOf(list.size()))));

            ui.addButton(new ElementButton(PrintUtil.cc("&8&l一键找回")));
        }
        ui.addButton(new ElementButton(PrintUtil.cc("&4&l关闭")));
        return ui;
    }

    public static String buildInfo(Player p,Boolean isEmpty)
    {
        List<String> list = new ArrayList<String>();
        if(isEmpty)
        {
            list = Lang.ClaimShow_noItems;
        } else {
            list = Lang.ClaimShow_hasItems;
        }
        String info = ItemUtil.buildLore(list);
        if(info == null) return "";
        return info;
    }
}
