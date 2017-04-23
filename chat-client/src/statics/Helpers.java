package statics;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class Helpers {
	public static <T> byte[] convertToBytes(T object) throws IOException {
	    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        ObjectOutput out = new ObjectOutputStream(bos)) {
	        out.writeObject(object);
	        return bos.toByteArray();
	    } 
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T convertFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
	    try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
	         ObjectInput in = new ObjectInputStream(bis)) {
	        return (T) in.readObject();
	    } 
	}
}
