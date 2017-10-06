import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileOutputStream;
import java.util.Formatter;

public class EncryptorDriver {
	private static final String originalFileName = "original.txt";
	private static final String encryptedFileName = "encrypted.txt";
	private static final String decryptedFileName = "decrpyted.txt";	
	public static final byte[] key = {3,5,9,0,2,8,6,7,1,4};
	public static String ORIGINAL_STRING = "Hi, my name is Kelsey.";
	public static Formatter output;
	
	public static void main(String[] args) 
	{
		File f = makeOriginalFile(ORIGINAL_STRING);	
		System.out.println(f.length());
		Encryptor e = new Encryptor (key);
		//byte[][] m = e.createFileMatrix(f);
		e.encrypt(f, encryptedFileName);

		//e.encrypt(originalFile, encryptedFileName);
		//e.decrypt(encryptFile, decryptedFileName);

	}
	
	public static File makeOriginalFile(String data)
	{
				try
				{
					output = new Formatter(originalFileName);
				}
				catch (FileNotFoundException e)
				{
					System.exit(1);
				}
				output.format("%s", ORIGINAL_STRING);
				output.close();
				return new File(originalFileName);
	}

}
