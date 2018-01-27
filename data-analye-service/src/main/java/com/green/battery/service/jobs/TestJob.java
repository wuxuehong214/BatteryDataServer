package com.green.battery.service.jobs;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.green.analyze.battery.entity.RecordEntity;
import com.mchange.lang.ByteUtils;

public class TestJob implements Job {

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
//		System.out.println("Struts 2.3.16 + Quartz2.2.2");
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		System.out.println("end");
		
		byte f = (byte) 0xff;
		
		System.out.println(ByteUtils.toHexAscii(f));
		System.out.println(ByteUtils.toUnsigned(f));
	}
	
	public static void main(String args[]) throws FileNotFoundException{
		byte f = (byte) 0x3f;
		
		System.out.println(ByteUtils.toHexAscii(f));
		System.out.println(ByteUtils.toUnsigned(f));
		
		byte[] ff = new byte[]{0x00,(byte)0xff};
		int a = (ff[0]<<8)&0xff00|ff[1]&0x0ff;
		System.out.println(a);
		
		
		System.out.println("##");
		System.out.println(0x3ff&0x0ffff);
		
		File file = new File("g:/GREENWAY.GWR");
		InputStream is = new FileInputStream(file);
		byte[] buffer = new byte[64];
		int len = -1;
		try {
			int count = 0;
			while((len = is.read(buffer))!=-1){
				System.out.println(len+":"+convert(buffer));
				if(len == 64){
						System.out.println(RecordEntity.convet(buffer).toString());
				}else{
					
				}
				System.out.println();
				count++;
			}
			System.out.println(count);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String convert(byte[] b){
		StringBuffer sb = new StringBuffer();
		for(byte bb:b){
			sb.append(ByteUtils.toUnsigned(bb)+" ");
		}
		return sb.toString();
	}
}
