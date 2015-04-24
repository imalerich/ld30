package com.me.mygdxgame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.Integer;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Map 
{
	//graphical representations
	private TileSheet _spritesheet;
	private Vector<Integer> _mapdata;
	private Vector<Body> _bodies;
	private PolygonShape _square;
	private Entity _collinfo;
	
	public int _width;
	public int _height;
	
	public void Init(String Mapname, String TileSheet, int Width, int Height) throws Exception
	{
		_collinfo = new Entity();
		_collinfo._name = "Map";
		
		_mapdata = new Vector<Integer>();
		_width = Width;
		_height = Height;
		
		System.out.println("Local storage - " + Gdx.files.getExternalStoragePath() );
		BufferedReader csv = new BufferedReader( new InputStreamReader( 
				Gdx.files.internal(Mapname).read() ) );
		String data = csv.readLine();
		
		while (data != null)
		{
			String[] dataArray = data.split(",");
			
			for (int i=0; i<dataArray.length; i++) {
				dataArray[i] = dataArray[i].replaceAll("\\s+", "");
				if (dataArray[i].length() > 0)
					_mapdata.add( Integer.parseInt(dataArray[i]) );
			}
			
			data = csv.readLine();
		}
		
		csv.close();
	
		_spritesheet = new TileSheet(TileSheet, 32, 32);
	}
	
	public void GenereateCollisionData(World Physics)
	{
		_square = new PolygonShape();
		_square.setAsBox(1.0f, 1.0f);
		_bodies = new Vector<Body>();
		
		//create the physics representation of the world
		for (int y=0; y<_height; y++)
		{
			for (int x=0; x<_width; x++)
			{
				int index = y*_width+x;
				index = _mapdata.get(index);
				
				if (index != 0)
					AddCollisionPoint(x*32+16, _height*32 - y*32-16, Physics);
			}
		}
	}
	
	private void AddCollisionPoint(float X, float Y, World Physics)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set( new Vector2(X/16.0f, Y/16.0f) );
		
		//physics body
		_bodies.add( Physics.createBody(bodyDef) );
		_bodies.lastElement().createFixture(_square, 0.0f);
		_bodies.lastElement().setUserData(_collinfo);
	}
	
	public void Release()
	{
		_square.dispose();
	}
	
	public void Render(SpriteBatch Batch, float Campos)
	{	
		int startx = (int)(Campos/32.0f)-1;
		if (startx<0) startx = 0;
		int endx = startx+2+(int)(LD30Gamei.SCREENW/32.0f);
		if (endx>_width) endx = _width;
		
		for (int y=0; y<_height; y++)
		{
			for (int x = startx; x<endx; x++)
			{
				int index = y*_width + x;
				index = _mapdata.get(index);
				
				Vector2 pos = new Vector2(x*32, _height*32 - (y+1)*32);
				pos.x -= Campos;
				
				_spritesheet.Render(Batch, pos, index-1);
			}
		}
	}
}
