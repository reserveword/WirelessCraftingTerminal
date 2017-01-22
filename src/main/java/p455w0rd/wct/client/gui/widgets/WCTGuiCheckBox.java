package p455w0rd.wct.client.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.client.config.*;

public class WCTGuiCheckBox extends GuiCheckBox {

	private boolean isChecked;
	private int boxWidth;

	public WCTGuiCheckBox(int id, int xPos, int yPos, String displayString, boolean isChecked, int width) {
		super(id, xPos, yPos, displayString, isChecked);
		this.isChecked = isChecked;
		boxWidth = 11;
		height = 11;
		//this.width = this.boxWidth + 2 + Minecraft.getMinecraft().fontRenderer.getStringWidth(displayString);
		this.width = width;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (visible) {
			hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + boxWidth && mouseY < yPosition + height;
			GuiUtils.drawContinuousTexturedBox(BUTTON_TEXTURES, xPosition + (width - boxWidth), yPosition, 0, 46, boxWidth, height, 200, 20, 2, 3, 2, 2, zLevel);
			mouseDragged(mc, mouseX, mouseY);
			int color = 14737632;

			if (!enabled) {
				color = 10526880;
			}
			else {
				color = 4210752;
			}
			if (isChecked) {
				drawCenteredString(mc.fontRendererObj, "✔", xPosition + (width - boxWidth) + boxWidth / 2 + 1, yPosition + 1, 0x00FF00);
			}

			drawStringNoShadow(mc.fontRendererObj, displayString, xPosition, yPosition + 2, color);
		}
	}

	public void drawStringNoShadow(FontRenderer p_73731_1_, String p_73731_2_, int p_73731_3_, int p_73731_4_, int p_73731_5_) {
		p_73731_1_.drawString(p_73731_2_, p_73731_3_, p_73731_4_, p_73731_5_);
	}

	@Override
	public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_) {
		if (enabled && visible && p_146116_2_ >= xPosition && p_146116_3_ >= yPosition && p_146116_2_ < xPosition + width && p_146116_3_ < yPosition + height) {
			isChecked = !isChecked;
			return true;
		}

		return false;
	}

	@Override
	public boolean isChecked() {
		return isChecked;
	}

	@Override
	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

}
