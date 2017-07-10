/**
 * 
 */
package online.escribiendo.rubricEditor.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
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
	private Label lbl = null;
	private Label lbles = null;
	private FocusPanel fs = null;
	private int id= 0;
	private String  definition = null;
	private int score = 0;

	public CriterionLevel(String id, String definition, String score) {
		
		ta = new TextArea();
		lbl = new Label("level");
		fs = new FocusPanel();
		lbl.setText(definition);
		removeLevelButton = new Button("Remove");
		fs.setStylePrimaryName("level");
		lbl.setStylePrimaryName("leveltext");
		ta.setStylePrimaryName("leveltextbox");
		
		fs.add(lbl);
		fs.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(clicked == false){
					fs.remove(lbl);
					ta.setText(lbl.getText());
					fs.add(ta);
					ta.selectAll();
					ta.setFocus(true);
					clicked = true;		
				}	
			}
		});

		ta.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				// TODO Auto-generated method stub
				if(clicked == true){
					fs.remove(ta);
					lbl.setText(ta.getText());
					fs.add(lbl);	
					clicked = false;
				}	
			}
		});
		
		removeLevelButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				removeFromParent();
			}
		});
		
		add(fs);
		add(removeLevelButton);
	}

	
}