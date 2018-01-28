package com.green.battery.server.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.apache.commons.codec.binary.Hex;

public class TEAValidateUtil {
	
	public static void main(String args[]){
		
		//4D13784ADF1F53DD234B45395D598646
		//0x20,0x00,0x04,0x70,0x00,0x00,0x3f,(byte)0xbd
		byte[] code = KeyUtils.getKey(2);
//		byte[] encoded  = new byte[]{0x39,(byte)0x92,0x50,0x04,(byte)0xF5,(byte)0xC1,0x43,(byte)0xAB,(byte)0x8E,(byte)0xBB,0x33,0x46,0x6A,(byte)0xE4,0x74,0x34};
//		byte[] data = new byte[]{0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		byte[] data = new byte[]{(byte)0xD8,0x05,0x00,0x20,0x21,0x7A,0x00,0x00,0x13,0x6C,0x00,0x00,(byte)0xB3,0x74,0x00,0x00};
		System.out.println("加密密钥:"+Hex.encodeHexString(code));
		//12ccea186f5e6322858dee9c140d6cf2
		System.out.println("原始数据:"+Hex.encodeHexString(data));
		byte[] decoded = TEAUtils.encrypt(data, code);
		System.out.println("加密后数据:"+Hex.encodeHexString(decoded));
		
		byte[] d = TEAUtils.decrypt(decoded, code);
		System.out.println("解密后数据:"+Hex.encodeHexString(d));
		//00dde336f0f571bdd1dde336f0f571bdd1
		//    dde336f0f571bdd1dde336f0f571bdd1
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("new.hex")));
			String str = br.readLine();
			for(int i=0;i<10;i++){
				String dd = str.substring(9, str.length()-2);
//				System.out.println(dd);
				byte[] ds = new byte[16];
				for(int j=0;j<16;j++){
					ds[j] = (byte)Short.parseShort(dd.substring(j*2, j*2+2),16);
				}
				
				byte[] encoded = TEAUtils.encrypt(ds, code);
				System.out.println(Hex.encodeHexString(encoded));
				str = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
