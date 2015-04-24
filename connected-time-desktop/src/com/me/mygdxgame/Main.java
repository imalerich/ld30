package com.me.mygdxgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main 
{
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "connected-time";
		cfg.useGL20 = false;
		cfg.width = 800;
		cfg.height = 600;
		cfg.resizable = false;
		cfg.x = -1;
		cfg.y = -1;
		
		new LwjglApplication(new LD30Gamei(), cfg);
	}
}
