package updater;

import logging.Logger;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Updater {
    public static boolean CheckForUpdate(String version){

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

            if(recentTag == version){
                return false;
            }
            String[] recentSplit = recentTag.split("\\.");
            String[] currentSplit = version.split("\\.");
            if(Integer.parseInt(recentSplit[0]) > Integer.parseInt(currentSplit[0])){
                return true;
            }
            if(Integer.parseInt(recentSplit[1]) > Integer.parseInt(currentSplit[1])){
                return true;
            }
            if(Integer.parseInt(recentSplit[2]) > Integer.parseInt(currentSplit[2])){
                return true;
            }

        } catch (IOException | ParseException ex) {
        }
        return false;
    }

    public static void GetLastestRelease(JFrame frame){
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

            //CREATE POPUP
            JLabel hyperlink = new JLabel(downloadText);
            hyperlink.setForeground(Color.BLUE.darker());
            hyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            hyperlink.setFont(new Font("Serif", Font.PLAIN, 20));
            hyperlink.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)){
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            desktop.browse(new URI(downloadURL));
                        } catch (IOException | URISyntaxException m) {
                            Logger.Error(m.getLocalizedMessage());
                        }
                    }else{
                        Runtime runtime = Runtime.getRuntime();
                        try {
                            runtime.exec("xdg-open " + downloadURL);
                        } catch (IOException m) {
                            Logger.Error(m.getLocalizedMessage());
                        }
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // the mouse has entered the label
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // the mouse has exited the label
                }
            });

            JOptionPane.showMessageDialog(frame, hyperlink, "New version: " + tag, JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
    }
}
