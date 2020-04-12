package zbv5.cn.XiaoBind.util;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.utils.ConfigSection;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Base64;

public class ItemUtil
{
    //test code 2020/4/8

    public static void saveItem(String PlayerName, Item item)
    {
        //判断item是否为null或空气
        if((item != null) && (item.getId() != 0))
        {
            //不保存不含Lore物品
            if(getLore(item) == null) return;
            //生成随机name
            String name = DateUtil.getDate("yyyy-MM-dd-HH-mm-ss") +"~"+ getRandom(10);
            while (FileUtil.data.getString(name+".player") == null)
            {
                name = DateUtil.getDate("yyyy-MM-dd-HH-mm-ss") +"~"+ getRandom(10);
            }
            //绑定玩家id
            FileUtil.data.set(name+".player",PlayerName);
            //储存物品id
            FileUtil.data.set(name+".item.ID",item.getId());
            //储存物品meta
            if(item.hasMeta())
            {
                FileUtil.data.set(name+".item.Meta",item.getDamage());
            } else {
                FileUtil.data.set(name+".item.Meta",0);
            }
            FileUtil.data.set(name+".item.Meta",item.getDamage());

            //储存物品数量
            FileUtil.data.set(name+".item.amount",item.getCount());

            //储存NBT
            if(item.hasCompoundTag())
            {
                byte[] bytes = item.getCompoundTag();
                String base64Str = Base64.getEncoder().encodeToString(bytes);
                FileUtil.data.set(name+".item.base64",base64Str);
            }
            FileUtil.data.save();
        }
    }

    public static Item getItem(String name)
    {
        ConfigSection c = FileUtil.data.getSection(name);

        if((c.getString("player") == null)||(c.getString("player").equals(""))) return null;

        c = FileUtil.data.getSection(name+".item");
        if(c == null) return null;

        Item item = new Item(c.getInt("ID"),c.getInt("Meta"),c.getInt("amount"));

        String base64Str = c.getString("base64");
        byte[] bytes = Base64.getDecoder().decode(base64Str);
        item.setCompoundTag(bytes);

        return item;
    }

    private static String getRandom(int length)
    {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<length;i++)
        {
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static List<String> getLore(Item item)
    {
        if((item != null) && (item.getId() != 0))
        {
            if(item.getLore().length == 1)
            {
                if(Arrays.toString(item.getLore()).contains("\n"))
                {
                    return Arrays.asList(item.getLore()[0].split("\n"));
                } else {
                    List<String> list  = new ArrayList<String>();
                    list.add(item.getLore()[0]);
                    return list;
                }
            }
        }
        return null;
    }

    public static String buildLore(List<String> list)
    {
        if(list.isEmpty())
        {
            return null;
        } else {
            StringBuilder lore = new StringBuilder();
            int size = 1;
            for (String s :list)
            {
                if(size == list.size())
                {
                    lore.append(s);
                } else {
                    lore.append(s).append("\n");
                }
                size ++;
            }
            return lore.toString();
        }
    }
}
