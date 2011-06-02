package cn.ict.binos.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BinosFileOutputStream extends BinosOutputStream{
	
	public BinosFileOutputStream(File file) throws FileNotFoundException, IOException {	
		super(new BufferedOutputStream(new FileOutputStream(file)));
	} 
}
