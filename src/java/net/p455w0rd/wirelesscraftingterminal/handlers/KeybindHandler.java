package net.p455w0rd.wirelesscraftingterminal.handlers;

import org.lwjgl.input.Keyboard;

import appeng.util.Platform;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.p455w0rd.wirelesscraftingterminal.client.gui.GuiWirelessCraftingTerminal;
import net.p455w0rd.wirelesscraftingterminal.common.utils.RandomUtils;
import net.p455w0rd.wirelesscraftingterminal.core.sync.network.NetworkHandler;
import net.p455w0rd.wirelesscraftingterminal.core.sync.packets.PacketOpenGui;
import net.p455w0rd.wirelesscraftingterminal.core.sync.packets.PacketSwitchMagnetMode;
import net.p455w0rd.wirelesscraftingterminal.items.ItemMagnet;
import net.p455w0rd.wirelesscraftingterminal.items.ItemWirelessCraftingTerminal;
import net.p455w0rd.wirelesscraftingterminal.reference.Reference;

@SideOnly(Side.CLIENT)
public class KeybindHandler {

	public static KeyBinding openTerminal;
	public static KeyBinding openMagnetFilter;
	public static KeyBinding changeMagnetMode;

	public static void registerKeybinds() {
		openTerminal = new KeyBinding("key.OpenTerminal", Keyboard.CHAR_NONE, "key.categories.ae2wct");
		openMagnetFilter = new KeyBinding("key.OpenMagnetFilter", Keyboard.CHAR_NONE, "key.categories.ae2wct");
		changeMagnetMode = new KeyBinding("key.SwitchMagnetMode", Keyboard.CHAR_NONE, "key.categories.ae2wct");
		ClientRegistry.registerKeyBinding(openTerminal);
		ClientRegistry.registerKeyBinding(openMagnetFilter);
		ClientRegistry.registerKeyBinding(changeMagnetMode);
	}

	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		EntityClientPlayerMP p = (EntityClientPlayerMP) Minecraft.getMinecraft().thePlayer;
		if (p.openContainer == null) {
			return;
		}
		if (p.openContainer != null && p.openContainer instanceof ContainerPlayer) {
			if (openTerminal.isPressed()) {
				if (!FMLClientHandler.instance().isGUIOpen(GuiWirelessCraftingTerminal.class)) {
					NetworkHandler.instance.sendToServer(new PacketOpenGui(Reference.GUI_WCT));
				}
			}
			else if (openMagnetFilter.isPressed()) {
				ItemWirelessCraftingTerminal wirelessTerm = (ItemWirelessCraftingTerminal) RandomUtils.getWirelessTerm(p.inventory).getItem();
				//ensure player has a Wireless Crafting Terminal (with Magnet Card Installed) or Magnet Card in their inventory
				if (wirelessTerm != null && wirelessTerm instanceof ItemWirelessCraftingTerminal) {
					ItemStack magnetItem = RandomUtils.getMagnet(p.inventory);
					if (magnetItem != null && magnetItem.getItem() instanceof ItemMagnet) {
						NetworkHandler.instance.sendToServer(new PacketOpenGui(Reference.GUI_MAGNET));
					}
				}
			}
			else if (changeMagnetMode.isPressed()) {
				ItemStack magnetItem = RandomUtils.getMagnet(p.inventory);
				if (magnetItem != null) {
					NetworkHandler.instance.sendToServer(new PacketSwitchMagnetMode(magnetItem.getItemDamage()));
				}
			}
			else {
				return;
			}
		}
	}
}
