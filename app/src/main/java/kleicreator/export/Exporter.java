package kleicreator.export;

import kleicreator.data.Constants;
import kleicreator.export.interfaces.Job;
import kleicreator.export.interfaces.RequireJob;
import kleicreator.export.jobs.ItemJob;
import kleicreator.export.jobs.ModinfoJob;
import kleicreator.export.jobs.ModmainJob;
import kleicreator.export.jobs.SetupJob;
import kleicreator.frames.LoadingStartup;
import kleicreator.items.Item;
import kleicreator.util.Logger;
import kleicreator.data.Mod;
import kleicreator.sdk.internal.PluginBlob;
import kleicreator.sdk.internal.PluginHandler;
import kleicreator.sdk.EventTrigger;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exporter {
    List<Job> jobs;
    List<Class<? extends Job>> runJobs;

    Map<String, String> shared;

    String output;

    LoadingStartup loadingStartup;
    int maxJobs;
    int completedJobs;

    public Map<String, String> getShared() {
        return shared;
    }

    public String getOutput() {
        return output;
    }

    public Exporter(){
        /* SETUP */
        loadingStartup = new LoadingStartup();
        output = Constants.constants.FetchExportLocation(Mod.escapedModName());

        /* JOB INIT */
        jobs = QueueJobs();
        maxJobs = jobs.size();
        runJobs = new ArrayList<>();
        shared = new HashMap<>();

        for(Job job : jobs){
            ExecuteJob(job);
            completedJobs++;
        }

        loadingStartup.Destroy();
        try {
            Desktop.getDesktop().open(new File(output));
        } catch (IOException e) {
            Logger.Error(e);
        }
    }

    public void Write(String path, String content){
        try {
            File file = new File(Path.of(output, path).toUri());
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter f = new FileWriter(file, false);
            f.write(content);
            f.close();
        } catch (IOException e) {
            Logger.Error(e);
        }
    }

    public void ExecuteJob(Job job){
        loadingStartup.SetProgress((completedJobs*100)/maxJobs, job.prettyName());
        if(job.getClass().isAnnotationPresent(RequireJob.class)){
            RequireJob require = job.getClass().getAnnotation(RequireJob.class);
            for(Class<? extends Job> dependency : require.job()){
                if(runJobs.contains(dependency)){
                    continue;
                }
                try {
                    ExecuteJob(dependency.getConstructor().newInstance());
                } catch (Exception e) {
                    Logger.Error(e);
                    return;
                }
            }
        }

        Map<String, String> export = null;
        try {
            export = job.run(this);
        } catch (Exception e) {
            Logger.Error(e);
            return;
        }
        if(export != null){
            shared.putAll(export);
        }

        runJobs.add(job.getClass());
    }

    private List<Job> QueueJobs() {
        List<Job> jobs = new ArrayList<>();
        jobs.add(new SetupJob());
        jobs.add(new ModmainJob());
        jobs.add(new ModinfoJob());
        for(Item item : Mod.items){
            jobs.add(new ItemJob(item));
        }
        for(PluginBlob blob : PluginHandler.blobs){
            for(EventTrigger eventTrigger : blob.triggers){
                jobs.addAll(eventTrigger.ExportJobs());
            }
        }
        return jobs;
    }



}
