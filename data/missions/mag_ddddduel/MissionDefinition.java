package data.missions.mag_ddddduel;

import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.fleet.FleetGoal;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.mission.FleetSide;
import com.fs.starfarer.api.mission.MissionDefinitionAPI;
import com.fs.starfarer.api.mission.MissionDefinitionPlugin;

public class MissionDefinition implements MissionDefinitionPlugin {

	public void defineMission(MissionDefinitionAPI api) {
		
		// Set up the fleets
		api.initFleet(FleetSide.PLAYER, "UF", FleetGoal.ATTACK, false);
		api.initFleet(FleetSide.ENEMY, "IO", FleetGoal.ATTACK, false);

		// Set a blurb for each fleet
		api.setFleetTagline(FleetSide.PLAYER, "The Unstoppable Force");
		api.setFleetTagline(FleetSide.ENEMY, "The Immovable Object");
		
		// These show up as items in the bulleted list under 
		// "Tactical Objectives" on the mission detail screen
		api.addBriefingItem("An Unstoppable Force vs An Immovable Object");
		
		// Set up the player's fleet
		api.addToFleet(FleetSide.PLAYER, "idx_magnetar_va", FleetMemberType.SHIP, "The Unstoppable Force", true).getCaptain().setPersonality("reckless");
		
		// Set up the enemy fleet
		api.addToFleet(FleetSide.ENEMY, "idx_magnetar_va", FleetMemberType.SHIP, "The Immovable Object", true).getCaptain().setPersonality("reckless");
		
        api.getContext().fightToTheLast = true;

 		// Set up the map.
		float width = 18000f;
		float height = 13500f;
		api.initMap((float)-width/2f, (float)width/2f, (float)-height/2f, (float)height/2f);
		
		for (int i = 0; i < 50; i++) {
			float x = (float) Math.random() * width - width/2;
			float y = (float) Math.random() * height - height/2;
			float radius = 100f + (float) Math.random() * 400f; 
			api.addNebula(x, y, radius);
		}
		
		String [] planets = {"barren", "lava", "lava_minor", "frozen1", "cryovolcanic", "frozen", "barren_castiron", "desert", "bombarded"};
		String planet = planets[(int) (Math.random() * (double) planets.length)];
		float radius = 100f + (float) Math.random() * 150f;
		api.addPlanet(0, 0, radius, planet, 200f, true);
	}

}