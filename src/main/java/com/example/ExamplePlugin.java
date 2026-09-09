package com.example;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Projectile;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.ProjectileMoved;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@PluginDescriptor(
		name = "Verzik Yellow Orbs"
)
public class ExamplePlugin extends Plugin
{
	private static final int YELLOW_PROJECTILE_ID = 1596;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private OrbOverlay orbOverlay;

	private final Set<LocalPoint> yellowTargets = new HashSet<>();

	@Override
	protected void startUp()
	{
		yellowTargets.clear();
		overlayManager.add(orbOverlay);

		log.info("Verzik Yellow Orbs started!");
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(orbOverlay);
		yellowTargets.clear();

		log.info("Verzik Yellow Orbs stopped!");
	}

	@Subscribe
	public void onProjectileMoved(ProjectileMoved event)
	{
		Projectile projectile = event.getProjectile();

		if (projectile.getId() != YELLOW_PROJECTILE_ID)
		{
			return;
		}

		LocalPoint target = event.getPosition();

		if (target == null)
		{
			return;
		}

		if (yellowTargets.add(target))
		{
			log.info("ADDING YELLOW TILE TO OVERLAY: {}", target);
		}
	}

	Set<LocalPoint> getYellowTargets()
	{
		return yellowTargets;
	}
}