/**
 * 
 */
package online.escribiendo.rubricEditor.client;

import java.util.Map;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
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


	private static Logger logger = Logger.getLogger("");
	
	public static native void console(String text)
	/*-{
    console.log(text);
	}-*/;

	public boolean clicked = false;
	private TextArea ta = null;
	private Button removeLevelButton = null;
	private Label levelDefinition = null;
	private FocusPanel fp = null;
	public int id;
	private String  definition = null;
	private Label title = null;
	public int score = 0;
	

	public CriterionLevel(int id, String definition, int levelCount) {
		this.score=levelCount;
		this.id = id;
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

	public static  void updateLevelText(String text, TextEditor textEditor) {
		// TODO Auto-generated method stub
		CriterionLevel cl = (CriterionLevel) textEditor.getParent();
		AjaxRequest.moodleUrl =RubricEditor.moodleurl;
		String params="action=updateLevelText&levelid=" + cl.id + "&leveltext=" + text;
		AjaxRequest.ajaxRequest(params, new AsyncCallback<AjaxData>() {
			@Override
			public void onFailure(Throwable caught) {
				console(caught.toString());
				logger.warning("Failure on heartbeat");			
			}
			
			@Override
			public void onSuccess(AjaxData result) {
				Map<String, String> value = AjaxRequest.getValueFromResult(result);
				console(value.toString());
			}			
		});
	}

	
}