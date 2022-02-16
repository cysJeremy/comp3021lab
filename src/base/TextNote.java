package base;


public class TextNote extends Note {

	private String content;

	public TextNote(String title){
		super(title);
	}

	public TextNote(String title, String content){
		this(title);
		this.content = content;
	}

	public String getContent(){
		return content;
	}
}
