package zbv5.cn.XiaoBind.listener;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.inventory.InventoryClickEvent;
import cn.nukkit.event.inventory.InventoryPickupItemEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerDropItemEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerItemHeldEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import zbv5.cn.XiaoBind.Main;
import zbv5.cn.XiaoBind.lang.Lang;
import zbv5.cn.XiaoBind.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerListener implements Listener
{
    public static HashMap<String, Integer> check_inv = new HashMap<String, Integer>();

    public static boolean CheckOffHand = true;
    public static int CheckOffHand_Delay = 20;
    public static boolean Drop = false;
    public static boolean Click = false;
    public static boolean Held = false;
    public static boolean Build = false;
    public static boolean Break = false;
    public static boolean DeleteOnDrop = false;
    public static boolean KeepOnDeath = true;

    public static boolean KeepProtect = true;
    public static boolean DropProtect = true;
    public static boolean PickUpProtect = true;
    public static boolean NoSelfSend = true;
    public static void load()
    {
        try
        {
            if(FileUtil.config.getBoolean("Setting.CheckOffHand.Enable"))
            {
                CheckOffHand_Delay = FileUtil.config.getInt("Setting.CheckOffHand.Delay");
            } else {
                CheckOffHand = false;
            }
            Drop = FileUtil.config.getBoolean("Setting.AllowDrop");
            Click = FileUtil.config.getBoolean("Setting.AllowClick");
            Held = FileUtil.config.getBoolean("Setting.AllowHeld");
            Build = FileUtil.config.getBoolean("Setting.AllowBuild");
            Break = FileUtil.config.getBoolean("Setting.AllowBreak");
            DeleteOnDrop = FileUtil.config.getBoolean("Setting.DeleteOnDrop");
            KeepOnDeath = FileUtil.config.getBoolean("Setting.KeepOnDeath");

            KeepProtect = FileUtil.config.getBoolean("Setting.KeepProtect");
            DropProtect = FileUtil.config.getBoolean("Setting.DropProtect");
            PickUpProtect = FileUtil.config.getBoolean("Setting.PickUpProtect");
            NoSelfSend = FileUtil.config.getBoolean("Setting.NoSelfSend");
            PrintUtil.PrintConsole("&a&l√ &a监听设定加载完成.");
        }
        catch (Exception e)
        {
            PrintUtil.PrintConsole("&c&l× &4读取监听设定出现问题,请检查服务器.");
            e.printStackTrace();
        }
    }
    //1.1.0 done
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e)
    {
        if(e.isCancelled()) return;
        if((Drop)&&(!DeleteOnDrop)) return;
        Player p = e.getPlayer();
        final Item item = e.getItem();
        if(BindUtil.isBind(item))
        {
            if(Drop)
            {
                if(DeleteOnDrop)
                {
                    final Level world = p.getLevel();
                    int clearDelay = 1;
                    if(KeepOnDeath)
                    {
                        clearDelay = 2;
                    }
                    Main.getInstance().getServer().getScheduler().scheduleDelayedTask(Main.getInstance(), new Runnable()
                    {
                        public void run()
                        {
                            Entity[] entities = world.getEntities();
                            for(Entity entity : entities)
                            {
                                if(entity instanceof EntityItem)
                                {
                                    Item SearchItem = ((EntityItem) entity).getItem();
                                    if((SearchItem.getId() != 0) && (SearchItem.getLore().length == 1) &&(SearchItem == item) && (BindUtil.isBind(SearchItem))&& (BindUtil.getBindPlayerName(SearchItem).equals(p.getName())))
                                    {
                                        world.removeEntity(entity);
                                        if((DropProtect))
                                        {
                                            ItemUtil.saveItem(p.getName(),item);
                                        }
                                    }
                                }
                            }
                        }
                    }, clearDelay);
                }
            } else {
                if((p.isOp()) || p.hasPermission("XiaoBind.ignore")) return;
                e.setCancelled(true);
                PrintUtil.PrintCommandSender(p, Lang.Drop);
            }
        }
    }
    //1.1.0 done
    @EventHandler
    public void onPlayerPickUpItem(InventoryPickupItemEvent e)
    {
        if(e.isCancelled()) return;
        Player p = (Player)e.getInventory().getHolder();
        Item item = e.getItem().getItem();
        if(BindUtil.hasBindLore(p,item,"Pickup"))
        {
            Item BindItem = BindUtil.addBind(p,item,"Bind");
            PrintUtil.PrintCommandSender(p,Lang.Bind_SuccessPickUp);
            return;
        }
        if(BindUtil.isBind(item))
        {
            if(BindUtil.isSelf(p,item))
            {
                if((PickUpProtect) &&(DataUtil.getFullInventorySize(p)==0))
                {
                    Entity[] entities = p.getLevel().getEntities();
                    for(Entity entity : entities)
                    {
                        if(entity instanceof EntityItem)
                        {
                            Item SearchItem = ((EntityItem) entity).getItem();
                            if((SearchItem.getId() != 0) && (SearchItem.getLore().length == 1) &&(SearchItem == item) && (BindUtil.isBind(SearchItem))&& (BindUtil.getBindPlayerName(SearchItem).equals(p.getName())))
                            {
                                p.getLevel().removeEntity(entity);
                                ItemUtil.saveItem(p.getName(),item);
                                PrintUtil.PrintCommandSender(p,Lang.PickUp_Full);

                            }
                        }
                    }
                }
            } else {
                if((p.isOp()) || p.hasPermission("XiaoBind.ignore")) return;
                e.setCancelled(true);
                PrintUtil.PrintCommandSender(p,Lang.Bind_NoSelf_PickUP);
            }
        }
    }
    //1.1.0 done
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        if(e.isCancelled()) return;
        Player p = e.getPlayer();
        Item item = e.getItem();
        if(BindUtil.hasBindLore(p,item,"Use"))
        {
            e.setCancelled(true);
            Item BindItem = BindUtil.addBind(p,item,"Bind");
            p.getInventory().setItemInHand(BindItem);
            PrintUtil.PrintCommandSender(p,Lang.Bind_SuccessUse);
        }
        if((e.getAction() == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) || (e.getAction() == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK))
        {
            if(BindUtil.hasBindLore(p,item,"Equip"))
            {
                e.setCancelled(true);
                Item BindItem = BindUtil.addBind(p,item,"Bind");
                p.getInventory().setItemInHand(BindItem);
                PrintUtil.PrintCommandSender(p,Lang.Bind_SuccessEquip);
            }
        }

        if((BindUtil.isBind(item)) && (!BindUtil.isSelf(p,item)) && (NoSelfSend))
        {
            p.getInventory().remove(item);
            ItemUtil.saveItem(BindUtil.getBindPlayerName(item),item);
        }
    }
    //1.1.0 done
    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent e)
    {
        if(e.isCancelled()) return;

        Player p = e.getPlayer();

        Item item = e.getSourceItem();
        if((BindUtil.isBind(item)) && (!BindUtil.isSelf(p,item)))
        {
            if((p.isOp()) || p.hasPermission("XiaoBind.ignore")) return;
            if(NoSelfSend)
            {
                e.setCancelled(true);
                p.getInventory().remove(item);
                ItemUtil.saveItem(BindUtil.getBindPlayerName(item),item);
                return;
            }
            if(!Click)
            {
                e.setCancelled(true);
                PrintUtil.PrintCommandSender(p,Lang.Bind_NoSelf_Inv);
                return;
            }
        }
        if(!check_inv.containsKey(p.getName()))
        {
            check_inv.put(p.getName(),857);
            TaskUtil.checkEquip(p);
        }
    }
    //1.1.0 done
    @EventHandler
    public void onBuild(BlockPlaceEvent e)
    {
        Player p = e.getPlayer();
        Item item = p.getInventory().getItemInHand();
        if(BindUtil.hasBindLore(p,item,"Use"))
        {
            e.setCancelled(true);
            Item BindItem = BindUtil.addBind(p,item,"Bind");
            p.getInventory().setItemInHand(BindItem);
            PrintUtil.PrintCommandSender(p,Lang.Bind_SuccessUse);
            return;
        }
        if((BindUtil.isBind(item)) && (!BindUtil.isSelf(p,item)))
        {
            if((p.isOp()) || p.hasPermission("XiaoBind.ignore")) return;
            if(NoSelfSend)
            {
                e.setCancelled(true);
                p.getInventory().remove(item);
                ItemUtil.saveItem(BindUtil.getBindPlayerName(item),item);
                return;
            }
            if(!Build)
            {
                e.setCancelled(true);
                PrintUtil.PrintCommandSender(p,Lang.Bind_NoSelf_Held);
            }
        }
    }
    //1.1.0 done
    @EventHandler
    public void onBreak(BlockBreakEvent e)
    {
        Player p = e.getPlayer();
        Item item = p.getInventory().getItemInHand();
        if(BindUtil.hasBindLore(p,item,"Use"))
        {
            e.setCancelled(true);
            Item BindItem = BindUtil.addBind(p,item,"Bind");
            p.getInventory().setItemInHand(BindItem);
            PrintUtil.PrintCommandSender(p,Lang.Bind_SuccessUse);
            return;
        }
        if((BindUtil.isBind(item)) && (!BindUtil.isSelf(p,item)))
        {
            if((p.isOp()) || p.hasPermission("XiaoBind.ignore")) return;
            if(NoSelfSend)
            {
                e.setCancelled(true);
                p.getInventory().remove(item);
                ItemUtil.saveItem(BindUtil.getBindPlayerName(item),item);
                return;
            }
            if(!Break)
            {
                e.setCancelled(true);
                PrintUtil.PrintCommandSender(p,Lang.Bind_NoSelf_Held);
            }
        }
    }
    //1.1.0 done
    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent e)
    {
        Player p = e.getPlayer();
        Item item = e.getItem();
        if((BindUtil.isBind(item))&& (!BindUtil.isSelf(p,item)))
        {
            if((p.isOp()) || p.hasPermission("XiaoBind.ignore")) return;
            if(NoSelfSend)
            {
                p.getInventory().remove(item);
                ItemUtil.saveItem(BindUtil.getBindPlayerName(item),item);
                return;
            }
            if(!Held)
            {
                e.setCancelled(true);
                PrintUtil.PrintCommandSender(p,Lang.Bind_NoSelf_Held);
            }
        }
    }
    //1.1.0 done
    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e)
    {
        if(e.getDamager() instanceof  Player)
        {
            Player p = (Player) e.getDamager();
            Item item = p.getInventory().getItemInHand();
            if(BindUtil.hasBindLore(p,item,"Use"))
            {
                e.setCancelled(true);
                Item BindItem = BindUtil.addBind(p,item,"Bind");
                p.getInventory().setItemInHand(BindItem);
                PrintUtil.PrintCommandSender(p,Lang.Bind_SuccessUse);
                return;
            }
            if(NoSelfSend)
            {
                p.getInventory().remove(item);
                ItemUtil.saveItem(BindUtil.getBindPlayerName(item),item);
                return;
            }
            if((BindUtil.isBind(item))&& (!BindUtil.isSelf(p,item)))
            {
                if((p.isOp()) || p.hasPermission("XiaoBind.ignore")) return;

                if(!Held)
                {
                    e.setCancelled(true);
                    PrintUtil.PrintCommandSender(p,Lang.Bind_NoSelf_Held);
                }
            }
        }
    }
    //1.1.0 done
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {
        if(e.isCancelled()) return;
        if(!e.getKeepInventory() && (KeepOnDeath))
        {
            PrintUtil.PrintConsole("1");
            final Player p = e.getEntity();
            final List<Item> items = new ArrayList<Item>();
            for(Item item:e.getDrops())
            {
                if((BindUtil.isBind(item)) && (BindUtil.isSelf(p,item)))
                {
                    items.add(item);
                }
            }
            final Level world = p.getLevel();
            Main.getInstance().getServer().getScheduler().scheduleDelayedTask(Main.getInstance(), new Runnable()
            {
                public void run()
                {
                    Entity[] entities = world.getEntities();
                    for(Entity entity : entities)
                    {
                        if(entity instanceof EntityItem)
                        {
                            Item item = ((EntityItem) entity).getItem();
                            if(items.contains(item))
                            {
                                world.removeEntity(entity);
                                if(p.isOnline())
                                {
                                    if(KeepProtect)
                                    {
                                        Boolean full = false;
                                        if(DataUtil.getFullInventorySize(p) == 0)
                                        {
                                            full = true;
                                        } else  {
                                            p.getInventory().addItem(item);
                                        }
                                        if(full)
                                        {
                                            PrintUtil.PrintCommandSender(p,Lang.KeepOnDeath_Full);
                                        }
                                    } else {
                                        p.getInventory().addItem(item);
                                    }
                                }
                            }
                        }
                    }
                }
            }, 1);
            if((!items.isEmpty()) && (p.isOnline()))
            {
                PrintUtil.PrintCommandSender(p,Lang.KeepOnDeath);
            }
        }
    }
}
