package com.LouDBasS_101.modules;

import eu.darkbot.api.game.entities.Entity;
import eu.darkbot.api.managers.BotAPI;
import eu.darkbot.api.managers.HeroAPI;
import eu.darkbot.api.managers.MovementAPI;
import eu.darkbot.shared.modules.LootCollectorModule;

import eu.darkbot.api.PluginAPI;

public class SmartKillAndCollect extends LootCollectorModule {

    // Variables para almacenar información sobre la muerte y la posición
    private final HeroAPI hero;
    private final PluginAPI api;
    private final BotAPI bot;
    private final MovementAPI movement;
    private Entity currentTarget;
    private double dropPositionX;
    private double dropPositionY;
    private boolean flag = false;

    public SmartKillAndCollect(PluginAPI api, BotAPI bot, HeroAPI hero, MovementAPI movement) {
        super(api);
        this.api = api;
        this.bot = bot;
        this.hero = hero;
        this.movement = movement;
    }

    @Override
    public void onTickModule() {
        this.currentTarget = hero.getLocalTarget();
        // Lógica principal que se ejecuta en cada tick

        if (currentTarget != null) {
            dropPositionX = currentTarget.getX();  // Capturar la posición de muerte
            dropPositionY = currentTarget.getY();
            flag = true;
        } else if(flag) {
            movement.moveTo(dropPositionX, dropPositionY);
            flag = false;
        }
    }
}

