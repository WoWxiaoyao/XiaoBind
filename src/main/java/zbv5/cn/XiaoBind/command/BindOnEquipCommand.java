package zbv5.cn.XiaoBind.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginIdentifiableCommand;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.Plugin;
import zbv5.cn.XiaoBind.Main;
import zbv5.cn.XiaoBind.lang.Lang;
import zbv5.cn.XiaoBind.util.BindUtil;
import zbv5.cn.XiaoBind.util.PrintUtil;

public class BindOnEquipCommand extends Command implements PluginIdentifiableCommand
{
    private final Main plugin;

    public BindOnEquipCommand(Main plugin) {
        super("BindOnEquip", "XiaoBind 插件BindOnEquip指令.", "/BindOnEquip <player>", new String[]{"boe"});
        this.setPermission("XiaoBind.BindOnEquip");
        this.plugin = plugin;
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args)
    {
        if (!this.plugin.isEnabled()) return false;

        if (args.length == 0)
        {
            if(sender instanceof Player)
            {
                if((sender.hasPermission("XiaoBind.BindOnEquip")) || (sender.hasPermission("XiaoBind.BindOnEquip.Other")))
                {
                    Player p = (Player) sender;
                    Item item = p.getInventory().getItemInHand();
                    if((item == null) ||(item.getId()==0))
                    {
                        PrintUtil.PrintCommandSender(p,Lang.Bind_FailNullItem);
                        return false;
                    }
                    p.getInventory().setItemInHand(BindUtil.addBind(p,item,"Equip"));
                    PrintUtil.PrintCommandSender(sender,Lang.Executed);
                    return true;
                } else {
                    PrintUtil.PrintCommandSender(sender, Lang.NoPermission);
                    return false;
                }
            } else {
                PrintUtil.PrintCommandSender(sender,Lang.NeedPlayer);
                return false;
            }
        }
        if (args.length == 1)
        {
            if(sender.hasPermission("XiaoBind.BindOnEquip.Other"))
            {
                Player p = Main.getInstance().getServer().getPlayer(args[0]);
                if((p != null) && (p.isOnline()))
                {
                    Item item = p.getInventory().getItemInHand();
                    if((item == null) ||(item.getId()==0))
                    {
                        PrintUtil.PrintCommandSender(p,Lang.Bind_FailNullItem);
                        return false;
                    }
                    p.getInventory().setItemInHand(BindUtil.addBind(p,item,"Equip"));
                    PrintUtil.PrintCommandSender(sender,Lang.Executed);
                    return true;
                } else {
                    PrintUtil.PrintCommandSender(sender,Lang.NullPlayer);
                    return false;
                }
            } else {
                PrintUtil.PrintCommandSender(sender,Lang.NoPermission);
                return false;
            }
        }
        PrintUtil.PrintCommandSender(sender,"{prefix}&c格式错误:/"+label+" &7<玩家>");
        return false;
    }
}
