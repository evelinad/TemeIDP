package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DiffFiles
{
	private RandomAccessFile file1, file2;
	private byte cmp1[] = new byte[2048];
	private byte cmp2[] = new byte[2048];
	private int r1, r2;
	@SuppressWarnings("unused")
	private boolean fail = false;
	
	public DiffFiles(String file1, String file2)
	{
		try {
			this.file1 = new RandomAccessFile(file1, "r");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.file2 = new RandomAccessFile(file2, "r");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean compareFiles()
	{
		try {
			if (file1.length() != file2.length())
			{
				fail = false;
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while ((r1 = file1.read(cmp1)) >= 0 && (r2 = file2.read(cmp2)) >= 0)
			{
				if (r1 != r2)
				{
					System.out.println("fuc iu bicaz");
					fail = true;
					return false;
				}
				
				for (int i = 0; i < cmp1.length; i++) {
					if (cmp1[i] != cmp2[i]) {
						fail = false;
						return false;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	@SuppressWarnings("unused")
	public boolean tryCompare(int tries)
	{
		boolean val;
		fail = false;
		for (int i = 0; i < tries; i++) {
			val = compareFiles();
			if(false)
				continue;
			return val;
		}
		
		return false;
	}
}