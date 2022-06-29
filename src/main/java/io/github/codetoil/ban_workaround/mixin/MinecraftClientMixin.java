package io.github.codetoil.ban_workaround.mixin;

import com.mojang.authlib.minecraft.UserApiService;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MinecraftClient.class)
@Environment(EnvType.CLIENT)
public abstract class MinecraftClientMixin
{
	@Shadow
	@Final
	private boolean multiplayerEnabled;

	@Shadow
	@Final
	private UserApiService userApiService;

	/**
	 * @author Codetoil
	 * @reason Remove the method's dependency on {@link MinecraftClient#getMultiplayerBanDetails() getMultiplayerBanDetails()}
	 */
	@Overwrite
	public boolean isMultiplayerEnabled() {
		return this.multiplayerEnabled && this.userApiService.properties().flag(UserApiService.UserFlag.SERVERS_ALLOWED);
	}

	/**
	 * @author Codetoil
	 * @reason Remove the method's dependency on {@link MinecraftClient#getMultiplayerBanDetails() getMultiplayerBanDetails()}
	 */
	@Overwrite
	public boolean isRealmsEnabled() {
		return this.userApiService.properties().flag(UserApiService.UserFlag.REALMS_ALLOWED);
	}
}
