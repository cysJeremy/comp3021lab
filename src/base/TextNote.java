package base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;

import java.io.BufferedWriter;
import java.io.FileWriter;


public class TextNote extends Note {

	private static final long serialVersionUID = 3L;
	private String content;

	public TextNote(String title){
		super(title);
	}

	public TextNote(File f) {
		super(f.getName());
		this.content = getTextFromFile(f.getAbsolutePath());
	}

	public TextNote(String title, String content){
		this(title);
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	public void exportTextToFile(String pathFolder) {
        //TODO
		File file;
		if(pathFolder != "")
			file = new File( pathFolder + File.separator + this.getTitle().replace(' ', '_') + ".txt");
		else
			file = new File( this.getTitle().replace(' ', '_') + ".txt");
		// Todo
		BufferedWriter bw = null;
		FileWriter fw = null;

		try{
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(this.content);
			bw.close();
		}catch (Exception e) {
            e.printStackTrace();
	    }
	}

	private String getTextFromFile(String absolutePath) {
		String result = "";
		// TODO
		FileInputStream fis = null;
	    ObjectInputStream in = null;
	    BufferedReader br = null;
	    String line;
	    try{
	    	fis = new FileInputStream(absolutePath);
            br = new BufferedReader(new InputStreamReader(fis));
            while((line = br.readLine()) != null)
            	result += line;
            br.close();
	    }catch (Exception e) {
            e.printStackTrace();
	    }

		return result;
	}

}
