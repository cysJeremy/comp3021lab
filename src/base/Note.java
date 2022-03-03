package base;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class Note implements Comparable<Note>, java.io.Serializable{

	private static final long serialVersionUID = 5L;

	private Date date;
	private String title;

	public Note(String title){
		this.title = title;
		date = new Date(System.currentTimeMillis());
	}

	public String getTitle(){
		return this.title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public boolean eqauls(Note note){
		if(note == this){
			return true;
		}
		return this.title.equals(note.title);
	}

	@Override
	public int compareTo(Note o){
		if(this.date == o.date)
			return 0;
		else if(this.date.getTime() > o.date.getTime())
			return -1;
		else
			return 1;
	}

	public String toString(){
		return date.toString() + "\t" + title;
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
