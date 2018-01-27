package com.green.power.data.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class BatteryDao {
	
	public boolean put(Document battery){
		MongoDBFactory db = MongoDBFactory.getInstance();
		MongoCollection<Document> collection = db.getCollection(DBUtil.COLLECTION_BATTERYINFO_NAME);
		try{
			collection.insertOne(battery);
		}catch(Exception e){
			System.out.println("============================================================================");
			return false;
		}
		return true;
	}
	
	public Document query(String serialNum){
		MongoDBFactory db = MongoDBFactory.getInstance();
		MongoCollection<Document> collection = db.getCollection(DBUtil.COLLECTION_BATTERYINFO_NAME);
		FindIterable<Document> iterable = collection.find(new Document("serialNum",serialNum));
		final List<Document> list = new ArrayList<Document>();
		iterable.forEach(new Block<Document>() {
			public void apply(Document d) {
				list.add(d);
			}
		});
		if(list.size()>0)
			return list.get(0);
		return null;
	}

}
