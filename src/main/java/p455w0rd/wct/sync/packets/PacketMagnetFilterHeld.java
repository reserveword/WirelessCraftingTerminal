/*
 * This file is part of Wireless Crafting Terminal. Copyright (c) 2017, p455w0rd
 * (aka TheRealp455w0rd), All rights reserved unless otherwise stated.
 *
 * Wireless Crafting Terminal is free software: you can redistribute it and/or
 * modify it under the terms of the MIT License.
 *
 * Wireless Crafting Terminal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the MIT License for
 * more details.
 *
 * You should have received a copy of the MIT License along with Wireless
 * Crafting Terminal. If not, see <https://opensource.org/licenses/MIT>.
 */
package p455w0rd.wct.sync.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import p455w0rd.ae2wtlib.api.networking.INetworkInfo;
import p455w0rd.wct.items.ItemMagnet;
import p455w0rd.wct.items.ItemMagnet.MagnetItemMode;
import p455w0rd.wct.sync.WCTPacket;

public class PacketMagnetFilterHeld extends WCTPacket {

	// 0 = initialize
	// 1 = whitelist/blacklist
	// 2 = ignore NBT
	// 3 = ignore meta
	// 4 = use oredict
	MagnetItemMode whichMode;
	boolean modeValue;

	public PacketMagnetFilterHeld(final ByteBuf stream) {
		whichMode = MagnetItemMode.VALUES[stream.readInt()];
		modeValue = stream.readBoolean();
	}

	public PacketMagnetFilterHeld(final MagnetItemMode mode, final boolean modeVal) {
		modeValue = modeVal;
		whichMode = mode;
		final ByteBuf data = Unpooled.buffer();
		data.writeInt(getPacketID());
		data.writeInt(whichMode.ordinal());
		data.writeBoolean(modeValue);
		configureWrite(data);
	}

	@Override
	public void serverPacketData(final INetworkInfo manager, final WCTPacket packet, final EntityPlayer player) {
		ItemStack magnet = ItemMagnet.getHeldMagnet(player);
		if (!magnet.isEmpty()) {
			ItemMagnet.setItemMode(magnet, whichMode, modeValue);
		}
	}

}
