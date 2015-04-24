package com.me.mygdxgame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Arrow extends Entity
{
	public Vector2 _vel;
	private static Image _img;
	
	private Body _body;
	private PolygonShape _square;
	private Fixture _fix;
	private float _xscale;
	
	Arrow(Vector2 Position, Vector2 Velocity, World Physics)
	{
		//set Entity data
		_name = "Arrow";
		
		//load the arrows image (only once, _img is static)
		if (_img == null)
			_img = new Image("img/arrow.png", 32, 32);
		
		//create the physics body
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set( Position );
		
		_body = Physics.createBody(bodyDef);
		_body.setFixedRotation(true);
		_square = new PolygonShape();
		_square.setAsBox(0.2f, 0.2f);
		
		FixtureDef fixd = new FixtureDef();
		fixd.shape = _square;
		fixd.density = 0.0f;
		fixd.friction = 0.0f;
		
		_fix = _body.createFixture(fixd);
		
		//constant velocity with no acceleration from gravity
		_body.setGravityScale( 0.015f );
		_body.setLinearVelocity( Velocity );
		_body.setUserData(this);
		
		if (Velocity.x > 0.0f) _xscale = 1.0f;
		else _xscale = -1.0f;
	}
	
	public void Release()
	{
		_body.destroyFixture(_fix);
	}
	
	public void Render(SpriteBatch Batch, float Campos)
	{
		Vector2 pos = _body.getPosition();
		pos.x *= 16; pos.y *= 16;
		
		pos.x -= (Campos + 16);
		pos.y -= 16;
		
		_img.Render(Batch, pos.x, pos.y, _xscale, 1.0f, 1.0f);
	}
	
	public Vector2 GetVel()
	{
		return _body.getLinearVelocity();
	}
	
	public Vector2 GetPos()
	{
		return _body.getPosition();
	}
}
