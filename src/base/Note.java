package base;

import java.util.Date;

public class Note implements Comparable<Note>{

	private Date date;
	private String title;

	public Note(String title){
		this.title = title;
		date = new Date(System.currentTimeMillis());
	}

	public String getTitle(){
		return this.title;
	}

	public boolean eqauls(Note note){
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
}
