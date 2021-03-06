/**
 * The QubeVuSampleClient class implements an application that
 * invokes each method of the QubeVuService web service exposed by the QubeVu device.
 */

import java.io.*;
import java.net.*;

// Jersey client API
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.WebResource.Builder;

// import classes generated by wsimport
import com.postea.webservices.qubevu.*;

import javax.ws.rs.core.MultivaluedMap;
import com.sun.jersey.core.util.*;

class QubeVuSampleClient 
{
    
    static String captureDefName = "QubeVuSampleClient";
    static String captureDef = "<CaptureDefinitionDetail Name=\"QubeVuSampleClient\">" +
                      	"<LowResImages>" +
                      	"    <LowResCamCapture ImageName=\"ChainOfCustodyImage\">" +
                      	"        <ResX>640</ResX>" +
                      	"        <ResY>480</ResY>" +
                      	"    </LowResCamCapture>" +
                      	"</LowResImages>" +
                      	"<HighResImages />" +
                      	"</CaptureDefinitionDetail>";


    public static void main(String[] args) 
    {
	String url = "http://192.168.2.205/WebServices/QubeVuService/";

        System.out.println("QubeVuSampleClient application");
        System.out.println("QubeVu url: " + url);
	
	QVClient qvClient = new QVClient(url);

	if (args.length > 0 && args[0].equals("2"))
	{
            System.out.println("Using URLConnection class");

	    qvClient.Status();
	    qvClient.CreateCaptureDefinition(captureDefName, captureDef);
	    qvClient.GetCaptureDefinition(captureDefName);
	    qvClient.CaptureDefinitionList("");
	    qvClient.Capture(captureDefName);
	    qvClient.DeleteCaptureDefinition(captureDefName);
	    qvClient.Restart("");
	} else if (args.length > 0 && args[0].equals("1")) {


            System.out.println("Using JAXWS (Typed)");

	    QVServiceResponse qvServiceResponse;

	    QVClient_JAXWS qvClient_JAXWS = new QVClient_JAXWS(url);
	    QVStatus qvStatus = qvClient_JAXWS.Status(QVStatus.class);
	    qvServiceResponse = qvClient_JAXWS.CreateCaptureDefinition(QVServiceResponse.class, captureDefName, captureDef);
	    CaptureDefinitionDetail captureDefDetail = qvClient_JAXWS.GetCaptureDefinition(CaptureDefinitionDetail.class, captureDefName);
	    QVCaptureDefinitionList qvCaptureDefList = qvClient_JAXWS.CaptureDefinitionList(QVCaptureDefinitionList.class, "");
	    qvServiceResponse = qvClient_JAXWS.Capture(QVServiceResponse.class, captureDefName);
	    qvServiceResponse = qvClient_JAXWS.DeleteCaptureDefinition(QVServiceResponse.class, captureDefName);
	    qvServiceResponse = qvClient_JAXWS.Restart(QVServiceResponse.class, "");
	} else {

            System.out.println("Using JAXWS (String)");

	    QVClient_JAXWS qvClient_JAXWS = new QVClient_JAXWS(url);
	    System.out.println(qvClient_JAXWS.Status(String.class));
	    System.out.println(qvClient_JAXWS.CreateCaptureDefinition(String.class, captureDefName, captureDef));
	    System.out.println(qvClient_JAXWS.GetCaptureDefinition(String.class, captureDefName));
	    System.out.println(qvClient_JAXWS.CaptureDefinitionList(String.class, ""));
	    System.out.println(qvClient_JAXWS.Capture(String.class, captureDefName));
	    System.out.println(qvClient_JAXWS.DeleteCaptureDefinition(String.class, captureDefName));
	    System.out.println(qvClient_JAXWS.Restart(String.class, ""));
	}
    }
}

class QVClient_JAXWS
{
    private Client client;
    String baseUrl;

    public QVClient_JAXWS() 
    {
	this("http://192.168.2.205/WebServices/QubeVuService/");
    }

    public QVClient_JAXWS(String baseUrl)
    {
	this.baseUrl = baseUrl;
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
    }    

    public <T> T Status(Class<T> responseType) throws UniformInterfaceException {
	String wsMethod = "Status";

	System.out.println("Calling " + wsMethod);
	System.out.println("=====================");

        WebResource resource = client.resource(baseUrl).path(wsMethod);
        return resource.accept(javax.ws.rs.core.MediaType.TEXT_XML).post(responseType);
    }

    public <T> T Capture(Class<T> responseType, String captureDefinitionName) throws UniformInterfaceException {
	String wsMethod = "Capture";

	System.out.println("Calling " + wsMethod);
	System.out.println("=====================");

        WebResource resource = client.resource(baseUrl).path("Capture");
	//resource = resource.queryParam("captureDefinitionName", captureDefinitionName);

	MultivaluedMap formData = new MultivaluedMapImpl();
	formData.add("captureDefinitionName", captureDefinitionName);

        return resource.accept(javax.ws.rs.core.MediaType.TEXT_XML).type(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(responseType, formData);
    }

    public <T> T Restart(Class<T> responseType, String restartMode) throws UniformInterfaceException {
	String wsMethod = "Restart";

	System.out.println("Calling " + wsMethod);
	System.out.println("=====================");

        WebResource resource = client.resource(baseUrl).path(wsMethod);
	//resource = resource.queryParam("restartMode", restartMode);

	MultivaluedMap formData = new MultivaluedMapImpl();
	formData.add("restartMode", restartMode);

        return resource.accept(javax.ws.rs.core.MediaType.TEXT_XML).type(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(responseType, formData);
    }

    public <T> T CreateCaptureDefinition(Class<T> responseType, String name, String definitionString) throws UniformInterfaceException {
	String wsMethod = "CreateCaptureDefinition";

	System.out.println("Calling " + wsMethod);
	System.out.println("=====================");

        WebResource resource = client.resource(baseUrl).path(wsMethod);

	MultivaluedMap formData = new MultivaluedMapImpl();
	formData.add("name", name);
	formData.add("definitionString", definitionString);

        return resource.accept(javax.ws.rs.core.MediaType.TEXT_XML).type(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(responseType, formData);
    }

    public <T> T GetCaptureDefinition(Class<T> responseType, String name) throws UniformInterfaceException {
	String wsMethod = "GetCaptureDefinition";

	System.out.println("Calling " + wsMethod);
	System.out.println("=====================");

        WebResource resource = client.resource(baseUrl).path(wsMethod);
	//resource = resource.queryParam("name", name);

	MultivaluedMap formData = new MultivaluedMapImpl();
	formData.add("name", name);

        return resource.accept(javax.ws.rs.core.MediaType.TEXT_XML).type(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(responseType, formData);
    }

    public <T> T CaptureDefinitionList(Class<T> responseType, String filter) throws UniformInterfaceException {
	String wsMethod = "CaptureDefinitionList";

	System.out.println("Calling " + wsMethod);
	System.out.println("=====================");

        WebResource resource = client.resource(baseUrl).path(wsMethod);
	//resource = resource.queryParam("filter", filter);

	MultivaluedMap formData = new MultivaluedMapImpl();
	formData.add("filter", filter);

        return resource.accept(javax.ws.rs.core.MediaType.TEXT_XML).type(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(responseType, formData);
    }

    public <T> T DeleteCaptureDefinition(Class<T> responseType, String name) throws UniformInterfaceException {
	String wsMethod = "DeleteCaptureDefinition";

	System.out.println("Calling " + wsMethod);
	System.out.println("=====================");

        WebResource resource = client.resource(baseUrl).path(wsMethod);
	//resource = resource.queryParam("name", name);

	MultivaluedMap formData = new MultivaluedMapImpl();
	formData.add("name", name);

        return resource.accept(javax.ws.rs.core.MediaType.TEXT_XML).type(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(responseType, formData);
    }

    public void close() {
        client.destroy();
    }
}

class QVClient
{

    String baseUrl;

    public QVClient() 
    {
	this("http://192.168.2.205/WebServices/QubeVuService/");
    }

    public QVClient(String baseUrl)
    {
	this.baseUrl = baseUrl;
    }    

    public void Status()
    {
	String wsMethod = "Status";

	System.out.println("Calling " + wsMethod);
	System.out.println("=====================");

	try
	{
		URL url = new URL(baseUrl + wsMethod);
        	URLConnection connection = url.openConnection();
       		connection.setDoOutput(true);
		connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");

       		OutputStreamWriter out = new OutputStreamWriter(
                                         connection.getOutputStream());
       		out.close();

       		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
       		String decodedString;
       		while ((decodedString = in.readLine()) != null) {
           			System.out.println(decodedString);
       		}
       		in.close();
	}
	catch(Exception ex)
	{
		System.out.println("Exception in " + wsMethod);
		System.out.println(ex.toString());
	}
    }

    public void Capture(String captureDefinitionName)
    {
	String wsMethod = "Capture";

	System.out.println("Calling " + wsMethod);
	System.out.println("=====================");

	try
	{
		URL url = new URL(baseUrl + wsMethod);
        	URLConnection connection = url.openConnection();
       		connection.setDoOutput(true);
		connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");

		captureDefinitionName = URLEncoder.encode(captureDefinitionName, "UTF-8");

       		OutputStreamWriter out = new OutputStreamWriter(
                                         connection.getOutputStream());
       		out.write("captureDefinitionName=" + captureDefinitionName);
       		out.close();

       		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
       		String decodedString;
       		while ((decodedString = in.readLine()) != null) {
           			System.out.println(decodedString);
       		}
       		in.close();
	}
	catch(Exception ex)
	{
		System.out.println("Exception in " + wsMethod);
		System.out.println(ex.toString());
	}
    }

    public void Restart(String restartMode)
    {
	String wsMethod = "Restart";

	System.out.println("Calling " + wsMethod);
	System.out.println("=====================");

	try
	{
		URL url = new URL(baseUrl + wsMethod);
        	URLConnection connection = url.openConnection();
       		connection.setDoOutput(true);
		connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");

		restartMode = URLEncoder.encode(restartMode, "UTF-8");

       		OutputStreamWriter out = new OutputStreamWriter(
                                         connection.getOutputStream());
       		out.write("restartMode=" + restartMode);
       		out.close();

       		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
       		String decodedString;
       		while ((decodedString = in.readLine()) != null) {
           			System.out.println(decodedString);
       		}
       		in.close();
	}
	catch(Exception ex)
	{
		System.out.println("Exception in " + wsMethod);
		System.out.println(ex.toString());
	}
    }

    public void CreateCaptureDefinition(String name, String definitionString)
    {
	String wsMethod = "CreateCaptureDefinition";

	System.out.println("Calling " + wsMethod);
	System.out.println("=====================");

	try
	{
		URL url = new URL(baseUrl + wsMethod);
        	URLConnection connection = url.openConnection();
       		connection.setDoOutput(true);
		connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");

		name = URLEncoder.encode(name, "UTF-8");
		definitionString = URLEncoder.encode(definitionString, "UTF-8");

       		OutputStreamWriter out = new OutputStreamWriter(
                                         connection.getOutputStream());
       		out.write("name=" + name);
       		out.write("&definitionString=" + definitionString);
       		out.close();

       		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
       		String decodedString;
       		while ((decodedString = in.readLine()) != null) {
           			System.out.println(decodedString);
       		}
       		in.close();
	}
	catch(Exception ex)
	{
		System.out.println("Exception in " + wsMethod);
		System.out.println(ex.toString());
	}
    }

    public void GetCaptureDefinition(String name)
    {
	String wsMethod = "GetCaptureDefinition";

	System.out.println("Calling " + wsMethod);
	System.out.println("=====================");

	try
	{
		URL url = new URL(baseUrl + wsMethod);
        	URLConnection connection = url.openConnection();
       		connection.setDoOutput(true);
		connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");

		name = URLEncoder.encode(name, "UTF-8");

       		OutputStreamWriter out = new OutputStreamWriter(
                                         connection.getOutputStream());
       		out.write("name=" + name);
       		out.close();

       		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
       		String decodedString;
       		while ((decodedString = in.readLine()) != null) {
           			System.out.println(decodedString);
       		}
       		in.close();
	}
	catch(Exception ex)
	{
		System.out.println("Exception in " + wsMethod);
		System.out.println(ex.toString());
	}
    }

    public void CaptureDefinitionList(String filter)
    {
	String wsMethod = "CaptureDefinitionList";

	System.out.println("Calling " + wsMethod);
	System.out.println("=====================");

	try
	{
		URL url = new URL(baseUrl + wsMethod);
        	URLConnection connection = url.openConnection();
       		connection.setDoOutput(true);
		connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");

		filter = URLEncoder.encode(filter, "UTF-8");

       		OutputStreamWriter out = new OutputStreamWriter(
                                         connection.getOutputStream());
       		out.write("filter=" + filter);
       		out.close();

       		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
       		String decodedString;
       		while ((decodedString = in.readLine()) != null) {
           			System.out.println(decodedString);
       		}
       		in.close();
	}
	catch(Exception ex)
	{
		System.out.println("Exception in " + wsMethod);
		System.out.println(ex.toString());
	}
    }

    public void DeleteCaptureDefinition(String name)
    {
	String wsMethod = "DeleteCaptureDefinition";

	System.out.println("Calling " + wsMethod);
	System.out.println("=====================");

	try
	{
		URL url = new URL(baseUrl + wsMethod);
        	URLConnection connection = url.openConnection();
       		connection.setDoOutput(true);
		connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");

		name = URLEncoder.encode(name, "UTF-8");

       		OutputStreamWriter out = new OutputStreamWriter(
                                         connection.getOutputStream());
       		out.write("name=" + name);
       		out.close();

       		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
       		String decodedString;
       		while ((decodedString = in.readLine()) != null) {
           			System.out.println(decodedString);
       		}
       		in.close();
	}
	catch(Exception ex)
	{
		System.out.println("Exception in " + wsMethod);
		System.out.println(ex.toString());
	}
    }

}