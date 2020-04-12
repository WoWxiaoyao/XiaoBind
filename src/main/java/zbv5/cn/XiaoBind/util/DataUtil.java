package zbv5.cn.XiaoBind.util;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.utils.ConfigSection;
import zbv5.cn.XiaoBind.lang.Lang;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataUtil
{
    public static void ReceiveItem(Player p)
    {
        List<String> list = DataUtil.getNameList(p);
        if(!list.isEmpty())
        {
            int add = 0;
            for(String name:list)
            {
                if(getFullInventorySize(p) == 0)
                {
                    PrintUtil.PrintCommandSender(p, Lang.Receive_Full);
                    break;
                }
                if(sendItem(p,name))
                {
                    add ++;
                }
            }
            if(add != 0)
            {
                PrintUtil.PrintCommandSender(p, Lang.Receive_Success.replace("{amount}",String.valueOf(add)));
            }
        }
    }

    private static Boolean sendItem(Player p,String name)
    {
        Item item = ItemUtil.getItem(name);
        if(item != null)
        {
            try {
                FileUtil.data.remove(name);
                FileUtil.data.save();
            }
            catch (Exception e)
            {
                PrintUtil.PrintConsole("&c&l× &4删除data内容失败.");
                e.printStackTrace();
                return false;
            }
            p.getInventory().addItem(item);
            return true;
        }
        return false;
    }














    public static List<String> getNameList(Player p)
    {
        List<String> list = new ArrayList<String>();

        Set<String> keys = new HashSet<String>(FileUtil.data.getKeys(false));

        if(keys.isEmpty()) return list;

        for(String name:keys)
        {
            ConfigSection c = FileUtil.data.getSection(name);
            if(c.getString("player").equals(p.getName()))
            {
                list.add(name);
            }
        }
        return list;
    }


    public static int getFullInventorySize(Player p)
    {
        int size = p.getInventory().getSize();
        for(Item item:p.getInventory().getContents().values())
        {
            if((item != null) && (item.getId() != 0))
            {
                size = size -1;
            }
        }
        return size;
    }
}
