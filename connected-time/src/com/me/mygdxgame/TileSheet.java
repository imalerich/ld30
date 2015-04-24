package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class TileSheet 
{
	private Texture _img;
	private TextureRegion[][] _reg;
	
	private int _columns;
	
	public TileSheet(String Filename, int Width, int Height)
	{
		_img = new Texture(Gdx.files.internal(Filename));
		_reg = TextureRegion.split(_img, Width, Height);
		
		_columns = _reg[0].length;
	}
	
	public void Render(SpriteBatch Batch, Vector2 Pos, int Index)
	{
		if (Index < 0) return;
		
		int y = Index/_columns;
		int x = Index - _columns*y;
		
		Batch.draw(_reg[y][x], Pos.x, Pos.y);
	}
}
