import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Math;


public class Encryptor {
	private byte[] key;
	private static FileInputStream input;
	private FileOutputStream output;

	
		public Encryptor(byte[] key)
		{
			this.key = key;
		}
		
		public void encrypt(File oldFile, String newFileName, String mode)
		{
			
			openNewFile(newFileName);
			byte[][] newM;
			if (mode == "encrypt")
			{
				byte[][] M = createMatrixForEncryption (oldFile);
				newM = encryptMatrix (M);
				fillEncryptedFile(newM);
			}
			if (mode == "decrypt")
			{
				byte[][] M = createMatrixForDecryption (oldFile);
				newM = decryptMatrix(M);
				fillEncryptedFile(newM);
			}
												
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
			System.out.println("First matrix");
			for (int i = 0; i < matrix.length; i++)
			{
				for (int j = 0; j < matrix[0].length; j++)
				{
					System.out.printf("%4s",matrix[i][j] + " ");
				}
				System.out.println();
			}
			
			return matrix;			
		}
		
		public byte[][] encryptMatrix(byte[][] M)
		{
			ArrayList<Integer> keyList = new ArrayList<Integer>();
			for (int i = 0; i < key.length; i++)
			{
				Integer k = (Integer) (int) key[i];
				keyList.add(k);
			}
			Collections.sort(keyList);
			
			byte[][] newMatrix = new byte[M[0].length][M.length];
			for (int k = 0; k < keyList.size(); k++)
			{
				for (int j = 0; j < M[0].length; j++)
				{
					if (keyList.get(k) == M[0][j])
					{
						byte[] babyArray = new byte[M.length - 1];
						for (int i = 1; i < M.length; i++ )
						{
							babyArray[i-1] = M[i][j];
						}

					newMatrix[k] = babyArray;
					}
				}
						
			}
			System.out.println("Encrypted matrix");
			for (int i = 0; i < newMatrix.length; i++)
			{
				for (int j = 0; j < newMatrix[0].length; j++)
				{
					System.out.printf("%4s",newMatrix[i][j] + " ");
				}
				System.out.println();
			}

			return newMatrix;
		}
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
			
			//fill a matrix of the correct size
			double size = oldFile.length();
			int numCols = (int) Math.ceil(size / key.length); //add one for the key row
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
			System.out.println("2nd read in matrix");
			for (int i = 0; i < matrix.length; i++)
			{
				for (int j = 0; j < matrix[0].length; j++)
				{
					System.out.printf("%4s",matrix[i][j] + " ");
				}
				System.out.println();
			}
			
			return matrix;			
		}
		
		public byte[][] decryptMatrix(byte[][] M)
		{ 
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
			
/*
			byte[][] newMatrix = new byte[M[0].length-1][M.length];
			for (int i = 0; i < M.length; i++)
			{
				int newIndex = key[i];
				byte[] babyArray = new byte[newMatrix[0].length];
				for (int j = 1; j < M[0].length; j++ )
				{
					babyArray[j-1] = M[i][j];
				}
				for (int k = 0; k < newMatrix.length; k++)
				{
					newMatrix[k][newIndex] = babyArray[k];
				}
				
			} */
			System.out.println("Decrypted matrix");
			for (int i = 0; i < newMatrix.length; i++)
			{
				for (int j = 0; j < newMatrix[0].length; j++)
				{
					System.out.printf("%4s",newMatrix[i][j] + " ");
				}
				System.out.println();
			}
			
			/*byte[][] newMatrix = new byte[M[0].length][M.length];
			for (int k = 0; k < keyList.size(); k++)
			{
				for (int j = 0; j < M[0].length; j++)
				{
					if (keyList.get(k) == M[0][j])
					{
						System.out.println("j val = "  + j);
						byte[] babyArray = new byte[M.length - 1];
						for (int i = 1; i < M.length; i++ )
						{
							babyArray[i-1] = M[i][j];
						}
						for (int g = 0; g < babyArray.length; g++)
						{
							System.out.println(babyArray[g]);
						}

					newMatrix[k] = babyArray;
					}
				} 
						
			} */

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
							output.write(32);
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


