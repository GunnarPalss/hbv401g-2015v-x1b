/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database.common;

import java.nio.ByteBuffer;

public class Order {
	public final String order;
	public final String target;
	
	Order(String order, String target) {
		this.order = order;
		this.target = target;
	}
	
	public byte[] Serialize() {
		ByteBuffer bb = ByteBuffer.allocate(8 + order.length() + target.length());
		Utilities.writeString(bb, order);
		Utilities.writeString(bb, target);
		
		return bb.array();
	}
	
	public static Order Deserialize(byte[] bytes) {
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		String order = Utilities.readString(bb);
		String target = Utilities.readString(bb);

		return new Order(order, target);
	}
    
    public String[] receiveDataToOrder(String data, int size){
        return new String[0];
    }
}
