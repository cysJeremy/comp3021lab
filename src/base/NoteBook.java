package base;

import java.util.ArrayList;
public class NoteBook {

	private ArrayList<Folder> folders;

	public NoteBook(){
		folders = new ArrayList<Folder>();
	}

	public boolean createTextNote(String folderName, String title){
		TextNote note = new TextNote(title);
		return insertNote(folderName, note);
	}

	public boolean createImageNote(String folderName, String title){
		ImageNote note = new ImageNote(title);
		return insertNote(folderName, note);
	}

	public ArrayList<Folder> getFolders(){
		return folders;
	}

	public boolean insertNote(String folderName, Note note){
		Folder noteFolder = null;
		for(Folder f: folders){
			if(f.getName() == folderName){
				noteFolder = f;
			}
		}
		if(noteFolder == null){
			noteFolder = new Folder(folderName);
			folders.add(noteFolder);
		}

		for(Note n: noteFolder.getNotes()){
			if(n.eqauls(note)){
				System.out.println("Create note " + note.getTitle() + " under folder " + folderName + " failed");
				return false;
			}
		}
		noteFolder.addNote(note);
		return true;

	}
}
