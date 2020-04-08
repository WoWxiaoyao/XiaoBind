package zbv5.cn.XiaoBind.util;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import zbv5.cn.XiaoBind.Main;
import zbv5.cn.XiaoBind.lang.Lang;
import zbv5.cn.XiaoBind.listener.PlayerListener;

public class TaskUtil
{
    public static void checkEquip(final Player p)
    {
        Main.getInstance().getServer().getScheduler().scheduleDelayedTask(Main.getInstance(), new Runnable()
        {
            public void run()
            {
                if(p.isOnline())
                {
                    Item item;

                    item = p.getInventory().getHelmet();
                    if((item != null) && (item.getId() != 0) && (item.getLore().length == 1)&&(BindUtil.hasBindLore(p,item,"Equip")))
                    {
                        item = BindUtil.addBind(p,item,"Bind");;
                        p.getInventory().setHelmet(item);
                        PrintUtil.PrintCommandSender(p, Lang.Bind_SuccessEquip);
                    }

                    item = p.getInventory().getChestplate();
                    if((item != null) && (item.getId() != 0) && (item.getLore().length == 1)&&(BindUtil.hasBindLore(p,item,"Equip")))
                    {
                        item = BindUtil.addBind(p,item,"Bind");;
                        p.getInventory().setChestplate(item);
                        PrintUtil.PrintCommandSender(p,Lang.Bind_SuccessEquip);
                    }

                    item = p.getInventory().getLeggings();
                    if((item != null) && (item.getId() != 0) && (item.getLore().length == 1)&&(BindUtil.hasBindLore(p,item,"Equip")))
                    {
                        item = BindUtil.addBind(p,item,"Bind");;
                        p.getInventory().setLeggings(item);
                        PrintUtil.PrintCommandSender(p,Lang.Bind_SuccessEquip);
                    }

                    item = p.getInventory().getBoots();
                    if((item != null) && (item.getId() != 0) && (item.getLore().length == 1)&&(BindUtil.hasBindLore(p,item,"Equip")))
                    {
                        item = BindUtil.addBind(p,item,"Bind");;
                        p.getInventory().setBoots(item);
                        PrintUtil.PrintCommandSender(p,Lang.Bind_SuccessEquip);
                    }
                }
                PlayerListener.check_inv.remove(p.getName());
            }
        }, 1);
    }

    public static void checkOffHand(int delay)
    {
        Main.getInstance().getServer().getScheduler().scheduleRepeatingTask(Main.getInstance(), new Runnable()
        {
            public void run()
            {
                for(Player p:Main.getInstance().getServer().getOnlinePlayers().values())
                {
                    Item item = p.getOffhandInventory().getItem(0);
                    if((item != null) && (item.getId() != 0) && (item.getLore().length == 1)&&(BindUtil.hasBindLore(p,item,"Equip")))
                    {
                        item = BindUtil.addBind(p,item,"Bind");;
                        p.getOffhandInventory().setItem(0,item);
                        PrintUtil.PrintCommandSender(p,Lang.Bind_SuccessEquip);
                    }
                }
            }
        }, delay,true);
    }
}
