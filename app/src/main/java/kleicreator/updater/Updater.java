package kleicreator.updater;

import kleicreator.sdk.logging.Logger;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class Updater {
    public static boolean CheckForUpdate(String version) {

        String url = "https://lab.deepcore.dev/api/v4/projects/6/releases";

        version = version.substring(1); // Remove v

        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            request.addHeader("content-type", "application/json");
            request.addHeader("accept", "application/vnd.github.v3+json");
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(((CloseableHttpResponse) result).getEntity(), "UTF-8");

            JSONArray obj = new JSONArray(json);
            JSONObject recent = obj.getJSONObject(0);
            String recentTag = recent.getString("tag_name").substring(1);

            if (recentTag == version) {
                return false;
            }
            String[] recentSplit = recentTag.split("\\.");
            String[] currentSplit = version.split("\\.");
            if (Integer.parseInt(recentSplit[0]) < Integer.parseInt(currentSplit[0])) {
                return false;
            } else if (Integer.parseInt(recentSplit[1]) < Integer.parseInt(currentSplit[1])) {
                return false;
            } else return Integer.parseInt(recentSplit[2]) > Integer.parseInt(currentSplit[2]);

        } catch (IOException | ParseException ex) {
        }
        return false;
    }

    public static void GetLastestRelease(JFrame frame) {
        String url = "https://lab.deepcore.dev/api/v4/projects/6/releases";

        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            request.addHeader("content-type", "application/json");
            request.addHeader("accept", "application/vnd.github.v3+json");
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(((CloseableHttpResponse) result).getEntity(), "UTF-8");

            JSONArray obj = new JSONArray(json);
            JSONObject recent = obj.getJSONObject(0);

            String downloadURL = recent.getJSONObject("_links").getString("self");
            String downloadText = recent.getString("name");
            String tag = recent.getString("tag_name");

            //Create popup
            JLabel hyperlink = new JLabel("Download new version? \"" + downloadText + "\"");
            hyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
                        runtime.exec("xdg-open " + downloadURL);
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