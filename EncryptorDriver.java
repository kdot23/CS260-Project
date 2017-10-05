import java.io.File;

public class EncryptorDriver {
	private static final String originalFileName = "original.txt";
	private static final String encryptedFileName = "encrypted.txt";
	private static final String decryptedFileName = "decrpyted.txt";	
	public static final byte[] key = {3,5,9,0,2,8,6,7,1,4};
	
	public static void main(String[] args) 
	{
		File originalFile = new File(originalFileName);
		
		Encryptor e = new Encryptor (key);
		e.createFileMatrix(originalFile).toString();
		//e.encrypt(originalFile, encryptedFileName);
		//e.decrypt(encryptFile, decryptedFileName);

	}

}
