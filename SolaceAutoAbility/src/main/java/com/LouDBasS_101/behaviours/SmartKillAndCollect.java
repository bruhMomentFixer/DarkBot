package com.LouDBasS_101.behaviours;

import eu.darkbot.api.game.entities.Entity;
import eu.darkbot.api.managers.BotAPI;
import eu.darkbot.api.managers.HeroAPI;
import eu.darkbot.api.managers.EventBrokerAPI;
import eu.darkbot.api.managers.MovementAPI;
import eu.darkbot.api.managers.GameLogAPI.LogMessageEvent;
import eu.darkbot.shared.modules.CollectorModule;

import eu.darkbot.api.PluginAPI;
import eu.darkbot.api.events.Event;
import eu.darkbot.api.events.EventHandler;
import eu.darkbot.api.events.Listener;
import eu.darkbot.api.extensions.Behavior;
import eu.darkbot.api.extensions.Feature;

@Feature(name = "SmartKillAndCollect", description =  "Collects all the drops from killed NPCs.")

public class SmartKillAndCollect implements Behavior, Listener {

    // Variables para almacenar información sobre la muerte y la posición
    private final HeroAPI hero;
    private final EventBrokerAPI eventBrokerAPI;
    private final Listener listener;
    private final MovementAPI movement;
    private final CollectorModule collectorModule;
    private Entity currentTarget;
    private double dropPositionX;
    private double dropPositionY;

    public SmartKillAndCollect(HeroAPI hero, MovementAPI movement, CollectorModule collectorModule, EventBrokerAPI eventBrokerAPI, Listener listener) {
        this.hero = hero;
        this.movement = movement;
        this.collectorModule = collectorModule;

        this.eventBrokerAPI = eventBrokerAPI;
        this.listener = listener;
        eventBrokerAPI.registerListener(listener);
    }

    @Override
    public void onTickBehavior() {
        this.currentTarget = hero.getLocalTarget();
        
        if (currentTarget != null) {
            //System.out.println("cap death");
            dropPositionX = currentTarget.getX();  // Capturar la posición de muerte
            dropPositionY = currentTarget.getY();
        }
    }

    @EventHandler
    public void listenKill(LogMessageEvent event) {
        collectorModule.findBox();
        if(event.getMessage().contains("recibes ninguna") || 
        event.getMessage().contains("ditos")) { 
            moveToAndCollect(dropPositionX, dropPositionY); 
        }
    }

    public void moveToAndCollect(double dropPositionX, double dropPositionY) {
        System.out.println("\nMOVE\n");
        movement.moveTo(dropPositionX, dropPositionY);
        collectorModule.tryCollectNearestBox();
    }

    
}


