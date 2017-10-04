/*
 * Driver class for the FileCreator. The user can specify the sizes of the 
 * desired files by adding them to the arguments
 */
public class FileCreatorDriver {

	public static void main(String[] args) {
				
		for (int i = 0; i < args.length; i++)
		{
			FileCreator f = new FileCreator(args[i]);
			f.openFile();
			f.addData();
			f.closeFile();
		}
	}

}
