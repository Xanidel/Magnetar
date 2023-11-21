package data.missions.magnetar;

import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.fleet.FleetGoal;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.mission.FleetSide;
import com.fs.starfarer.api.mission.MissionDefinitionAPI;
import com.fs.starfarer.api.mission.MissionDefinitionPlugin;

public class MissionDefinition implements MissionDefinitionPlugin {

	private List ships = new ArrayList();
	private void addShip(String variant, int weight) {
		for (int i = 0; i < weight; i++) {
			ships.add(variant);
		}
	}
	
	private void generateFleet(int maxFP, FleetSide side, List ships, MissionDefinitionAPI api) {
		int currFP = 0;
		
		if (side == FleetSide.PLAYER) {
			String [] choices = {
					"onslaught_Elite",
					"astral_Strike",
					"paragon_Elite",
					"odyssey_Balanced",
					"legion_Strike",
					"legion_FS",
					"doom_Strike"
			};
			String flagship = choices[(int) (Math.random() * (float) choices.length)];
			api.addToFleet(side, flagship, FleetMemberType.SHIP, true);
			currFP += api.getFleetPointCost(flagship);
		}
		
		while (true) {
			int index = (int)(Math.random() * ships.size());
			String id = (String) ships.get(index);
			currFP += api.getFleetPointCost(id);
			if (currFP > maxFP) {
				return;
			}
			
			if (id.endsWith("_wing")) {
				api.addToFleet(side, id, FleetMemberType.FIGHTER_WING, false);
			} else {
				api.addToFleet(side, id, FleetMemberType.SHIP, false);
			}
		}
	}
	
	public void defineMission(MissionDefinitionAPI api) {

		addShip("doom_Strike", 3);
		addShip("shade_Assault", 7);
		addShip("afflictor_Strike", 7);
		addShip("hyperion_Attack", 3);
		addShip("hyperion_Strike", 3);
		addShip("onslaught_Standard", 3);
		addShip("onslaught_Outdated", 3);
		addShip("onslaught_Elite", 1);
		addShip("astral_Elite", 3);
		addShip("astral_Strike", 3);
		addShip("astral_Attack", 3);
		addShip("paragon_Elite", 1);
		addShip("legion_Strike", 1);
		addShip("legion_Assault", 1);
		addShip("legion_Escort", 1);
		addShip("legion_FS", 1);
		addShip("odyssey_Balanced", 2);
		addShip("conquest_Elite", 3);
		addShip("eagle_Assault", 5);
		addShip("falcon_Attack", 5);
		addShip("venture_Balanced", 5);
		addShip("apogee_Balanced", 5);
		addShip("aurora_Balanced", 5);
		addShip("aurora_Balanced", 5);
		addShip("gryphon_FS", 7);
		addShip("gryphon_Standard", 7);
		addShip("mora_Assault", 3);
		addShip("mora_Strike", 3);
		addShip("mora_Support", 3);
		addShip("dominator_Assault", 5);
		addShip("dominator_Support", 5);
		addShip("medusa_Attack", 5);
		addShip("condor_Support", 15);
		addShip("condor_Strike", 15);
		addShip("condor_Attack", 15);
		addShip("enforcer_Assault", 15);
		addShip("enforcer_CS", 15);
		addShip("hammerhead_Balanced", 10);
		addShip("hammerhead_Elite", 5);
		addShip("drover_Strike", 10);
		addShip("sunder_CS", 10);
		addShip("gemini_Standard", 8);
		addShip("buffalo2_FS", 20);
		addShip("lasher_CS", 20);
		addShip("lasher_Standard", 20);
		addShip("hound_Standard", 15);
		addShip("tempest_Attack", 15);
		addShip("brawler_Assault", 15);
		addShip("wolf_CS", 2);
		addShip("hyperion_Strike", 1);
		addShip("vigilance_Standard", 10);
		addShip("vigilance_FS", 15);
		addShip("tempest_Attack", 2);
		addShip("brawler_Assault", 10);
		
		// Set up the fleets
		api.initFleet(FleetSide.PLAYER, "AS", FleetGoal.ATTACK, true);
		api.initFleet(FleetSide.ENEMY, "HHP", FleetGoal.ATTACK, false);

		// Set a blurb for each fleet
		api.setFleetTagline(FleetSide.PLAYER, "The Last Armada of the Sector");
		api.setFleetTagline(FleetSide.ENEMY, "An invincible Magnetar-class Super Capital!");
		
		// These show up as items in the bulleted list under 
		// "Tactical Objectives" on the mission detail screen
		api.addBriefingItem("Defeat the terrifying Magnetar-class. IF! YOU! CAN!!");
		
		// Set up the player's fleet
		generateFleet(500 + (int)((float) Math.random() * 50), FleetSide.PLAYER, ships, api);
		
		// Set up the enemy fleet
		api.addToFleet(FleetSide.ENEMY, "idx_magnetar_va", FleetMemberType.SHIP, "The Eschaton", true).getCaptain().setPersonality("reckless");
		
        api.getContext().fightToTheLast = true;
		
		// Set up the map.
		float width = 20000f;
		float height = 16000f;
		
		float minX = -width/2;
		float minY = -height/2;

		api.initMap(minX, (float)width/2f, minY, (float)height/2f);
		
		
		for (int i = 0; i < 50; i++) {
			float x = (float) Math.random() * width - width/2;
			float y = (float) Math.random() * height - height/2;
			float radius = 100f + (float) Math.random() * 400f; 
			api.addNebula(x, y, radius);
		}
		
		// Add objectives
		api.addObjective(minX + width * 0.25f + 2000, minY + height * 0.25f + 2000, "nav_buoy");
		api.addObjective(minX + width * 0.75f - 2000, minY + height * 0.25f + 2000, "comm_relay");
		api.addObjective(minX + width * 0.75f - 2000, minY + height * 0.75f - 2000, "nav_buoy");
		api.addObjective(minX + width * 0.25f + 2000, minY + height * 0.75f - 2000, "comm_relay");
		api.addObjective(minX + width * 0.5f, minY + height * 0.5f, "sensor_array");
		
		String [] planets = {"barren", "lava", "lava_minor", "frozen1", "cryovolcanic", "frozen", "barren_castiron", "desert", "bombarded"};
		String planet = planets[(int) (Math.random() * (double) planets.length)];
		float radius = 100f + (float) Math.random() * 150f;
		api.addPlanet(0, 0, radius, planet, 200f, true);
	}

}