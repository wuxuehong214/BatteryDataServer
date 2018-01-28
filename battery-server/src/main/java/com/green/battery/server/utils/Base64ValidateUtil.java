package com.green.battery.server.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.mina.proxy.utils.ByteUtilities;

import com.green.battery.server.model.BatteryRecordEntity;
import com.mchange.lang.ByteUtils;

public class Base64ValidateUtil {
	
	public byte[] getBytes() throws IOException{
		byte[] data = new byte[1029];
		BufferedReader br = new BufferedReader(new FileReader(new File("a.txt")));
		String str = br.readLine();
		str = br.readLine();
		Scanner s = null;
		String c;
		int index = 0;
		while(str !=null){
			s = new Scanner(str);
			s.next();
			c  = s.next();
			data[index++] = Integer.decode(c).byteValue();
			str = br.readLine();
		}
		br.close();
		return data;
	}
	
	public String getBase64Str() throws IOException{
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new FileReader(new File("b.txt")));
		String str = br.readLine();
		str = br.readLine();
		str = br.readLine();
		Scanner s = null;
		String v;
		while(str != null){
			s = new Scanner(str);
			s.next();
			v = s.next();
			v = v.substring(1, v.length()-1);
			sb.append(v);
			str = br.readLine();
		}
		br.close();
		return sb.toString();
	}
	
	public static void main(String args[]) throws IOException{
		Base64ValidateUtil util = new Base64ValidateUtil();
		byte[] data = util.getBytes();
		
		String begin = Base64.encodeBase64String(data);
		System.out.println(begin);
		String str = util.getBase64Str();
		
		System.out.println(data.length);
		System.out.println(str);
		System.out.println(ByteUtilities.asHex(data, " "));
		
		
		String test = "/wDnCAAcAAUCAQkARUYAANgcGRwAAAAAFg4TDhMOEg4SDgkOCg4IDgcOCQ4AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHAAFAgETAEVGAADYHBkcAAAAABUOEw4TDhIOEg4JDgkOCA4IDgkOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABwABQIBFwJFRgAA2BwZHAAAAAAVDhMOEw4SDhIOCg4JDggOCA4JDgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAcAAUCARwARUYAANgcGRwAAAAAFQ4TDhMOEg4SDgkOCQ4IDggOCQ4AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHAAFAgEfA0VGAADYHBkcAAAAABUOEw4TDhIOEg4JDgkOCA4IDgkOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABwABQIBJAJGRgAA2BwZHAAAAAAVDhMOEw4SDhIOCQ4KDggOCA4KDgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAcAAUCAScDRkYAANgcGRwAAAAAFQ4TDhMOEg4SDgoOCQ4IDggOCg4AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHAAFAgE0AEZGAADYHBkcAAAAABUOEw4TDhIOEg4JDgoOCA4IDgoOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABwABQICAgBGRgAA2BwZHAAAAAAVDhMOEw4SDhIOCg4KDggOCA4KDgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAcAAUCAgwARkYAANgcGRwAAAAAFQ4TDhMOEg4SDgoOCg4IDggOCg4AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHAAFAgIWAEZGAADYHBkcAAAAABYOEw4TDhIOEg4JDgoOCA4IDgoOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABwABQICIABGRgAA2BwZHAAAAAAVDhMOEw4SDhIOCg4KDggOCA4JDgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAcAAUCAioARkYAANgcGRwAAAAAFQ4TDhMOEg4SDgkOCg4IDggOCg4AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHAAFAgI0AEVGAADYHBkcAAAAABUOEw4TDhIOEg4JDgoOCA4IDgkOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABwABQIDAgBGRgAA2BwZHAAAAAAVDhMOEw4SDhIOCg4KDggOCA4KDgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAcAAUCAwwARkYAANgcGRwAAAAAFQ4TDhMOEg4SDgkOCg4IDggOCg4AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=";

		System.out.println();
		byte[] decode  = Base64.decodeBase64(test);
		System.out.println(decode.length);
		System.out.println(ByteUtilities.asHex(decode, " "));
		
		byte[] datas = Arrays.copyOfRange(decode, 4, decode.length);
		System.out.println(ByteUtilities.asHex(datas, " "));
		
		int size =  datas.length/64;
		for(int i=0;i<size;i++){
			byte[] tmp = Arrays.copyOfRange(datas, 64 * i, 64 * i + 64); // 加密后的电池记录数据
			
			// 解密
			byte[] bd = TEAUtils.decrypt(tmp, KeyUtils.getKey(0));
			System.out.println("####################");
			System.out.println("TEA加密前数据:"+ByteUtilities.asHex(tmp, " "));
			System.out.println("TEA解密前数据:"+ByteUtilities.asHex(bd, " "));
			BatteryRecordEntity record = BatteryRecordEntity.convet(tmp);
			System.out.println(record.toString());
		}
		
		byte[] d = new byte[]{0x00, 0x1c, 0x00, 0x05, 0x02, 0x01, 0x09, 0x01};
		byte[] a = TEAUtils.decrypt(d, KeyUtils.getKey(0));
		System.out.println("begin:"+ByteUtilities.asHex(d, " "));
		System.out.println("after:"+ByteUtilities.asHex(a, " "));
		//f8 f4 13 34 40 db 20 79
		//39 9b c1 a4 cc 78 4d 7e
	}

}
