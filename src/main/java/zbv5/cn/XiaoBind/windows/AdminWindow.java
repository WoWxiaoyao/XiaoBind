package zbv5.cn.XiaoBind.windows;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import zbv5.cn.XiaoBind.Main;
import zbv5.cn.XiaoBind.util.BindUtil;
import zbv5.cn.XiaoBind.util.ItemUtil;
import zbv5.cn.XiaoBind.util.PrintUtil;

import java.util.ArrayList;
import java.util.List;

public class AdminWindow
{
    public static FormWindowSimple getAdminWindow()
    {
        FormWindowSimple ui = new FormWindowSimple(PrintUtil.cc("&4&lXiaoBind - 逍式绑定"),PrintUtil.cc("&e插件版本:&a"+ Main.getInstance().getDescription().getVersion()));
        ui.addButton(new ElementButton(PrintUtil.cc("&c重载配置文件")));
        ui.addButton(new ElementButton(PrintUtil.cc("查询Tag")));
        ui.addButton(new ElementButton(PrintUtil.cc("添加Tag")));
        ui.addButton(new ElementButton(PrintUtil.cc("绑定/解绑")));
        ui.addButton(new ElementButton(PrintUtil.cc("绑定丢失找回")));
        ui.addButton(new ElementButton(PrintUtil.cc("&4&l关闭")));
        return ui;
    }

    public static FormWindowSimple getSetBindOrUnbindWindow()
    {
        FormWindowSimple ui = new FormWindowSimple(PrintUtil.cc("&4&lXiaoBind - 逍式绑定&c&l绑定/解绑"),PrintUtil.cc("&f请手持物品点击."));
        ui.addButton(new ElementButton(PrintUtil.cc("绑定")));
        ui.addButton(new ElementButton(PrintUtil.cc("解绑")));
        ui.addButton(new ElementButton(PrintUtil.cc("&4返回首页")));
        ui.addButton(new ElementButton(PrintUtil.cc("&4&l关闭")));
        return ui;
    }
    public static FormWindowSimple getSetTagsWindow()
    {
        FormWindowSimple ui = new FormWindowSimple(PrintUtil.cc("&4&lXiaoBind - 逍式绑定&c&l设置Tag"),PrintUtil.cc("&f请手持物品点击."));
        ui.addButton(new ElementButton(PrintUtil.cc("添加 &4捡拾Tag")));
        ui.addButton(new ElementButton(PrintUtil.cc("添加 &4装备Tag")));
        ui.addButton(new ElementButton(PrintUtil.cc("添加 &4使用Tag")));
        ui.addButton(new ElementButton(PrintUtil.cc("&4返回首页")));
        ui.addButton(new ElementButton(PrintUtil.cc("&4&l关闭")));
        return ui;

    }

    public static FormWindowSimple getTagsWindow()
    {
        List<String> list = new ArrayList<String>();
        list.add("&c绑定: "+ BindUtil.Tag_Bind);
        list.add("&c绑定起始: "+BindUtil.Tag_Start);
        list.add("&c捡拾: "+BindUtil.Tag_Pickup);
        list.add("&c装备: "+BindUtil.Tag_Equip);
        list.add("&c使用: "+BindUtil.Tag_Use);
        FormWindowSimple ui = new FormWindowSimple(PrintUtil.cc("&4&lXiaoBind - 逍式绑定&c&lTags"),PrintUtil.cc(ItemUtil.buildLore(list)));
        ui.addButton(new ElementButton(PrintUtil.cc("&4返回首页")));
        ui.addButton(new ElementButton(PrintUtil.cc("&4&l关闭")));
        return ui;
    }
}
