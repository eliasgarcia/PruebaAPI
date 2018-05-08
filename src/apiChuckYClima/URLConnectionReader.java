package apiChuckYClima;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

public class URLConnectionReader {
	
	private static final String URL_YAHOO = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20"
			+ "(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"+ "ciudadABuscar" +"%22)&format=json&env=store%3A%2F%2Fdatatables.org%2"
			+ "Falltableswithkeys"; 
   
	private static final String URL_CHUCK_NORRIS= "https://api.chucknorris.io/jokes/random";
	
	public static void main(String[] args) throws Exception {
		obtenerClimaPorIp();
		System.out.println(ObtenerClimaPorCiudad("San Justo"));
		System.out.println(ObtenerClimaPorCiudad("SAN JUSTO"));
		System.out.println(ObtenerClimaPorCiudad("san justo"));
		System.out.println(obtenerChuckNorrisFact());
		System.out.println(obtenerChuckNorrisFact());
		System.out.println(obtenerChuckNorrisFact());
		System.out.println(obtenerChuckNorrisFact());
    }
    
    
    private static String  ciudadPorIp() throws JsonParseException, JsonProcessingException, IOException {
    	URL ipapi = new URL("https://ipapi.co/json");
        URLConnection ic = ipapi.openConnection();
        JsonNode rootNode = new ObjectMapper().readTree(new JsonFactory().createJsonParser(new InputStreamReader(
                ic.getInputStream())));
        System.out.println(rootNode.path("city").getTextValue());
        return rootNode.path("city").getTextValue(); 
    }
    
    private static String obtenerClimaPorIp() throws JsonParseException, JsonProcessingException, IOException {
    	String ciudad = ciudadPorIp();
    	URL yahoo = new URL(URL_YAHOO.replace("ciudadABuscar", ciudad.replaceAll(" ", "%20")));
        URLConnection yc = yahoo.openConnection();
        JsonNode rootNode = new ObjectMapper().readTree(new JsonFactory().createJsonParser(new InputStreamReader(
                yc.getInputStream())));
        JsonNode rootCondition = rootNode.path("query").path("results").path("channel").path("item").path("condition");
        Clima clima =  new ObjectMapper().readValue(rootCondition, Clima.class);
        return clima.toString();
    }
    
    private static String ObtenerClimaPorCiudad(String ciudad) throws JsonParseException, JsonProcessingException, IOException {
    	URL yahoo = new URL(URL_YAHOO.replace("ciudadABuscar", ciudad.replaceAll(" ", "%20")));
        URLConnection yc = yahoo.openConnection();
        JsonNode rootNode = new ObjectMapper().readTree(new JsonFactory().createJsonParser(new InputStreamReader(
                yc.getInputStream())));
        JsonNode rootCondition = rootNode.path("query").path("results").path("channel").path("item").path("condition");
        Clima clima =  new ObjectMapper().readValue(rootCondition, Clima.class);
        return clima.toString();
    }
    
    private static String obtenerChuckNorrisFact() throws JsonParseException, JsonProcessingException, IOException {
    	URL chuckUrl = new URL(URL_CHUCK_NORRIS);
        URLConnection chuckConnection = chuckUrl.openConnection();
        chuckConnection.addRequestProperty("User-Agent", 
        		"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        JsonNode rootNode = new ObjectMapper().readTree(new JsonFactory().createJsonParser(new InputStreamReader(
        		chuckConnection.getInputStream())));
        JsonNode rootCondition = rootNode.path("value");
        return rootCondition.getTextValue();
    }
}