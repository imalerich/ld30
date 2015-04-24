package com.me.mygdxgame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Game 
{
	private Player _usr;
	public Map _worldmap;
	public Map _detailmap;
	public float _campos;
	
	public World _physics;
	
	public void Init()
	{	
		_physics = new World(new Vector2(0, -100), true);
		_physics.setContactListener( new Listener() );
		
		try {
			_worldmap = new Map();
			//_worldmap.Init("C:/Users/Ian/Copy/ld30/connected-time-android/assets/map/main.txt", "img/tilesheet.png",  100, 19);
			//_worldmap.Init("/home/imalerich/Copy/ld30/connected-time-android/assets/map/main.txt", "img/tilesheet.png",  100, 19);
			_worldmap.Init("map/main.txt", "img/tilesheet.png", 100, 19);
			_worldmap.GenereateCollisionData(_physics);
			
			_detailmap = new Map();
			//_detailmap.Init("C:/Users/Ian/Copy/ld30/connected-time-android/assets/map/detail.txt", "img/tilesheet.png", 100, 19);
			//_detailmap.Init("/home/imalerich/Copy/ld30/connected-time-android/assets/map/detail.txt", "img/tilesheet.png", 100, 19);
			_detailmap.Init("map/detail.txt", "img/tilesheet.png", 100, 19);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		_usr = new Player();
		_usr.Init(_physics);
	}
	
	public void Update()
	{
		_usr.Update(_physics);
	}
	
	public void Release()
	{
		_usr.Release();
		_worldmap.Release();
	}
	
	public void Render(SpriteBatch Batch)
	{
		_campos = _usr.GetPos().x*16 - LD30Gamei.SCREENW/2.0f + 16;
		if (_campos < 0.0f) _campos = 0.0f;
		else if (_campos > _worldmap._width*32 - LD30Gamei.SCREENW)
			_campos = _worldmap._width*32 - LD30Gamei.SCREENW;
		
		_detailmap.Render(Batch, _campos);
		_worldmap.Render(Batch, _campos);
		_usr.Render(Batch, _campos);
		
		_physics.step(1/60f, 6, 2);
	}

}
