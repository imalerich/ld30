package com.me.mygdxgame;

public class Entity 
{
	public int _health;
	public int _damage;
	
	public boolean _ishit;
	public String _name;
	
	Entity()
	{
		_health = 100;
		_damage = 0;
		
		_ishit = false;
		_name = "";
	}
}
