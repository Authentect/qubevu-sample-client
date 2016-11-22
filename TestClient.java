
// Jersey client API
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.WebResource.Builder;

// import classes generated by wsimport
import com.postea.webservices.qubevu.*;


public class TestClient {

    private Client client;
    private static final String BASE_URI = "http://192.168.2.200/WebServices/QubeVuService/";

    public TestClient() {
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
    }

    public <T> T status(Class<T> responseType) throws UniformInterfaceException {
        WebResource resource = client.resource(BASE_URI).path("Status");
	Builder builder = resource.accept(javax.ws.rs.core.MediaType.TEXT_XML);
	
	//String s = builder.post(String.class);

	//ClientResponse response = builder.post(ClientResponse.class);
	//QVStatus status = (QVStatus)response.getEntity(QVStatus.class);

        return builder.post(responseType);
    }

    public <T> T capture(Class<T> responseType) throws UniformInterfaceException {
        WebResource resource = client.resource(BASE_URI).path("Capture");
        return resource.accept(javax.ws.rs.core.MediaType.TEXT_XML).get(responseType);
    }

    public void close() {
        client.destroy();
    }

    public static void main(String[] args) {
        TestClient n = new TestClient();

        while (true) {
            QVStatus s = n.status(QVStatus.class);

            System.out.println(s.getCaptureId());
            System.out.println(s.getStatus());
            System.out.println(s.getExtendedStatus());
	

            if (s.getStatus().equals("READY")) {
            } else if (s.getStatus().equals("IMAGING")) {
                CapturedData cd = s.getCapturedData();
		Dimensions d = cd.getDimensions();

                System.out.println("getHeight: " + d.getHeight() + " getLength: " + d.getLength() + " getWidth: " + d.getWidth());
            } else if (s.getStatus().equals("REMOVE")) {
                CapturedData cd = s.getCapturedData();
		TrackerImage ti = cd.getTrackerImage();
		Dimensions d = cd.getDimensions();

                System.out.println("getHeight: " + d.getHeight() + " getLength: " + d.getLength() + " getWidth: " + d.getWidth());

                String u = ti.getUrl();
                String na = ti.getName();

                System.out.println("Iamge name: " + na + " URL: " + u);
            }
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }
        }
    }
}
