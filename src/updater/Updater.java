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
    public static boolean CheckForUpdate(float version){

        String url = "https://api.github.com/repos/decduck3/dstguimodcreator/releases";

        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            request.addHeader("content-type", "application/json");
            request.addHeader("accept", "application/vnd.github.v3+json");
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(((CloseableHttpResponse) result).getEntity(), "UTF-8");

            json = "{ \"releases\": " + json + "}";

            JSONObject obj = new JSONObject(json);
            JSONArray releases = obj.getJSONArray("releases");
            if(Float.parseFloat(releases.getJSONObject(0).getString("tag_name")) > version){
                return true;
            }

        } catch (IOException | ParseException ex) {
        }
        return false;
    }

    public static void GetLastestRelease(JFrame frame){
        String url = "https://api.github.com/repos/decduck3/dstguimodcreator/releases";

        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            request.addHeader("content-type", "application/json");
            request.addHeader("accept", "application/vnd.github.v3+json");
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(((CloseableHttpResponse) result).getEntity(), "UTF-8");

            json = "{ \"releases\": " + json + "}";

            JSONObject obj = new JSONObject(json);
            JSONArray releases = obj.getJSONArray("releases");
            String newURL = releases.getJSONObject(0).getString("assets_url");
            String newVersion = releases.getJSONObject(0).getString("name");

            HttpGet newRequest = new HttpGet(newURL);
            newRequest.addHeader("content-type", "application/json");
            newRequest.addHeader("accept", "application/vnd.github.v3+json");
            result = httpClient.execute(newRequest);

            String newJson = EntityUtils.toString(((CloseableHttpResponse) result).getEntity(), "UTF-8");

            newJson = "{ \"releases\": " + newJson + "}";

            obj = new JSONObject(newJson);

            JSONArray assets = obj.getJSONArray("releases");

            String downloadURL = assets.getJSONObject(0).getString("browser_download_url");

            //CREATE POPUP
            JLabel hyperlink = new JLabel("Download new update");
            hyperlink.setForeground(Color.BLUE.darker());
            hyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            hyperlink.setFont(new Font("Serif", Font.PLAIN, 20));
            hyperlink.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI(downloadURL));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (URISyntaxException uriSyntaxException) {
                        uriSyntaxException.printStackTrace();
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

            JOptionPane.showMessageDialog(frame, hyperlink, "New update: " + newVersion, JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
    }
}
