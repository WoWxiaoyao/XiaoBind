package zbv5.cn.XiaoBind.util;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import zbv5.cn.XiaoBind.lang.Lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BindUtil
{
    public static String Tag_Bind = "&6绑定: &c{player}";
    public static String Tag_Start = "&6绑定: &c";
    public static String Tag_Pickup = "&4拾取后绑定";
    public static String Tag_Use = "&4使用后绑定";
    public static String Tag_Equip = "&4装备后绑定";

    public static void load()
    {
        try
        {
            Tag_Bind = FileUtil.config.getString("Tag.Bind");
            Tag_Start = FileUtil.config.getString("Tag.Start");
            Tag_Pickup = FileUtil.config.getString("Tag.Pickup");
            Tag_Use = FileUtil.config.getString("Tag.Use");
            Tag_Equip = FileUtil.config.getString("Tag.Equip");
            PrintUtil.PrintConsole("&a&l√ &aTag加载完成.");
        }
        catch (Exception e)
        {
            PrintUtil.PrintConsole("&c&l× &4读取绑定Tag出现问题,请检查服务器.");
            e.printStackTrace();
        }
    }

    public static Item addBind(Player p,Item item,String type)
    {
        if((item != null) && (item.getId() != 0))
        {
            List<String> list = getLore(item);
            List<String> Lore = new ArrayList<String>();
            if(list != null)
            {
                if(isBind(item))
                {
                    PrintUtil.PrintCommandSender(p, Lang.Bind_AlreadyBind);
                    return item;
                }
                Lore = new ArrayList<String>(list);
            }
            if(type.equalsIgnoreCase("Bind"))
            {
                if(list != null)
                {
                    for(String lore:list)
                    {
                        if((lore.equalsIgnoreCase(PrintUtil.cc(Tag_Equip))) || (lore.equalsIgnoreCase(PrintUtil.cc(Tag_Pickup))) || (lore.equalsIgnoreCase(PrintUtil.cc(Tag_Use))))
                        {
                            Lore.remove(lore);
                        }
                    }
                }
                Lore.add(Tag_Bind.replace("{player}",p.getName()));
            }
            if(type.equalsIgnoreCase("Pickup"))
            {
                for(String lore:Lore)
                {
                    if(lore.equalsIgnoreCase(PrintUtil.cc(Tag_Pickup)))
                    {
                        PrintUtil.PrintCommandSender(p, Lang.Bind_AlreadyTag.replace("{tag}",Tag_Pickup));
                        return item;
                    }
                }
                Lore.add(Tag_Pickup);
            }
            if(type.equalsIgnoreCase("Use"))
            {
                for(String lore:Lore)
                {
                    if(lore.equalsIgnoreCase(PrintUtil.cc(Tag_Use)))
                    {
                        PrintUtil.PrintCommandSender(p, Lang.Bind_AlreadyTag.replace("{tag}",Tag_Use));
                        return item;
                    }
                }
                Lore.add(Tag_Use);
            }
            if(type.equalsIgnoreCase("Equip"))
            {
                for(String lore:Lore)
                {
                    if(lore.equalsIgnoreCase(PrintUtil.cc(Tag_Equip)))
                    {
                        PrintUtil.PrintCommandSender(p, Lang.Bind_AlreadyTag.replace("{tag}",Tag_Equip));
                        return item;
                    }
                }
                Lore.add(Tag_Equip);
            }
            item.setLore(PrintUtil.cc(buildLore(Lore)));
        }
        return item;
    }

    public static Item unBind(Player p,Item item)
    {
        if((item != null) && (item.getId() != 0))
        {
            List<String> list = getLore(item);
            List<String> Lore;

            if((list != null) && (isBind(item)))
            {
                Lore = new ArrayList<String>(list);

                for(String lore:list)
                {
                    if(lore.startsWith(PrintUtil.cc(Tag_Start)))
                    {
                        Lore.remove(lore);
                    }
                }

                if(Lore.isEmpty())
                {
                    item.setLore();
                } else {
                    item.setLore(PrintUtil.cc(buildLore(Lore)));
                }

                PrintUtil.PrintCommandSender(p,Lang.unBind_Success);
            } else {
                PrintUtil.PrintCommandSender(p,Lang.unBind_FailNoBind);
            }
        }
        return item;
    }

    public static boolean hasBindLore(Player p,Item item,String type)
    {
        if((item != null) && (item.getId() != 0) && (getLore(item) != null))
        {
            List<String> list = getLore(item);
            for(String lore:list)
            {
                if(type.equalsIgnoreCase("Use"))
                {
                    if(lore.equalsIgnoreCase(PrintUtil.cc(Tag_Use)))
                    {
                        return true;
                    }
                }
                if(type.equalsIgnoreCase("Pickup"))
                {
                    if(lore.equalsIgnoreCase(PrintUtil.cc(Tag_Pickup)))
                    {
                        return true;
                    }
                }
                if(type.equalsIgnoreCase("Equip"))
                {
                    if(lore.equalsIgnoreCase(PrintUtil.cc(Tag_Equip)))
                    {
                        return true;
                    }
                }
                if(type.equalsIgnoreCase("All"))
                {
                    if((lore.equalsIgnoreCase(PrintUtil.cc(Tag_Use)))||(lore.equalsIgnoreCase(PrintUtil.cc(Tag_Pickup)))||(lore.equalsIgnoreCase(PrintUtil.cc(Tag_Equip))))
                    {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public static boolean isBind(Item item)
    {
        if((item != null) && (item.getId() != 0) && (getLore(item) != null))
        {
            List<String> list = getLore(item);

            for(String lore:list)
            {
                if(lore.startsWith(PrintUtil.cc(Tag_Start))) return true;
            }
        }
        return false;
    }

    public static boolean isSelf(Player p,Item item)
    {
        String name = getBindPlayerName(item);
        if(name == null)
        {
            return true;
        } else {
            return p.getName().equalsIgnoreCase(name);
        }
    }
    public static String getBindPlayerName(Item item)
    {
        if((item != null) && (item.getId() != 0) && (getLore(item) != null))
        {
            List<String> list = getLore(item);
            if(list != null)
            {
                for(String lore:list)
                {
                    if(lore.startsWith(PrintUtil.cc(Tag_Start)))
                    {
                        return lore.replace(PrintUtil.cc(Tag_Start),"");
                    }
                }
            }
        }
        return null;
    }

    private static List<String> getLore(Item item)
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

    private static String buildLore(List<String> list)
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
