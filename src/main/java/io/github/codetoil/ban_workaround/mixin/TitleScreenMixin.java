package io.github.codetoil.ban_workaround.mixin;

import com.mojang.authlib.minecraft.BanDetails;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
@Environment(EnvType.CLIENT)
public abstract class TitleScreenMixin extends Screen
{

	protected TitleScreenMixin(Text title)
	{
		super(title);
	}

	/**
	 * @author Codetoil
	 * @reason Ensure that the Ban details show even if multiplayer is enabled.
	 */
	@Overwrite
	@Nullable
	private Text getMultiplayerDisabledText() {
		if (this.client == null) return null;
		BanDetails banDetails = this.client.getMultiplayerBanDetails();

		if (this.client.isMultiplayerEnabled()) {
			if (banDetails != null) {
				return banDetails.expires() != null ? Text.translatable("title.multiplayer.disabled.banned.temporary") : Text.translatable("title.multiplayer.disabled.banned.permanent");
			} else {
				return null;
			}
		} else {
			if (banDetails != null) {
				return banDetails.expires() != null ? Text.translatable("title.multiplayer.disabled.banned.temporary") : Text.translatable("title.multiplayer.disabled.banned.permanent");
			} else {
				return Text.translatable("title.multiplayer.disabled");
			}
		}
	}

	@Inject(at = @At("TAIL"), method="initWidgetsNormal(II)V")
	private void initWidgetsNormal(int y, int spacingY, CallbackInfo ci) {
		this.children().forEach((e) -> {
			if (e instanceof ButtonWidget &&
					((ButtonWidget) e).getMessage().getContent() instanceof TranslatableTextContent
			&& (((TranslatableTextContent) ((ButtonWidget) e).getMessage().getContent()).getKey().equals("menu.multiplayer")
			|| ((TranslatableTextContent) ((ButtonWidget) e).getMessage().getContent()).getKey().equals("menu.online"))) {
				((ButtonWidget) e).active = true;
			}
		});
	}
}
