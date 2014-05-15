package com.lvadt.phylane.model;

import com.lvadt.phylane.R;

public class Objects {

    public enum Types{
        ENGINE,
        MATERIAL,
        SIZE,
        SPECIAL;
    }

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
		CHEAPPARTS(new GameObject("Cheap Parts", 20.0, 650.0, 0, R.drawable.engines)),
        NLHP2(new GameObject("NL HP 2", 30.0, 700.0, 20, R.drawable.engines)),
        MODI3(new GameObject("MoD I III", 40.0, 800.0, 50, R.drawable.engines)),
        YTE(new GameObject("YT E", 55.0, 925.0, 100, R.drawable.engines)),
        R8000(new GameObject("R 8000", 70.0, 1050.0, 200, R.drawable.engines));


		private GameObject obj;

		Engine(GameObject obj) {
			this.obj = obj;
            this.obj.rName = this.name();
		}

        public GameObject getObj(){

            return obj;
        }
	}

	// List of the materials. Params are ("Name", Density, Price, Drawable, Strength)
	// Density is NOT the exact density of the material. Rather it is a made up number
	// That should well represent it's real life form. Keeping in mind that
	// the density is only used to calculate the mass of ONLY the outer shell of the plane
	public enum Material {
		WOOD(new GameObject("Wood", 3.3, 0, R.drawable.wood, 1.0)),
        TITANIUM(new GameObject("Titanium", 3, 10, R.drawable.titanium, 3.0)),
		ALUMINUM(new GameObject("Aluminum", 2, 30, R.drawable.aluminum, 2.0)),
        STEEL(new GameObject("Steel", 5, 20, R.drawable.steel, 4.0)),
        ALIEN(new GameObject("Alien Metal", 0.0001, 1000, R.drawable.alien, 20.0));

		private GameObject obj;

		Material(GameObject obj) {
			this.obj = obj;
            this.obj.rName = this.name();
		}

        public GameObject getObj(){
            return obj;
        }

	}

	// The different sizes. Params are ("Name", Volume, Price, Drawable)
	public enum Size {
		SMALL(new GameObject("Small", 3, 1, R.drawable.small)),
        MEDIUM(new GameObject("Medium", 10, 10, R.drawable.medium)),
        LARGE(new GameObject("Large", 20, 30, R.drawable.large));

		private GameObject obj;

		Size(GameObject obj) {
			this.obj = obj;
            this.obj.rName = this.name();
		}

        public GameObject getObj(){
            return obj;
        }

	}
	
	// The mission specific items. Params are ("Name", Weight, Price, id)
	public enum Special {
		NONE(new GameObject("None", 0, 0, R.drawable.box)),
		SMALLCARGOBAY(new GameObject("Small Cargo Bay", 3000, 1, R.drawable.box));
		
		private GameObject obj;

		Special(GameObject obj) {
			this.obj = obj;
            this.obj.rName = this.name();
		}

        public GameObject getObj(){
            return obj;
        }
	}
}
