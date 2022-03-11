package base;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Folder implements Comparable<Folder>, java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<Note> notes;
	private String name;

	public Folder(String name){
		this.name = name;
		notes = new ArrayList<Note>();
	}

	public void addNote(Note note){
		notes.add(note);
	}

	public boolean removeNote(Note note){
		return notes.remove(note);
	}

	public boolean removeNote(String noteTitle){
		for(Note n: notes){
			if(n.getTitle().equals(noteTitle)){
				return notes.remove(n);
			}
		}
		return false;
	}
	public String getName(){
		return name;
	}

	public ArrayList<Note> getNotes(){
		return notes;
	}

	public boolean equals(Folder folder){
		return this.name.equals(folder.name);
	}

	public String toString(){
		int nText = 0;
		int nImage = 0;
		for(Note n : notes){
			if(n instanceof TextNote)
				++nText;
			if(n instanceof ImageNote)
				++nImage;
		}

		return name + ":" + nText + ":" + nImage;
	}

	@Override
	public int compareTo(Folder f){
		return this.name.compareTo(f.name);
	}

	public void sortNotes(){
		Collections.sort(notes);
	}

	public List<Note> searchNotes(String keywords){
		List<Note> result = (ArrayList<Note>) this.notes.clone();
		String[] keys = keywords.toLowerCase().split(" ");
		ArrayList<String> Orkey= new ArrayList<String>();
		ArrayList<Note> fNote = new ArrayList<Note>();
		int j;
		for(int i = 0; i < keys.length; ++i){
			Orkey.clear();
			fNote.clear();
			while((i+2) < keys.length && keys[i+1].equals("or")){
				Orkey.add(keys[i]);
				i = i + 2;
			}
			Orkey.add(keys[i]);
			for(Note e: result){
				for(j = 0; j < Orkey.size(); ++j){
					if(e.getTitle().toLowerCase().contains(Orkey.get(j)))
						break;
					if(e instanceof TextNote){
						TextNote t = (TextNote) e;
						if(t.getContent().toLowerCase().contains(Orkey.get(j))){
							break;
						}
					}
				}
				if(j == Orkey.size())
					fNote.add(e);
			}
			result.removeAll(fNote);
		}

		return result;
	}

	public boolean save(String file){
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(file);
			out = new ObjectOutputStream(fos);
			out.writeObject(this);
			out.close();
		} catch (Exception e) {
		    return false;
		}
		return true;
	}
}
