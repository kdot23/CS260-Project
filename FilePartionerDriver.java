/*
 * Driver class for the FilePartitioner
 * args are the names of the files that the user wants to partition (ie: "myFile100.txt")
 * 
 */
public class FilePartionerDriver {

	public static void main(String[] args) {
		
		for (int i = 0; i < args.length; i++)
		{
			FilePartitioner f = new FilePartitioner(args[i]);
			f.createPartition(5);
		}

	}
}
