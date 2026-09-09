package com.example;

import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

import javax.inject.Inject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class OrbOverlay extends Overlay
{
    private final Client client;
    private final ExamplePlugin plugin;

    @Inject
    public OrbOverlay(Client client, ExamplePlugin plugin)
    {
        this.client = client;
        this.plugin = plugin;

        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        int count = 0;

        for (LocalPoint target : plugin.getYellowTargets())
        {
            Polygon tilePoly =
                    Perspective.getCanvasTilePoly(client, target);

            if (tilePoly == null)
            {
                continue;
            }

            OverlayUtil.renderPolygon(
                    graphics,
                    tilePoly,
                    Color.CYAN
            );

            count++;
        }

        // Temporary debug counter
        graphics.setColor(Color.MAGENTA);
        graphics.setFont(new Font("Arial", Font.BOLD, 20));
        graphics.drawString(
                "YELLOW TILES DRAWN: " + count,
                200,
                200
        );

        return null;
    }
}