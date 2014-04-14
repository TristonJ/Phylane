package com.lvadt.phylane.model;

import com.lvadt.phylane.R;

public class Objects {

    private static final int averageObj = 15;

	// This enum contains all of the missions,
	// and their required items. It is possible to add more
	
	// The format for a single is simply NAME(Special.Type, "title",
	// "description")
	// The format for multiple is NAME(new Specials[] {Specials.Type...},
	// "title", "description", reward, difficulty)
	public enum Missions{
		START(Special.NONE,
				"None!",
				"This is your first mission, so it does not have "
				+ "any mission-specific items. But normally, you would have a mission-"
				+ "specific item that you would need, and a description here.",
                10,
                1),
        TEST(Special.NONE,
                "Test",
                "Test Mission",
                10,
                1);
		public Special requirement;
		public Special[] requirements;
		public String description;
		public String title;
        public int reward;
        public int maxObj;
        public int minObj;

		Missions(Special s, String t, String d, int r, int dif) {
			requirement = s;
			description = d;
			title = t;
            reward = r;
            maxObj = (int) (averageObj*(dif/1.5));
            minObj = averageObj-(dif*2);
		}

		Missions(Special[] s, String t, String d, int r, int dif) {
			requirements = s;
			description = d;
			title = t;
            reward = r;
            maxObj = (int) (averageObj*(dif/1.5));
            minObj = averageObj-(dif*2);
		}
	}
	
	// List of all the Engines. Params are ("Name", Weight, Power, Price,
	// Drawable)
	// Power is in N, Weight in kg.
	public enum Engine {
		ONE(new GameObject("Engine One", 20, 650, 1, R.drawable.testplaneimage));

		private GameObject obj;

		Engine(GameObject obj) {
			this.obj = obj;
		}

		// Returns object name
		public String getName() {
			return obj.name;
		}

		// Returns object description
		public String getDesc() {
			return obj.description;
		}

		// Returns object power
		public double getPower() {
			return obj.power;
		}

		// Returns object weight
		public double getWeight() {
			return obj.weight;
		}

		// Returns object price
		public int getPrice() {
			return obj.price;
		}

		// Returns object Id
		public int getId() {
			return obj.id;
		}
	}

	// List of the materials. Params are ("Name", Density, Price, Drawable)
	// Density is NOT the exact density of the material. Rather it is a made up number
	// That should well represent it's real life form. Keeping in mind that
	// the density is only used to calculate the mass of ONLY the outer shell of the plane
	public enum Material {
		ASH(new GameObject("Ash", 3, 1, R.drawable.testplaneimage)),
		ALUMINUM(new GameObject("Aluminum", 1, 1, R.drawable.testplaneimage));

		private GameObject obj;

		Material(GameObject obj) {
			this.obj = obj;
		}

		// Returns object name
		public String getName() {
			return obj.name;
		}

		// Returns object description
		public String getDesc() {
			return obj.description;
		}

		// Returns object density
		public double getDensity() {
			return obj.density;
		}

		// Returns object price
		public int getPrice() {
			return obj.price;
		}

		// Returns object id
		public int getId() {
			return obj.id;
		}

	}

	// The different sizes. Params are ("Name", Volume, Price, Drawable)
	public enum Size {
		SMALL(new GameObject("Small", 3, 1, R.drawable.testplaneimage));

		private GameObject obj;

		Size(GameObject obj) {
			this.obj = obj;
		}

		// Returns object name
		public String getName() {
			return obj.name;
		}

		// Returns object description
		public String getDesc() {
			return obj.description;
		}

		// Returns object weight
		public double getVolume() {
			return obj.volume;
		}

		// Returns object price
		public int getPrice() {
			return obj.price;
		}

		// Returns object id
		public int getId() {
			return obj.id;
		}

	}
	
	// The mission specific items. Params are ("Name", Weight, Price, id)
	public enum Special {
		NONE(new GameObject("None", 0, 0, R.drawable.testplaneimage)),
		SMALLCARGOBAY(new GameObject("Small Cargo Bay", 3000, 1, R.drawable.testplaneimage ));
		
		private GameObject obj;

		Special(GameObject obj) {
			this.obj = obj;
		}

		// Returns object name
		public String getName() {
			return obj.name;
		}

		// Returns object description
		public String getDesc() {
			return obj.description;
		}

		// Returns object weight
		public double getWeight() {
			return obj.weight;
		}

		// Returns object price
		public int getPrice() {
			return obj.price;
		}

		// Returns object id
		public int getId() {
			return obj.id;
		}
	}
}
