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

public class BindOnUseCommand extends Command implements PluginIdentifiableCommand
{
    private final Main plugin;

    public BindOnUseCommand(Main plugin) {
        super("BindOnUse", "XiaoBind 插件BindOnUse指令.", "/BindOnUse <player>", new String[]{"bou"});
        this.setPermission("XiaoBind.BindOnUse");
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
                if((sender.hasPermission("XiaoBind.BindOnUse")) || (sender.hasPermission("XiaoBind.BindOnUse.Other")))
                {
                    Player p = (Player) sender;
                    Item item = p.getInventory().getItemInHand();
                    if((item == null) ||(item.getId()==0))
                    {
                        PrintUtil.PrintCommandSender(p,Lang.Bind_FailNullItem);
                        return false;
                    }
                    p.getInventory().setItemInHand(BindUtil.addBind(p,item,"Use"));
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
            if(sender.hasPermission("XiaoBind.BindOnUse.Other"))
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
                    p.getInventory().setItemInHand(BindUtil.addBind(p,item,"Use"));
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
