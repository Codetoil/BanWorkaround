package io.github.codetoil.ban_workaround;

import net.fabricmc.api.Environment;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(net.fabricmc.api.EnvType.CLIENT)
public class BanWorkaroundClient implements ClientModInitializer
{
	public static final Logger LOGGER = LoggerFactory.getLogger("ban_workaround");

	@Override
	public void onInitializeClient()
	{
	}
}
