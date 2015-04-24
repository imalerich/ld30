package com.me.mygdxgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LD30Gamei implements ApplicationListener 
{
	private OrthographicCamera _camera;
	private SpriteBatch _batch;
	
	public static float SCREENW = 800;
	public static float SCREENH = 600;
	
	private Game _mygame;
		
	@Override
	public void create() 
	{		
		_camera = new OrthographicCamera();
		_camera.setToOrtho(false, SCREENW, SCREENH);
		_batch = new SpriteBatch();
		_mygame = new Game();
		_mygame.Init();
	}

	@Override
	public void dispose() 
	{
		_batch.dispose();
		_mygame.Release();
	}

	@Override
	public void render() 
	{	
		//update game logic
		_mygame.Update();
		
		//render the game
		Clock.Update();
		BeginDraw();
		
		_mygame.Render(_batch);
		
		EndDraw();
	}
	
	private void BeginDraw()
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		_batch.setProjectionMatrix(_camera.combined);
		_batch.begin();
	}
	
	private void EndDraw()
	{
		_batch.end();
	}

	@Override
	public void resize(int width, int height) 
	{
		//
	}

	@Override
	public void pause() 
	{
		//
	}

	@Override
	public void resume() 
	{	//
	}
}
