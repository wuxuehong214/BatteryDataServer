package com.green.power.data.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBFactory {

	private String ip;
	private int port = 27017;
	private static MongoDBFactory instance;
	private Logger logger = Logger.getLogger(MongoDBFactory.class);

	private MongoDBFactory() {
		InputStream is = null;
		try {
			is = new FileInputStream(new File("conf/mongodb.conf"));
		} catch (FileNotFoundException e) {
			logger.warn(e.getMessage());
			logger.debug("No conf/mongodb.conf defined!");
		}

		if (is != null) {
			Properties p = new Properties();
			try {
				p.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ip = p.getProperty("ip");
			String port_s = p.getProperty("port");
			if (port_s != null)
				port = Integer.parseInt(port_s);
		}
		
		if(ip == null){
			logger.debug("Read mongodb.conf in class path!");
			is = MongoDBFactory.class.getClassLoader().getResourceAsStream("mongodb.conf");
			if (is != null) {
				Properties p = new Properties();
				try {
					p.load(is);
				} catch (IOException e) {
					e.printStackTrace();
				}
				ip = p.getProperty("ip");
				String port_s = p.getProperty("port");
				if (port_s != null)
					port = Integer.parseInt(port_s);
			}
		}
		logger.info("Mongodb_ip:"+ip+"\tMongodb_port:"+port);
	}

	public synchronized static MongoDBFactory getInstance() {
		if (instance == null)
			instance = new MongoDBFactory();
		return instance;
	}

	private MongoDatabase getDatabase(){
		@SuppressWarnings("resource")
		MongoClient client = new MongoClient(ip,port);
		return client.getDatabase(DBUtil.DATABASE_NAME);
	}
	
	public MongoCollection<Document> getCollection(String collection_name) {
		MongoDatabase db = getDatabase();
		if(db != null)
			return db.getCollection(collection_name);
		return null;
	}

	public static void main(String args[]) {
//		MongoDBFactory f = MongoDBFactory.getInstance();
		
		MongoClient client = new MongoClient( "124.232.154.55", 27017);
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
		}
//		MongoDatabase db = f.getDatabase();
//		MongoCollection<Document> conn = db.getCollection(DBUtil.COLLECTION_BATTERYINFO_NAME);
		
//		// DataEntity data = new DataEntity();
//		// data.setMsg("info");
//		// data.setData(new byte[]{0x01,0x02,0x03});
//		// String json = JSONObject.toJSONString(data);
//		// Document d = Document.parse(json);
//		// conn.insertOne(d);
//		// db.getCollection("resource").insertOne(new Document().append("name",
//		// "wxh").append("age", 123));
//		// db.getCollection("resource").insertOne(new Document().append("class",
//		// "0609").append("sex", "female"));
//		FindIterable<Document> iterable = conn.find();
//		iterable.forEach(new Block<Document>() {
//			public void apply(Document d) {
//				System.out.println(d);
				
//				BatteryEntity b = BatteryEntity.toBatteryEntity(d);
//				
//				Map<String,Object> map = b.getExtInfo();
//				
//				System.out.println(map);
//				
//				Iterator<String> it = map.keySet().iterator();
//				while(it.hasNext()){
//					String key = it.next();
//					System.out.println(key+"==>"+map.get(key));
//				}
//				
//				
//				String json = JSONObject.toJSONString(b);
//				System.out.println(json);
//				
//				BatteryEntity bb = JSONObject.parseObject(json,BatteryEntity.class);
//				System.out.println(bb.getExtInfo());
//				System.out.println(d.toJson());
//				System.out.println(d.get("sex"));
//			}
//		});
	}

}
