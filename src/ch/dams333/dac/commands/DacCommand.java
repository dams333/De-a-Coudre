package ch.dams333.dac.commands;

import ch.dams333.dac.Dac;
import ch.dams333.dac.deACoudre.DeACoudre;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DacCommand implements CommandExecutor {
    Dac main;
    public DacCommand(Dac dac) {
        this.main = dac;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length >= 1){
                if(args[0].equalsIgnoreCase("create")){
                    if(args.length == 2){
                        String name = args[1];
                        if(!main.dacsManager.isDac(name)){
                            DeACoudre dac = new DeACoudre(name);
                            main.dacs.add(dac);
                            sender.sendMessage(ChatColor.GREEN + "Le dé à coudre " + name + " à été créé. " + ChatColor.BOLD + "/dac set " + name + ChatColor.RESET + ChatColor.GREEN +" pour le modifier");
                            return true;
                        }
                    sender.sendMessage(ChatColor.RED + "Un dé à coudre nommé " + name + " existe déjà");
                        return true;
                    }
                    sender.sendMessage(ChatColor.RED + "/dac create <name>");
                    return true;
                }
                if(args[0].equalsIgnoreCase("remove")){
                    if(args.length == 2){
                        String dacName = args[1];
                        if(main.dacsManager.isDac(dacName)){
                            main.dacsManager.removeDac(dacName);
                            sender.sendMessage(ChatColor.GREEN + "Le dé à coudre nommé " + dacName + " a bien été supprimé");
                            return true;
                        }
                        sender.sendMessage(ChatColor.RED + "Il n'y a pas de dé à coudre nommé " + dacName);
                        return true;
                    }
                    sender.sendMessage(ChatColor.RED + "/dac remove <name>");
                    return true;
                }
                if(args[0].equalsIgnoreCase("set")){
                    if(args.length >= 3){
                        String dacName = args[1];
                        if(!main.dacsManager.isDac(dacName)){
                            sender.sendMessage(ChatColor.RED + "Il n'y a pas de dé à coudre nommé " + dacName);
                            return true;
                        }
                        DeACoudre dac = main.dacsManager.getDac(dacName);
                        if(args[2].equalsIgnoreCase("spectator")){
                            Location loc = p.getLocation();
                            dac.setSpec(loc);
                            main.dacsManager.modifyDac(dac);
                            sender.sendMessage(ChatColor.GREEN + "La position des spectateurs pour le dé à coudre " + dacName + " a été mise en x=" + Math.round(loc.getX()) + ", y=" + Math.round(loc.getY()) + ", z=" + Math.round(loc.getZ()));
                            return true;
                        }
                        if(args[2].equalsIgnoreCase("spawn")){
                            Location loc = p.getLocation();
                            dac.setSpawn(loc);
                            main.dacsManager.modifyDac(dac);
                            sender.sendMessage(ChatColor.GREEN + "La position de spawn pour le dé à coudre " + dacName + " a été mise en x=" + Math.round(loc.getX()) + ", y=" + Math.round(loc.getY()) + ", z=" + Math.round(loc.getZ()));
                            return true;
                        }
                        if(args[2].equalsIgnoreCase("block")){
                            ItemStack it = p.getInventory().getItemInMainHand();
                            if(it != null && it.getType() != Material.AIR){
                                if(it.getType().isBlock()){
                                    dac.setBlock(it.getType());
                                    main.dacsManager.modifyDac(dac);
                                    sender.sendMessage(ChatColor.GREEN + "Le block du dé à coudre " + dacName + " est désormais " + dac.getBlock().name());
                                    return true;
                                }
                                sender.sendMessage(ChatColor.RED + "Veuillez choisir un block posable");
                                return true;
                            }
                            sender.sendMessage(ChatColor.RED + "Veuillez tenir le block en main");
                            return true;
                        }
                        if(args[2].equalsIgnoreCase("minimum")){
                            if(args.length == 4){
                                String nbStr = args[3];
                                if(isNumeric(nbStr) && Integer.parseInt(nbStr) > 0){
                                    int nb = Integer.parseInt(nbStr);
                                    dac.setMinP(nb);
                                    main.dacsManager.modifyDac(dac);
                                    sender.sendMessage(ChatColor.GREEN + "Le nombre minimum de joueur du dé à coudre " + dacName + " est maintenant de " + nb);
                                    return true;
                                }
                                sender.sendMessage(ChatColor.RED + "Veuillez entrer un nombre valide");
                                return true;
                            }
                            sender.sendMessage(ChatColor.RED + "/dac set <name> minimum <minimum>");
                            return true;
                        }
                        if(args[2].equalsIgnoreCase("maximum")){
                            if(args.length == 4){
                                String nbStr = args[3];
                                if(isNumeric(nbStr) && Integer.parseInt(nbStr) > 0){
                                    int nb = Integer.parseInt(nbStr);
                                    dac.setMaxP(nb);
                                    main.dacsManager.modifyDac(dac);
                                    sender.sendMessage(ChatColor.GREEN + "Le nombre maximum de joueur du dé à coudre " + dacName + " est maintenant de " + nb);
                                    return true;
                                }
                                sender.sendMessage(ChatColor.RED + "Veuillez entrer un nombre valide");
                                return true;
                            }
                            sender.sendMessage(ChatColor.RED + "/dac set <name> maximum <maximum>");
                            return true;
                        }
                        if(args[2].equalsIgnoreCase("dac")){
                            if(args.length == 4){
                                if(args[3].equalsIgnoreCase("true")){
                                    dac.setDac(true);
                                    main.dacsManager.modifyDac(dac);
                                    sender.sendMessage(ChatColor.GREEN + "Les dé à coudre sont maintenant activés sur le dé à coudre " + dacName);
                                    return true;
                                }
                                if(args[3].equalsIgnoreCase("false")){
                                    dac.setDac(false);
                                    main.dacsManager.modifyDac(dac);
                                    sender.sendMessage(ChatColor.GREEN + "Les dé à coudre sont maintenant désactivés sur le dé à coudre " + dacName);
                                    return true;
                                }
                            }
                            sender.sendMessage(ChatColor.RED + "/dac set <name> dac <true/false>");
                            return true;
                        }
                        if(args[2].equalsIgnoreCase("time")){
                            if(args.length == 4){
                                String nbStr = args[3];
                                if(isNumeric(nbStr) && Integer.parseInt(nbStr) >= 0){
                                    int nb = Integer.parseInt(nbStr);
                                    dac.setTime(nb);
                                    main.dacsManager.modifyDac(dac);
                                    if(nb != 0) {
                                        sender.sendMessage(ChatColor.GREEN + "Le temps avant de sauter du dé à coudre " + dacName + " est maintenant de " + nb + " secondes");
                                    }else{
                                        sender.sendMessage(ChatColor.GREEN + "Il n'y a plus de temps limite avant de sauter sur le dé à coudre " + dacName);
                                    }
                                    return true;
                                }
                                sender.sendMessage(ChatColor.RED + "Veuillez entrer un nombre valide");
                                return true;
                            }
                            sender.sendMessage(ChatColor.RED + "/dac set <name> time <time(0 = no limit)>");
                            return true;
                        }
                        if(args[2].equalsIgnoreCase("start")){
                            if(args.length == 4){
                                String nbStr = args[3];
                                if(isNumeric(nbStr) && Integer.parseInt(nbStr) >= 1){
                                    int nb = Integer.parseInt(nbStr);
                                    dac.setTimeStart(nb);
                                    main.dacsManager.modifyDac(dac);
                                    sender.sendMessage(ChatColor.GREEN + "Le temps avant de démarrer du dé à coudre " + dacName + " est maintenant de " + nb + " secondes");
                                    return true;
                                }
                                sender.sendMessage(ChatColor.RED + "Veuillez entrer un nombre valide");
                                return true;
                            }
                            sender.sendMessage(ChatColor.RED + "/dac set <name> start <time>");
                            return true;
                        }
                        if(args[2].equalsIgnoreCase("life")){
                            if(args.length == 4){
                                String nbStr = args[3];
                                if(isNumeric(nbStr) && Integer.parseInt(nbStr) > 0){
                                    int nb = Integer.parseInt(nbStr);
                                    dac.setLife(nb);
                                    main.dacsManager.modifyDac(dac);
                                    sender.sendMessage(ChatColor.GREEN + "Le nombre de vie du dé à coudre " + dacName + " est maintenant de " + nb);
                                    return true;
                                }
                                sender.sendMessage(ChatColor.RED + "Veuillez entrer un nombre valide");
                                return true;
                            }
                            sender.sendMessage(ChatColor.RED + "/dac set <name> life <life>");
                            return true;
                        }
                    }
                    sender.sendMessage(ChatColor.RED + "/dac set <name> <spectator/spawn/block/minimum/maximum/dac/time/start/life>");
                    return true;
                }
                if(args[0].equalsIgnoreCase("list")){
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "==========  Liste des dé à coudre ==========");
                    for(DeACoudre dac : main.dacs){
                        sender.sendMessage(ChatColor.GOLD + "- " + dac.getName());
                    }
                    return true;
                }
                if(args[0].equalsIgnoreCase("infos")){
                    if(args.length == 2){
                        String dacName = args[1];
                        if(main.dacsManager.isDac(dacName)){
                            DeACoudre dac = main.dacsManager.getDac(dacName);
                            sender.sendMessage(ChatColor.LIGHT_PURPLE + "========== Infos du dé à coudre " + dacName + " ==========");
                            if(dac.getSpawn() != null) {
                                sender.sendMessage(ChatColor.GOLD + "- Location de spawn: x=" + Math.round(dac.getSpawn().getX()) + ", y=" + Math.round(dac.getSpawn().getY()) + ", z=" + Math.round(dac.getSpawn().getZ()));
                            }else{
                                sender.sendMessage(ChatColor.GOLD + "- Location de spawn: non défini");
                            }
                            if(dac.getSpec() != null) {
                                sender.sendMessage(ChatColor.GOLD + "- Location des spectateurs: x=" + Math.round(dac.getSpec().getX()) + ", y=" + Math.round(dac.getSpec().getY()) + ", z=" + Math.round(dac.getSpec().getZ()));
                            }else{
                                sender.sendMessage(ChatColor.GOLD + "- Location des spectateurs: non défini");
                            }
                            if(dac.getBlock() != null){
                                sender.sendMessage(ChatColor.GOLD + "- Matériau des blocks: " + dac.getBlock().name());
                            }else{
                                sender.sendMessage(ChatColor.GOLD + "- Matériau des blocks: non défini");
                            }
                            if(dac.getDac()){
                                sender.sendMessage(ChatColor.GOLD + "- Dé à coudre: activés");
                            }else{
                                sender.sendMessage(ChatColor.GOLD + "- Dé à coudre: désactivés");
                            }
                            if(dac.getMinP() != 0){
                                sender.sendMessage(ChatColor.GOLD + "- Nombre minimum de joueurs: " + dac.getMinP());
                            }else{
                                sender.sendMessage(ChatColor.GOLD + "- Nombre minimum de joueurs: non défini");
                            }
                            if(dac.getMaxP() != 0){
                                sender.sendMessage(ChatColor.GOLD + "- Nombre maximum de joueurs: " + dac.getMaxP());
                            }else{
                                sender.sendMessage(ChatColor.GOLD + "- Nombre maximum de joueurs: non défini");
                            }
                            sender.sendMessage(ChatColor.GOLD + "- Nombre de vie au démarrage: " + dac.getLife());
                            if(dac.getTime() == 0){
                                sender.sendMessage(ChatColor.GOLD + "- Temps avant de devoir sauter: pas de limite");
                            }else{
                                sender.sendMessage(ChatColor.GOLD + "- Temps avant de devoir sauter: " + dac.getTime() + "s");
                            }
                            sender.sendMessage(ChatColor.GOLD + "- Temps avant de démarrer la partie: " + dac.getTimeStart() + "s");
                            return true;
                        }
                        sender.sendMessage(ChatColor.RED + "Il n'y a pas de dé à coudre nommé " + dacName);
                        return true;
                    }
                    sender.sendMessage(ChatColor.RED + "/dac infos <name>");
                    return true;
                }

            }
            sender.sendMessage(ChatColor.RED + "/dac <create/remove/set/list/infos>");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Vous devez être un joueur");
        return false;
    }

    private boolean isNumeric(String strNum) {
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

}
