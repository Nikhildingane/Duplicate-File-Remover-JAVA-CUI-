import java.io.*;
import java.util.*;
import java.io.FileInputStream;
import java.security.MessageDigest;


class DuplicateFileRemover
{
	public static void main(String arg[]) throws Exception
	{
		System.out.println("Enter the directory name");
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		
		//Get the directory from user
		String dir=br.readLine();
		
		//create directory object
		File fdir=new File(dir);
		
		//returns if directory is not exists
		if(!fdir.exists())
		{
			System.out.println("Invalid Directory");
			System.exit(0);
		}
		
		//method which returns array File objects 
		File filelist[]=fdir.listFiles();
		
		//Use MD5 algorithm
		MessageDigest digest = MessageDigest.getInstance("MD5");
		
		//LinkedList to hold the unique files checksum and thir name only
		LinkedList<String> lobj=new LinkedList<String>();
		
		for(File file:filelist)
		{
			FileInputStream fis = new FileInputStream(file);
     
			//Create byte array to read data in chunks
			byte[] byteArray = new byte[1024];
			int bytesCount = 0; 
			
			//Read file data and update in message digest
			while ((bytesCount = fis.read(byteArray)) != -1) {
				digest.update(byteArray, 0, bytesCount);
			};
			
			//close the stream; We don't need it now.
			fis.close();
			
			//Get the hash's bytes
			byte[] bytes = digest.digest();
			
			//This bytes[] has bytes in decimal format;
			//Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			//checks if generated checksum is already present in linkedlist or not if not then it is inserted in linkedlist
			if(lobj.contains(sb.toString()))
			{
				//perform the file delete operation checks if it is deleted or not
				if(!file.delete())
				{
					System.out.println("Unable to delete file : "+file.getPath());
				}
				else
				{
					System.out.println("File Successfully deleted : "+file.getPath());
				}
			}
			else
			{
				//Insertion in linkedlist
				lobj.add(sb.toString());
			}
			
		}
	}
}