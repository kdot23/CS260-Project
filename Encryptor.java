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
		
		public void encrypt(File oldFile, String newFileName)
		{
			byte[][] m = createFileMatrix (oldFile);
			byte[][] newm = reorderMatrix (m);
			openNewFile(newFileName);
			fillEncryptedFile(newm);						
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
			double size = oldFile.length();
			int numRows = (int) Math.ceil(size / key.length) + 1; //add one for the key row
			byte[][] matrix = new byte[numRows][key.length];
			//System.out.println("numRows = " + numRows);
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
			
			/*for (int j = 0; j < matrix[0].length; j++)
			{
				while (input.read(matrix[numRows]) != -1)
				{
					
				}
			}
			int j = 0;
			while (input.read(matrix[numRows]) != -1)
			{
				
			} */
			return matrix;			
		}
		
		public byte[][] reorderMatrix(byte[][] M)
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
				//System.out.println("k loop " + k);
				for (int j = 0; j < M[0].length; j++)
				{
					//System.out.println("M[0] loop " + j);

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
						
			}
			for (int i = 0; i < newMatrix.length; i++)
			{
				for (int j =0; j < newMatrix[0].length; j++)
				{
					System.out.println(newMatrix[i][j]);
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
		/*
			ArrayList<Integer> keyList = new ArrayList<Integer>();
			for (int i = 0; i < key.length; i++)
			{
				Integer k = (Integer) (int) key[i];
				keyList.add(k);
			}
			Collections.sort(keyList);
			System.out.println("keyList:" + keyList.size() + " M[0]:" + M[0].length + " M:" + M.length);
			for (int k = 0; k < keyList.size(); k++)
			{
				System.out.println("k loop " + k);
				for (int i = 0; i < M[0].length; i++)
				{
					System.out.println("M[0] loop " + i);

					if (keyList.get(k) == M[0][i])
					{
						System.out.println("i val = "  + i);
						for (int j = 1; j < M.length; j++ )
						{
							System.out.println("M loop " + j);

							try
							{
								output.write(M[j][i]);
							}
							catch (IOException exception)
							{
								System.err.println("Error adding data to file. Terminating.");
								System.exit(1);
							}							
						}
						break;
					}
				}
			}
			
		} */

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


