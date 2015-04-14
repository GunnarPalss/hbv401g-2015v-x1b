/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.common;

import java.nio.ByteBuffer;

/**
 *
 * @author Wolf
 */
public class Utilities
{
	public static String readString(ByteBuffer bb) {
		int fieldLength = bb.getInt();
		String field = "";
		for (int i = 0; i < fieldLength; i++)
			field += bb.getChar();
		
		return field;
	}
	
	public static void writeString(ByteBuffer bb, String str) {
		bb.putInt(str.length());
		for (int i = 0; i < str.length(); i++)
			bb.putChar(str.charAt(i));
	}
	
	public static int countOccurrences(String str, char lookingForGroup)
	{
		int count = 0;
		for (int i=0; i < str.length(); i++)
		{
			if (str.charAt(i) == lookingForGroup)
			{
				 count++;
			}
		}
		return count;
	}
}
