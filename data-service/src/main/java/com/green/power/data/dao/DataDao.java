package com.green.power.data.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.green.power.data.entity.BatteryDataEntity;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class DataDao {
	
	public boolean put(List<Document> datas){
		if(datas == null) return false;
		MongoDBFactory db = MongoDBFactory.getInstance();
		MongoCollection<Document> collection = db.getCollection(DBUtil.COLLECTION_BATTERYDATA_NAME);
		try{
			collection.insertMany(datas);
		}catch(Exception e){
			System.out.println("============================================================================");
			return false;
		}
		return true;
	}
	
	public List<BatteryDataEntity> query(String serialNum){
		MongoDBFactory db = MongoDBFactory.getInstance();
		MongoCollection<Document> collection = db.getCollection(DBUtil.COLLECTION_BATTERYDATA_NAME);
		FindIterable<Document> iterable = collection.find(new Document("serialNum",serialNum));
		final List<BatteryDataEntity> datas = new ArrayList<BatteryDataEntity>();
		iterable.forEach(new Block<Document>() {
			public void apply(Document d) {
//				String json = d.toJson();
//				DataEntity data = JSONObject.parseObject(json, DataEntity.class);
				BatteryDataEntity data = BatteryDataEntity.toDataEntity(d);
				datas.add(data);
			}
		});
		return datas;
	}

}
