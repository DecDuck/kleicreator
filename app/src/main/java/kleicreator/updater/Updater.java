package kleicreator.updater;

import kleicreator.sdk.logging.Logger;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class Updater {
    public static boolean CheckForUpdate(String version) {

        String url = "https://api.github.com/repos/DecDuck/kleicreator/releases";

        version = version.substring(1); // Remove v

        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            request.addHeader("content-type", "application/json");
            request.addHeader("accept", "application/vnd.github.v3+json");
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");

            JSONArray obj = new JSONArray(json);
            JSONObject recent = obj.getJSONObject(0);
            String recentTag = recent.getString("tag_name").substring(1);

            Logger.Log(version + " vs " + recentTag);

            if (recentTag.equals(version)) {
                return false;
            }
            String[] recentSplit = recentTag.split("\\.");
            String[] currentSplit = version.split("\\.");
            if (Integer.parseInt(recentSplit[0]) > Integer.parseInt(currentSplit[0])) {
                Logger.Log("Major is bigger");
                return true;
            } else if (Integer.parseInt(recentSplit[1]) > Integer.parseInt(currentSplit[1])) {
                Logger.Log("Minor is bigger");
                return true;
            } else if(Integer.parseInt(recentSplit[2]) > Integer.parseInt(currentSplit[2])){
                Logger.Log("Patch is bigger");
                return true;
            }
            return false;

        } catch (IOException | ParseException | JSONException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void GetLatestRelease(JFrame frame) {
        String url = "https://api.github.com/repos/DecDuck/kleicreator/releases";

        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            request.addHeader("content-type", "application/json");
            request.addHeader("accept", "application/vnd.github.v3+json");
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");

            JSONArray obj = new JSONArray(json);
            JSONObject recent = obj.getJSONObject(0);

            String downloadURL = recent.getString("html_url");
            String downloadText = recent.getString("name");
            String tag = recent.getString("tag_name");

            //Create popup
            JLabel hyperlink = new JLabel("Download new version? \"" + downloadText + "\"");
            hyperlink.setFont(new Font("Serif", Font.PLAIN, 20));

            if (JOptionPane.showConfirmDialog(frame, hyperlink, "New version: " + tag, JOptionPane.YES_NO_OPTION) == 0) {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.browse(new URI(downloadURL));
                    } catch (IOException | URISyntaxException m) {
                        Logger.Error(ExceptionUtils.getStackTrace(m));
                    }
                } else {
                    Runtime runtime = Runtime.getRuntime();
                    try {
                        runtime.exec(new String[]{"xdg-open", downloadURL});
                    } catch (IOException m) {
                        Logger.Error(ExceptionUtils.getStackTrace(m));
                    }
                }
            }

        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
    }
}
