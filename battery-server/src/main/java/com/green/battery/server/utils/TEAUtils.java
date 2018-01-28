package com.green.battery.server.utils;

import java.util.Arrays;


/**
 * 
 * 
 * TEA加密解密：
 * 
 * 密钥：128bits   16字节
 * 
 * 每次加密8个字节 即 64位
 * 
 * @author Administrator
 *
 */
public class TEAUtils {
	
	public static void main(String args[]){
		byte[] data = new byte[]{0x0d,0x34,0x63,0x24,0x05,0x06,0x7f,0x08};
		byte[] key = new byte[]{0x78,(byte)0x9f,0x56,0x45, (byte)0xf6,(byte)0x8b,(byte)0xd5,(byte)0xa4, (byte)0x81,(byte)0x96,0x3f,(byte)0xfa, 0x45,(byte)0x8f,(byte)0xac,0x58};
		
		System.out.println(Arrays.toString(data));
		byte[] r = encrypt(data, key);
		System.out.println(Arrays.toString(r));
		
		byte[] content = decrypt(r, key);
		System.out.println(Arrays.toString(content));
	}
	
	 /**
     * 任意字节块的加密
     * @param content byte[] 任意长度
     * @param key byte[]
     * @return byte[] 加密后内容字节长度必为8的整数倍
     */
    public static byte[] encrypt(byte[] content, byte[] key) {
        if (content == null || key == null || content.length == 0 ||
            key.length == 0) {
            return content;
        }
        byte[] result = null;
        int resultLength = content.length;
        int mol = resultLength % 8; // 将原始内容长度对8取模
        if (mol != 0) {
            resultLength = resultLength + 8 - mol; // 如果长度不是8的整数倍则补足为8的整数倍
        }
        int[] k = validateKey(key); // 校验密钥
        int[] v = new int[2];
        int[] o = null;
        result = new byte[resultLength];
        int convertTimes = resultLength - 8; // 循环次数
        int next = 0;
        int times = 0;
        // 对于N个8字节块加密的顺序可以自己设置,不一定是从左到右
        for (; times < convertTimes; times += 8) { // 按照步长8依次加密
            next = times + 4;
            v[0] = byte2int(content, times);
            v[1] = byte2int(content, next);
            o = encrypt(v, k); // 字节块加密
            int2byte(o[0], result, times);
            int2byte(o[1], result, next);
        }
        next = times + 4;
        if (mol != 0) { // 最后一次加密
            byte[] tmp = new byte[8];
            System.arraycopy(content, times, tmp, 0, mol);
            v[0] = byte2int(tmp, 0);
            v[1] = byte2int(tmp, 4);
            o = encrypt(v, k);
            int2byte(o[0], result, times);
            int2byte(o[1], result, next);
        }
        else {
            v[0] = byte2int(content, times);
            v[1] = byte2int(content, next);
            o = encrypt(v, k);
            int2byte(o[0], result, times);
            int2byte(o[1], result, next);
        }
        return result;
    }

    /**
     * 字节块的解密
     * @param content byte[]
     * @param key byte[]
     * @return byte[]
     */
    public static byte[] decrypt(byte[] content, byte[] key) {
        if(content==null||key==null||content.length==0||key.length==0){
            return content;
        }
        if (content.length % 8 != 0) {
            throw new IllegalArgumentException("Content cannot be decypted!");
        }
        byte[] result = null;
        int[] k = validateKey(key); // 校验密钥
        int[] v = new int[2];
        int[] o = null;
        result = new byte[content.length];
        int convertTimes = content.length;
        int next = 0;
        int times = 0;
        for (; times < convertTimes; times += 8) { // 按照步长8依次解密
            next = times + 4;
            v[0] = byte2int(content, times);
            v[1] = byte2int(content, next);
            o = decrypt(v, k);
            int2byte(o[0], result, times);
            int2byte(o[1], result, next);
        }
        // 如果有必要则必须将多于的1~7个字节的空白去掉
//        convertTimes = convertTimes-8;
//        for(times=convertTimes+1;times<content.length;times++){
//            if(result[times]==(byte)0)break; // 退出
//        }
//        byte[] tmp=result;
//        result = new byte[times];
//        System.arraycopy(tmp,0,result,0,times); // 复制非空的内容
        return result;
    }
    
    
    /**
     * 字节数组转换成整型,高字节在前,低字节在后
     * @param buf byte[] 字节数组。
     * @param offset int 待转换字节开始的位置。
     * @return 四字节整型
     */
    private static int byte2int(byte[] buf, int offset) {
        return
            ( buf[offset + 0] & 0x000000ff) |
            ((buf[offset + 1] & 0x000000ff) << 8) |
            ((buf[offset + 2] & 0x000000ff) << 16) |
            ((buf[offset  + 3  ] & 0x000000ff) << 24);
    }

    /**
     * 整型转换成字节,高字节在前,低字节在后
     * @param integer int 四字节整型
     * @param buf byte[] 存放转换结果的字节数组。
     * @param offset int 存放位置的偏移地址
     */
    private static void int2byte(int integer, byte[] buf, int offset) {
        buf[offset  +3  ] = (byte)(integer >> 24);
        buf[offset + 2] = (byte)(integer >> 16);
        buf[offset + 1] = (byte)(integer >> 8);
        buf[offset + 0] = (byte) integer;
    }

    /**
     * 将字节数组内容按照十六进制码打印出来
     * @param buf byte[]
     * @param split String
     * @return String
     */
    private static String bytes2hex(byte[] buf,String split){
        int len=buf.length;
        StringBuffer chars=new StringBuffer();
        byte tmp;
        for(int i=0;i<len;i++){
            chars.append(split);
            tmp=(byte)((buf[i]>>4)&0x0F);
            if(tmp<0x0A){
                tmp+=0x30;
            }
            else{
                tmp+=0x37;
            }
            chars.append((char)tmp);
            tmp=(byte)(buf[i]&0x0F);
            if(tmp<0x0A){
                tmp+=0x30;
            }
            else{
                tmp+=0x37;
            }
            chars.append((char)tmp);
        }
        return chars.substring(split.length());
    }

    /**
     * 校验密钥,如果不足16字节则末尾填充字节0补足16字节,如果大于16字节则丢弃末尾多余的字节
     * @param key byte[]
     * @return int[] 长度为4的四字节类型数组
     */
    private static int[] validateKey(byte[] key) {
        byte[] tmpkey = key;
        if (key.length < 16) { // 不够16字节则以0补齐,也可以使用其它更好的方法补齐
            tmpkey = new byte[16];
            System.arraycopy(key, 0, tmpkey, 0, key.length);
        }
        int[] k = new int[] {
                  byte2int(tmpkey, 0), 
                  byte2int(tmpkey, 4),
                  byte2int(tmpkey, 8), 
                  byte2int(tmpkey, 12)
        };
        return k;
    }
	
	 /**
	   * 基本的8bytes数据块加密
	   * @param v int[] 8bytes data
	   * @param k int[] 16bytes key
	   * @return int[] 8bytes data
	   */
	  private static int[] encrypt(int[] v,int[] k){
	        int y=v[0],z=v[1],sum=0,delta=0x9E3779B9,
	                a=k[0],b=k[1],c=k[2],d=k[3],n=32;
	        int[] o=new int[2];
	        while(n-->0){
	            sum+=delta;
	            y+=((z << 4)+a)^(z+sum)^((z >>> 5)+b);
	            z+=((y << 4)+c)^(y+sum)^((y >>> 5)+d);
	        }
	        o[0]=y;
	        o[1]=z;
	        return o;
	    }

	    /**
	     * 基本的8bytes数据块解密
	     * @param v int[] 8bytes data
	     * @param k int[] 16bytes key
	     * @return int[] 8bytes data
	     */
	    private static int[] decrypt(int[] v,int[] k){
	        int y=v[0],z=v[1],sum=0xC6EF3720,
	                delta=0x9E3779B9,a=k[0],b=k[1],c=k[2],d=k[3],n=32;
	        int[] o=new int[2];
	        while(n-->0){
	            z-=((y << 4)+c)^(y+sum)^((y >>> 5)+d);
	            y-=((z << 4)+a)^(z+sum)^((z >>> 5)+b);
	            sum-=delta;
	        }
	        o[0]=y;
	        o[1]=z;
	        return o;
	    }
    
}
