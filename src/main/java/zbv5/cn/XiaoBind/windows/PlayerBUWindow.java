package zbv5.cn.XiaoBind.windows;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import zbv5.cn.XiaoBind.util.PrintUtil;

public class PlayerBUWindow
{
    public static FormWindowSimple getPlayerBUWindow()
    {
        FormWindowSimple ui = new FormWindowSimple(PrintUtil.cc("&4&lXiaoBind - 逍式绑定&c&l玩家绑定/解绑"),PrintUtil.cc("&f请手持物品点击,并确保所需道具充足."));
        ui.addButton(new ElementButton(PrintUtil.cc("绑定")));
        ui.addButton(new ElementButton(PrintUtil.cc("解绑")));
        ui.addButton(new ElementButton(PrintUtil.cc("&4&l关闭")));
        return ui;
    }
}
