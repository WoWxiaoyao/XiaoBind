package zbv5.cn.XiaoBind.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import org.json.JSONObject;
import zbv5.cn.XiaoBind.lang.Lang;
import zbv5.cn.XiaoBind.util.BindUtil;
import zbv5.cn.XiaoBind.util.DataUtil;
import zbv5.cn.XiaoBind.util.PluginUtil;
import zbv5.cn.XiaoBind.util.PrintUtil;
import zbv5.cn.XiaoBind.windows.AdminWindow;
import zbv5.cn.XiaoBind.windows.ClaimWindow;


public class WindowsListener implements Listener
{
    @EventHandler
    public void onClickWindow(PlayerFormRespondedEvent e)
    {
        if (e.getPlayer() == null) {
            return;
        }
        if (e.getResponse() == null) {
            return;
        }
        FormWindow gui = e.getWindow();
        JSONObject json = new JSONObject(e.getWindow().getJSONData());

        Player p = e.getPlayer();
        Item item = p.getInventory().getItemInHand();
        String title = json.getString("title");
        if (gui instanceof FormWindowSimple)
        {
            String ButtonName = ((FormResponseSimple)e.getResponse()).getClickedButton().getText();
            if(title.equals(PrintUtil.cc("&4&l绑定丢失物品找回")))
            {
                if(ButtonName.equalsIgnoreCase(PrintUtil.cc("&8&l一键找回")))
                {
                    DataUtil.ReceiveItem(p);
                }
            }
            if(title.equals(PrintUtil.cc("&4&lXiaoBind - 逍式绑定")))
            {
                if(ButtonName.equalsIgnoreCase(PrintUtil.cc("&c重载配置文件"))) PluginUtil.reloadLoadPlugin();
                if(ButtonName.equalsIgnoreCase(PrintUtil.cc("查询Tag"))) p.showFormWindow(AdminWindow.getTagsWindow());
                if(ButtonName.equalsIgnoreCase(PrintUtil.cc("添加Tag"))) p.showFormWindow(AdminWindow.getSetTagsWindow());
                if(ButtonName.equalsIgnoreCase(PrintUtil.cc("绑定/解绑"))) p.showFormWindow(AdminWindow.getSetBindOrUnbindWindow());
                if(ButtonName.equalsIgnoreCase(PrintUtil.cc("绑定丢失找回"))) p.showFormWindow(ClaimWindow.getClaimWindow(p));
            }
            if(title.equals(PrintUtil.cc("&4&lXiaoBind - 逍式绑定&c&l绑定/解绑")))
            {
                if((item == null) ||(item.getId()==0))
                {
                    PrintUtil.PrintCommandSender(p, Lang.Bind_FailNullItem);
                    return;
                }
                if(ButtonName.equalsIgnoreCase(PrintUtil.cc("绑定"))) p.getInventory().setItemInHand(BindUtil.addBind(p,item,"Bind"));
                if(ButtonName.equalsIgnoreCase(PrintUtil.cc("解绑"))) p.getInventory().setItemInHand(BindUtil.unBind(p,item));
            }
            if(title.equals(PrintUtil.cc("&4&lXiaoBind - 逍式绑定&c&l设置Tag")))
            {
                if((item == null) ||(item.getId()==0))
                {
                    PrintUtil.PrintCommandSender(p, Lang.Bind_FailNullItem);
                    return;
                }
                if(ButtonName.equalsIgnoreCase(PrintUtil.cc("添加 &4捡拾Tag"))) p.getInventory().setItemInHand(BindUtil.addBind(p,item,"Pickup"));
                if(ButtonName.equalsIgnoreCase(PrintUtil.cc("添加 &4装备Tag"))) p.getInventory().setItemInHand(BindUtil.addBind(p,item,"Equip"));
                if(ButtonName.equalsIgnoreCase(PrintUtil.cc("添加 &4使用Tag"))) p.getInventory().setItemInHand(BindUtil.addBind(p,item,"Use"));
            }
            if(title.startsWith(PrintUtil.cc("&4&lXiaoBind -")))
            {
                if(ButtonName.equalsIgnoreCase(PrintUtil.cc("&4返回首页"))) p.showFormWindow(AdminWindow.getAdminWindow());
            }
        }

    }
}
