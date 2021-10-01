package com.sample.page.element.provider;



import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.sample.page.BY;
import com.sample.page.ElementDescriptor;
import com.sample.page.element.Element;
import com.sample.page.element.ElementFactory;



public class MongoElementProvider extends AbstractElementProvider{


	/** The element map. */

	private Map<String,Element> elementMap = new HashMap<String,Element>(20);



	private String connectionUrl;
	private String dataBaseName;
	private String collectionName;





	/**

	 * Instantiates a new CSV element provider.

	 *

	 * @param resourceName the resource name

	 * @param tabNames the tab names

	 */

	public MongoElementProvider( String connectionUrl, String dataBaseName,String collectionName )

	{

		this.connectionUrl = connectionUrl;
		this.dataBaseName= dataBaseName;
		this.collectionName = collectionName;

		readElements();

	}



	/**

	 * Read elements.

	 */

	private void readElements() {

		try {
			if((connectionUrl!=null&&!connectionUrl.isEmpty())&&(dataBaseName!=null&&!dataBaseName.isEmpty())
					&& (collectionName!=null&&!collectionName.isEmpty())) {
				boolean elementsRead = true;
				MongoCursor<Document> cur=  fetchElementDetails();
				
				if(cur!=null) {
				   	while (cur.hasNext()) {
						Document doc = cur.next();
						ElementPOJO element	 = new ObjectMapper().readValue(doc.toJson(), ElementPOJO.class);
						String siteName= element.getSiteName();
						String pageName =element.getPageName();
						String eltName = element.getName();
						String eltDesc = element.getDescriptor();
						String eltVal = element.getValue();
						String contextName = null;
						ElementDescriptor elementDescriptor = new ElementDescriptor( siteName,
								                                                      pageName,
								                                                      eltName );
						Element currentElement = ElementFactory.instance().createElement( BY.valueOf( eltDesc ),
								eltVal.replace( "$$", ","),
								eltName,
								pageName,
								contextName );
						boolean rtn=/*validateElement( elementDescriptor, currentElement )*/ true;

						elementsRead = elementsRead & rtn;

						if(!rtn)

							System.out.println( "****Failed adding Excel Element using [" + elementDescriptor.toString() + "] as [" + currentElement );


						elementMap.put(elementDescriptor.toString(), currentElement );

					}
					setInitialized( elementsRead );
				}
			}
		}
		catch(Exception c) {
			log.fatal( "Error reading MongoDb for pageelements", c );
		}
	}


	private MongoCursor<Document> fetchElementDetails() {
		MongoCursor<Document> cur = null;
		try {
		MongoClient mongoClient = new MongoClient(new MongoClientURI(this.connectionUrl));
		MongoDatabase database = mongoClient.getDatabase(this.dataBaseName);
		MongoCollection<Document>   collection = database.getCollection(this.collectionName);
	 	Bson projections = Projections.exclude("_id");

    	 cur = collection.find().projection(projections).iterator();
		}
		catch(Exception c) {
			log.error("unable to connect and retrieve collection from mongo db"+c);
		}
		return cur;
	}


  

	protected Element _getElement( ElementDescriptor elementDescriptor )

	{

		return elementMap.get(  elementDescriptor.toString() );

	}


	
}
