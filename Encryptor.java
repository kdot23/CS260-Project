import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Math;

/*
 * This class encrypts code using a row transposition technique. The Encryptor is constructed 
 * by a key of integers. The program creates a matrix that has the same number of columns as
 * the key and reads the file into that matrix row by row. The columns of the matrix are reconfigured 
 * so that the key (first row) is in ascending order. Then the characters are read column by column
 * to form the encrypted text.
 * 
 */
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
			openNewFile(newFileName);			
			byte[][] M = createMatrixForEncryption (oldFile);
			byte[][] newM = encryptMatrix (M);
			fillEncryptedFile(newM);
		}
		public void decrypt(File oldFile, String newFileName)
		{			
			openNewFile(newFileName);			
			byte[][] M = createMatrixForDecryption (oldFile);
			byte[][] newM = decryptMatrix (M);
			fillEncryptedFile(newM);
		}
		
		
		public byte[][] createMatrixForEncryption(File oldFile)
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
			double size = oldFile.length();
			int numRows = (int) Math.ceil(size / key.length) + 1; //add one for the key row
			byte[][] matrix = new byte[numRows][key.length];
			matrix[0] = key;
			for (int i = 1; i < numRows; i++) //fills all the rows
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
		
		public byte[][] encryptMatrix(byte[][] M)
		{
			//Makes an ArrayList of key so that it can be sorted
			ArrayList<Integer> keyList = new ArrayList<Integer>();
			for (int i = 0; i < key.length; i++)
			{
				Integer k = (Integer) (int) key[i];
				keyList.add(k);
			}
			Collections.sort(keyList);
			
			//new Matrix has inverse dimensions for easy reading
			byte[][] newMatrix = new byte[M[0].length][M.length]; 
			
			//Sorting the columns so the key is in ascending order
			for (int k = 0; k < keyList.size(); k++)
			{
				//find the column which has the key element, k
				for (int j = 0; j < M[0].length; j++)
				{ 
					if (keyList.get(k) == M[0][j])
					{
						//makes an array of the correct column
						byte[] babyArray = new byte[M.length - 1];
						for (int i = 1; i < M.length; i++ )
						{
							babyArray[i-1] = M[i][j];
						}
						//places babyArray in the kth row of the new matrix
					newMatrix[k] = babyArray;
					}
				}
						
			}

			return newMatrix;
		}
		
		//creates a file with the inverse dimensions as the matrix for encryption
		public byte[][] createMatrixForDecryption(File oldFile)
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
			
			double size = oldFile.length();
			int numCols = (int) Math.ceil(size / key.length); 
			byte[][] matrix = new byte[key.length][numCols];
			
			for (int i = 0; i < key.length; i++) //fills all the rows
			{
				try
				{
					input.read(matrix[i], 0, numCols);
				}
				catch (IOException exception)
				{
					System.err.println("Error adding data to file. Terminating.");
					System.exit(1);
				}
			}

			return matrix;			
		}
		
		public byte[][] decryptMatrix(byte[][] M)
		{ 
			//create an ArrayList to use indexOf() method
			ArrayList<Integer> keyList = new ArrayList<Integer>();
			for (int i = 0; i < key.length; i++)
			{
				Integer k = (Integer) (int) key[i];
				keyList.add(k);
			}
			
			byte[][] newMatrix = new byte[M[0].length][M.length];
			for (int j = 0; j < M.length; j++)
			{
				int newIndex = keyList.indexOf((int) j);
				for (int i = 0; i < M[0].length; i++)
				{
					newMatrix[i][newIndex] = M[j][i];
				}
			}
			return newMatrix;
		}
		public void fillEncryptedFile(byte[][] M)
		{
			for (int i = 0; i < M.length; i++)
			{
				for (int j = 0; j < M[0].length; j++)
				{
					try
					{
						if (M[i][j] != 0)
						{
							output.write(M[i][j]);
						}
						else
						{
							output.write(32); //writes a space, if the element was null
						}
						
					}
					catch (IOException exception)
					{
						System.err.println("Error adding data to file. Terminating.");
						System.exit(1);
					}	
				}
			}
		}


		public void openNewFile(String name)
		{
				try
				{
					output = new FileOutputStream(name);
				}				
				catch (FileNotFoundException fileNotFoundException)
				{
					System.err.println("Error opening file. Terminating.");
					System.exit(1);
				} 
		}		
}


