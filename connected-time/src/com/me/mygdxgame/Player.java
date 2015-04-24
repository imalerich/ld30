package com.me.mygdxgame;

import java.util.Iterator;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Entity
{
	private AnimImage _anim;
	private Body _body;
	private CircleShape _circle;
	private Fixture _fix;
	private boolean _canjump; //can only jump when prev velocity negative and current is 0
	
	private float _direction;
	private boolean _moving;
	private boolean _canshoot;
	
	private Vector<Arrow> _arrows;
	
	public void Init(World Physics)
	{
		//set entity data
		_name = "Player";
		
		//load the animation
		_anim = new AnimImage("img/player.png", 1, 4, 2);
		_anim.NewAnimation(0, 1, 0, 0, 0.15f);
		_anim.NewAnimation(1, 4, 0, 3, 0.15f);
		
		_direction = 1.0f;
		_moving = false;
		
		//create the physics body
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(1+128/16, 112/16+2);
		
		_body = Physics.createBody(bodyDef);
		_body.setFixedRotation(true);
		_circle = new CircleShape();
		_circle.setRadius(1.0f);
		
		FixtureDef fixd = new FixtureDef();
		fixd.shape = _circle;
		fixd.density = 0.0f;
		fixd.friction = 0.0f;
		
		_fix = _body.createFixture(fixd);
		
		_arrows = new Vector<Arrow>();
		_canjump = true;
		_canshoot = true;
	}
	
	public void Release()
	{
		_body.destroyFixture(_fix);
		_circle.dispose();
	}
	
	public void Update(World Physics)
	{
		Vector2 vel = _body.getLinearVelocity();
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			//_body.applyLinearImpulse(-40.0f, 0, pos.x, pos.y, true);
			_body.setLinearVelocity( new Vector2(-13, vel.y) );
			_direction = -1.0f;
			_moving = true;
		} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			//_body.applyLinearImpulse(4000.0f, 0, pos.x, pos.y, true);
			_body.setLinearVelocity( new Vector2(13, vel.y) );
			_direction = 1.0f;
			_moving = true;
		} else {
			_body.setLinearVelocity( new Vector2(0, vel.y) );
			_moving = false;
		}
		
		if (Gdx.input.isKeyPressed(Keys.UP) && vel.y == 0.0f && _canjump)
			_body.setLinearVelocity( new Vector2(vel.x, 30) );
		
		//can jump when velocity is 0, but only because on ground (not peak of path)
		if (vel.y != 0 && vel.y > 0.0f)
			_canjump = false;
		else if (vel.y < 0.0f)
			_canjump = true;
		
		//add arrows that the player has fired
		if ( Gdx.input.isKeyPressed(Keys.SPACE) && _canshoot) {
			Vector2 pos = _body.getPosition();
			pos.x += _direction*0.7f;
			_arrows.add( new Arrow( pos, new Vector2(_direction*30.0f, 0.0f), Physics) );
			_canshoot = false;
		} else if ( !Gdx.input.isKeyPressed(Keys.SPACE) )
			_canshoot = true;
	}

	public void Render(SpriteBatch Batch, float Campos)
	{
		Vector2 pos = _body.getPosition();
		pos.x *= 16; pos.y *= 16;
		
		pos.x -= (Campos + 16);
		pos.y -= 16;
		
		if (_moving)
			_anim.Render( Batch, 1, pos, _direction, 1.0f );
		else _anim.Render( Batch, 0, pos, _direction, 1.0f );
		
		for (Iterator<Arrow> i = _arrows.iterator(); i.hasNext();)
		{
			Arrow current = i.next();
			
			//check if the current arrow should be destroyed or has fallen off the map
			if (current._health == 0 || current.GetPos().y < 0.0f) {
				current.Release();
				i.remove();
			}
			
			current.Render(Batch, Campos);
		}
	}
	
	public Vector2 GetPos()
	{
		return _body.getPosition();
	}
}
