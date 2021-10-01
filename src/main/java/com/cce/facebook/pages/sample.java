package com.cce.facebook.pages;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sample.page.element.provider.ElementPOJO;

public class sample {
	public static void main(String[] args) {
		  try {
				CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
		                fromProviders(PojoCodecProvider.builder().automatic(true).build()));


			
			MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://sam_user_87:Musk_12345@clusterbot.uchdg.mongodb.net/AutomationPOC?retryWrites=true&w=majority"));
			MongoDatabase database = mongoClient.getDatabase("AutomationPOC").withCodecRegistry(pojoCodecRegistry);
			MongoCollection<Document>   collection = database.getCollection("pageElements");
			FindIterable<Document> myDoc = collection.find();
			List<ElementPOJO> products = collection.find(new Document(), ElementPOJO.class).into(new ArrayList<ElementPOJO>());
	      System.out.println(products.size());
	      for(ElementPOJO doc:products) {
	      	System.out.println(doc.getName());
	      }
			//DBCursor cursor = collection.find();
			/*while (cursor.hasNext()) {
			    System.out.println(cursor.next());
			}*/
			}
			catch(Exception c) {
				System.out.println(c.toString());
			}
			 
	}
}
