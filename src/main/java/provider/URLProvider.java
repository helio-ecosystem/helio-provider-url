package provider;

import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

import helio.blueprints.components.DataProvider;



public class URLProvider implements DataProvider{

	private static final long serialVersionUID = 1L;
	private String resourceURL;
	private Logger logger = LoggerFactory.getLogger(URLProvider.class);

	/**
	 * This constructor creates an empty {@link URLProvider} that will need to be configured using a valid {@link JsonObject}
	 */
	public URLProvider() {
		super();
	}


	/**
	 * This constructor instantiates a valid {@link URLProvider} with the provided iterator
	 * @param resourceURL a valid URL referencing any of the implemented protocols
	 */
	public URLProvider(String resourceURL) {
		this.resourceURL = resourceURL;
	}

	public InputStream getData() {
		InputStream output = null;
		try {
			URL urlFile = new URL(resourceURL);
			output = urlFile.openStream();
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return  output;
	}

	public void configure(JsonObject configuration) {
		if(configuration.has("url")) {
			String resourceURLAux = configuration.get("url").getAsString();
			if(resourceURLAux.isEmpty()) {
				throw new IllegalArgumentException("URLProvider needs to receive non empty value for the key 'url'");
			}else{
				this.resourceURL = resourceURLAux;
			}
		}else {
			throw new IllegalArgumentException("URLProvider needs to receive json object with the mandatory key 'url'");
		}
	}
}
