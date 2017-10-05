import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Math;
import java.util.Scanner;

public class Encryptor {
	private byte[] key;
	private static FileInputStream input;
	private FileOutputStream output;

	
		public Encryptor(byte[] key)
		{
			this.key = key;
		}
		
		public void encrypt(File oldFile, String newFileName)
		{
			createFileMatrix (oldFile);
						
		}
		
		public byte[][] createFileMatrix(File oldFile)
		{
			
			try //open FileInputStream of oldFile
			{
				input = new FileInputStream(oldFile);
			}
			catch (FileNotFoundException exception)
			{
				System.err.println("Error opening file. Try a different file name:");
				System.exit(1);
			}
			
			//fill a matrix of the correct size
			long size = oldFile.length();
			int numRows = (int) Math.ceil(size / key.length);
			byte[][] matrix = new byte[key.length][numRows];
			matrix[0] = key;
			for (int i = 1; i <= numRows; i++) //fills all the rows except the last one
			{
				try
				{
					input.read(matrix[i], 0, key.length);
				}
				catch (IOException exception)
				{
					System.err.println("Error adding data to file. Terminating.");
					System.exit(1);
				}
			}

			return matrix;
			
		}

		
}


