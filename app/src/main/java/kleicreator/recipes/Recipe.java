package kleicreator.recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Recipe {

    public String id;
    public String result;
    public List<String> ingredients;
    public TECH tech;
    public RECIPETAB tab;

    public Recipe() {
        this.id = UUID.randomUUID().toString().substring(0, 5);
    }

    public Recipe(String result, List<String> ingredients, RECIPETAB tab, TECH tech) {
        this();
        this.result = result;
        this.ingredients = ingredients;
        this.tab = tab;
        this.tech = tech;
    }

    public enum TECH {
        NONE,
        SCIENCE_ONE,
        SCIENCE_TWO,
        SCIENCE_THREE,
        MAGIC_TWO,
        MAGIC_THREE,
        ANCIENT_TWO,
        ANCIENT_THREE,
        ANCIENT_FOUR,
        CELESTIAL_ONE,
        CELESTIAL_THREE,
        MOON_ALTAR_TWO,
        SHADOW_TWO,
        CARTOGRAPHY_TWO,
        SEAFARING_ONE,
        SEAFARING_TWO,
        SCULPTING_ONE,
        SCULPTING_TWO,
        ORPHANAGE_ONE,
        PERDOFFERING_ONE,
        PERDOFFERING_THREE,
        WARGOFFERING_THREE,
        PIGOFFERING_THREE,
        CARRATOFFERING_THREE,
        BEEFOFFERING_THREE,
        CATCOONOFFERING_THREE,
        MADSCIENCE_ONE,
        CARNIVAL_PRIZESHOP_ONE,
        CARNIVAL_HOSTSHOP_ONE,
        CARNIVAL_HOSTSHOP_THREE,
        FOODPROCESSING_ONE,
        FISHING_ONE,
        FISHING_TWO,
        HERMITCRABSHOP_ONE,
        HERMITCRABSHOP_THREE,
        HERMITCRABSHOP_FIVE,
        HERMITCRABSHOP_SEVEN,
        TURFCRAFTING_ONE,
        TURFCRAFTING_TWO,
        MASHTURFCRAFTING_TWO,
        WINTERSFEASTCOOKING_ONE,
        HALLOWED_NIGHTS,
        WINTERS_FEAST,
        SPIDERCRAFT_ONE
    }

    public enum RECIPETAB {
        TOOLS,
        LIGHT,
        SURVIVAL,
        FARM,
        SCIENCE,
        WAR,
        TOWN,
        SEAFARING,
        REFINE,
        MAGIC,
        DRESS
    }
}
