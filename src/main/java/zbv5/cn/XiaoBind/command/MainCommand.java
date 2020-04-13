package zbv5.cn.XiaoBind.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginIdentifiableCommand;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.Tag;
import cn.nukkit.plugin.Plugin;
import zbv5.cn.XiaoBind.Main;
import zbv5.cn.XiaoBind.lang.Lang;
import zbv5.cn.XiaoBind.util.BindUtil;
import zbv5.cn.XiaoBind.util.ItemUtil;
import zbv5.cn.XiaoBind.util.PluginUtil;
import zbv5.cn.XiaoBind.util.PrintUtil;
import zbv5.cn.XiaoBind.windows.AdminWindow;
import zbv5.cn.XiaoBind.windows.ClaimWindow;
import zbv5.cn.XiaoBind.windows.PlayerBUWindow;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.regex.Pattern;

public class MainCommand extends Command implements PluginIdentifiableCommand
{
    private final Main plugin;

    public MainCommand(Main plugin) {
        super("XiaoBind", "XiaoBind 插件主指令.", "/XiaoBind <help|reload>", new String[]{"xbind"});
        this.setPermission("XiaoBind.command");
        this.getCommandParameters().clear();
        this.addCommandParameters("default", new CommandParameter[]{
                new CommandParameter("命令", false, new String[]{"help","reload"})
        });
        this.plugin = plugin;
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args)
    {
        if (!this.plugin.isEnabled() || !this.testPermission(sender))
        {
            return false;
        }
        if((args.length == 0) ||(args[0].equalsIgnoreCase("help"))||(args[0].equalsIgnoreCase("?")))
        {
            PrintUtil.PrintCommandSender(sender,"&6=== [&bXiaoBind&6] &6===");
            PrintUtil.PrintCommandSender(sender,"&6/"+label+" help&f[?] &7- &b查看帮助");
            PrintUtil.PrintCommandSender(sender,"&6/"+label+" claim&7- &b打开托管物品页面");
            PrintUtil.PrintCommandSender(sender,"&6/"+label+" open &7- &b打开逍式绑定页面");
            PrintUtil.PrintCommandSender(sender,"&6/"+label+" tags &7- &b查看当前绑定标题");
            PrintUtil.PrintCommandSender(sender,"&6/"+label+" use &7- &b打开物品使用页面");
            PrintUtil.PrintCommandSender(sender,"&6/"+label+" give <类型> <数量> <玩家> &7- &b给予玩家绑定/解绑卡");
            PrintUtil.PrintCommandSender(sender,"&6/Bind &7- &b添加绑定");
            PrintUtil.PrintCommandSender(sender,"&6/BindOnEquip&f[boe] &7- &b添加装备绑定");
            PrintUtil.PrintCommandSender(sender,"&6/BindOnPickup&f[bop] &7- &b添加捡拾绑定");
            PrintUtil.PrintCommandSender(sender,"&6/BindOnUse&f[bou] &7- &b添加使用绑定");
            PrintUtil.PrintCommandSender(sender,"&6/UnBind &7- &b解绑绑定");
            PrintUtil.PrintCommandSender(sender,"&6/"+label+"&c reload &7- &d重载插件配置文件");
            return true;
        }

        if(args[0].equalsIgnoreCase("search"))
        {
            if(!sender.hasPermission("XiaoBind.search"))
            {
                PrintUtil.PrintCommandSender(sender,Lang.NoPermission);
                return false;
            }
            if(args.length == 2)
            {
                Player p = Main.getInstance().getServer().getPlayer(args[1]);
                if((p == null) || (!p.isOnline()))
                {
                    PrintUtil.PrintCommandSender(sender,Lang.NullPlayer.replace("{player}",args[1]));
                    return false;
                }
                BindUtil.checkPlayerPack(p);
                PrintUtil.PrintCommandSender(sender,Lang.Executed);
            }

        }
        if(args[0].equalsIgnoreCase("tags"))
        {
            if(!sender.hasPermission("XiaoBind.tags"))
            {
                PrintUtil.PrintCommandSender(sender,Lang.NoPermission);
                return false;
            }
            PrintUtil.PrintCommandSender(sender,"&6=== [&bXiaoBind&cTags&6] &6===");
            PrintUtil.PrintCommandSender(sender,"&c绑定: "+BindUtil.Tag_Bind);
            PrintUtil.PrintCommandSender(sender,"&c绑定起始: "+BindUtil.Tag_Start);
            PrintUtil.PrintCommandSender(sender,"&c捡拾: "+BindUtil.Tag_Pickup);
            PrintUtil.PrintCommandSender(sender,"&c装备: "+BindUtil.Tag_Equip);
            PrintUtil.PrintCommandSender(sender,"&c使用: "+BindUtil.Tag_Use);
            return true;
        }
        if(args[0].equalsIgnoreCase("reload"))
        {
            if(!sender.hasPermission("XiaoBind.reload"))
            {
                PrintUtil.PrintCommandSender(sender,Lang.NoPermission);
                return false;
            }
            try
            {
                PluginUtil.reloadLoadPlugin();
                PrintUtil.PrintCommandSender(sender, Lang.SuccessReload);
                return true;
            } catch (Exception e)
            {
                PrintUtil.PrintCommandSender(sender,Lang.FailReload);
                e.printStackTrace();
            }
            return false;
        }
        if(args[0].equalsIgnoreCase("give"))
        {
            if(!sender.hasPermission("XiaoBind.give"))
            {
                PrintUtil.PrintCommandSender(sender,Lang.NoPermission);
                return false;
            }
            if(args.length == 4)
            {
                Pattern pattern = Pattern.compile("[0-9]*");
                String info = args[1];
                Player p = Main.getInstance().getServer().getPlayer(args[3]);
                if(!pattern.matcher(args[2]).matches())
                {
                    PrintUtil.PrintCommandSender(sender,Lang.NoInteger);
                    return false;
                }
                if((p==null)||(!p.isOnline()))
                {
                    PrintUtil.PrintCommandSender(sender,Lang.NullPlayer.replace("{player}",args[3]));
                    return false;
                }
                if(info.equalsIgnoreCase("Bind"))
                {
                    Item item = ItemUtil.Card_Bind(Integer.parseInt(args[2]));
                    p.getInventory().addItem(item);
                    PrintUtil.PrintCommandSender(sender,Lang.Executed);
                    return true;
                }
                if(info.equalsIgnoreCase("unbind"))
                {
                    Item item = ItemUtil.Card_Unbind(Integer.parseInt(args[2]));
                    p.getInventory().addItem(item);
                    PrintUtil.PrintCommandSender(sender,Lang.Executed);
                    return true;
                }
            }
            PrintUtil.PrintCommandSender(sender,"{prefix}&c正确用法&a/"+label+" give <bind/unbind> <数量> <玩家>");
            return false;
        }
        if((args[0].equalsIgnoreCase("claim"))||(args[0].equalsIgnoreCase("open"))||(args[0].equalsIgnoreCase("use")))
        {
            if(sender instanceof Player)
            {
                Player p = (Player)sender;
                if(args[0].equalsIgnoreCase("claim"))
                {
                    if(!sender.hasPermission("XiaoBind.claim"))
                    {
                        PrintUtil.PrintCommandSender(sender,Lang.NoPermission);
                        return false;
                    }
                    p.showFormWindow(ClaimWindow.getClaimWindow(p));
                    return true;
                }
                if(args[0].equalsIgnoreCase("open"))
                {
                    if(!sender.hasPermission("XiaoBind.admin"))
                    {
                        PrintUtil.PrintCommandSender(sender,Lang.NoPermission);
                        return false;
                    }
                    p.showFormWindow(AdminWindow.getAdminWindow());
                    return true;
                }
                if(args[0].equalsIgnoreCase("use"))
                {
                    if(!sender.hasPermission("XiaoBind.use"))
                    {
                        PrintUtil.PrintCommandSender(sender,Lang.NoPermission);
                        return false;
                    }
                    p.showFormWindow(PlayerBUWindow.getPlayerBUWindow());
                    return true;
                }
                return true;
            } else {
                PrintUtil.PrintCommandSender(sender,Lang.NeedPlayer);
            }
            return false;
        }
        PrintUtil.PrintCommandSender(sender,Lang.NullCommand);
        return false;
    }
}
