package com.me.mygdxgame;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class Listener implements ContactListener 
{
	@Override
	public void endContact(Contact contact)
	{
		Entity fA = (Entity)contact.getFixtureA().getBody().getUserData();
		Entity fB = (Entity)contact.getFixtureB().getBody().getUserData();
		
		if (fA != null)
			fA._ishit = false;
		if (fB != null)
			fB._ishit = false;
	}
	
	@Override
	public void beginContact(Contact contact)
	{
		Entity fA = (Entity)contact.getFixtureA().getBody().getUserData();
		Entity fB = (Entity)contact.getFixtureB().getBody().getUserData();
		
		if (fA != null)
			fA._ishit = true;
		if (fB != null)
			fB._ishit = true;
		
		//no more entity specific processing necessary
		if (fA == null || fB == null) return;
		
		if (fA._name == "Arrow")
			if (fB._name != "Player")
				fA._health = 0;
		
		if (fB._name == "Arrow")
			if (fA._name != "Player")
				fB._health = 0;
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		//necessary
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		//necessary
	}
};
