package org.dmcs.transaction.analytics.mesosphere;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dmcs.transaction.analytics.mesosphere.chronos.ChronosJob;
import org.dmcs.transaction.analytics.mesosphere.marathon.MarathonApplication;

import java.io.IOException;

public class MesosphereDeployment {

    // TODO: Username / password!
    public static void deployToChronos(String dcosUrl, ChronosJob job) throws IOException {
        deployViaRest(dcosUrl + "/chronos/v1/scheduler/iso8601", job);
    }

    // TODO: Username / password!
    public static void deployToMarathon(String dcosUrl, MarathonApplication application) throws IOException {
        deployViaRest(dcosUrl + "/service/marathon-user/v2/apps", application);
    }

    // TODO: Username / password!
    private static void deployViaRest(String url, Object body) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        HttpClient client = new DefaultHttpClient();

        HttpPost request = new HttpPost(url);
        request.setEntity(new StringEntity(mapper.writeValueAsString(body), ContentType.APPLICATION_JSON));

        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode >= 200 && statusCode < 300) {
            System.out.println("REST deployment to: POST " + url + "successful.");
        } else {
            throw new IllegalStateException("REST deployment to: POST " + url + "failed. Status code: " + statusCode +
            ". Reason: " + response.getStatusLine().getReasonPhrase());
        }
    }
}
