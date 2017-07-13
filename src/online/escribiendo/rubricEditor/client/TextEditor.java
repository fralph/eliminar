package online.escribiendo.rubricEditor.client;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;

public class TextEditor extends FocusPanel{
	
	public boolean clicked = false;
	private TextArea ta = null;
	private Label levelDefinition = null;
	
	public TextEditor(String text, String type){
		ta = new TextArea();
		levelDefinition = new Label("level");
		levelDefinition.setText(text);

		if(type == "text"){
		ta.setStylePrimaryName("leveltextbox");
		setStylePrimaryName("level");
		levelDefinition.setStylePrimaryName("leveltext");
		}
		if(type == "description"){
			//ta.setStylePrimaryName("leveltextbox");
			}
		add(levelDefinition);
		addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(clicked == false){
					remove(levelDefinition);
					ta.setText(levelDefinition.getText());
					add(ta);
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
					remove(ta);
					levelDefinition.setText(ta.getText().replaceAll("\n", "<br>"));
					add(levelDefinition);	
					clicked = false;
				}	
			}
		});
	}

}
