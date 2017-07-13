/**
 * 
 */
package online.escribiendo.rubricEditor.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * @author francisco ralph
 *
 */


public class CriterionLevel extends VerticalPanel{


	public static native void console(String text)
	/*-{
    console.log(text);
	}-*/;

	public boolean clicked = false;
	private TextArea ta = null;
	private Button removeLevelButton = null;
	private Label levelDefinition = null;
	private FocusPanel fp = null;
	private int id= 0;
	private String  definition = null;
	private Label title = null;
	public int score = 0;

	public CriterionLevel(int id, String definition, int levelCount) {
		removeLevelButton = new Button("Remove");
		title = new Label("Nivel "+levelCount);
		TextEditor te = new TextEditor(definition,"text"); 
		removeLevelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Criterion.removeLevelFromCriterion(getCriterionLevel());
			}
		});
		
		add(title);
		add(te);
		add(removeLevelButton);
		
		
	}
	public CriterionLevel getCriterionLevel(){
		return this;
	}
	public void updateLevelScore(int score){
		this.score= score;
		title.setText("Nivel " + score);
		
	}
	public void addDescription(){
		
	}

	
}