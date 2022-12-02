package kleicreator.export.jobs;

import kleicreator.export.interfaces.RequireJob;
import kleicreator.export.interfaces.Job;
import kleicreator.export.Exporter;

import java.util.Map;

@RequireJob(job={SpeechJob.class, PrefabJob.class, RecipeJob.class})
public class ModmainJob implements Job {
    @Override
    public Map<String, String> run(Exporter exporter) {
        Map<String, String> shared = exporter.getShared();
        String template = this.loadTemplate("modmain.template");

        // Speech
        String speechBlock = shared.get("speech");
        template = sub(template, "$SPEECH$", speechBlock);

        // Prefabs
        String prefabBlock = shared.get("prefab");
        template = sub(template,"$PREFABS$", prefabBlock);

        // Recipes
        String recipeBlock = shared.get("recipe");
        template = sub(template,"$RECIPES$", recipeBlock);

        // Write
        exporter.Write("modmain.lua", template);
        return null;
    }

    @Override
    public String prettyName() {
        return "Modmain Job";
    }
}
