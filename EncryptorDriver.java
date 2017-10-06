import java.io.File;
import java.io.FileNotFoundException;
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

		Encryptor e = new Encryptor (key);
		e.encrypt(f, encryptedFileName);
		File encrypted = new File(encryptedFileName);
		e.decrypt(encrypted, decryptedFileName);

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
