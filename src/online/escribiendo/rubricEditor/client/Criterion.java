package online.escribiendo.rubricEditor.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Criterion extends HorizontalPanel {

	private static  Button addLevelButton = null;
	private static Button removeCriterionButton = null;
	private static Button moveCriterionUpButton = null;
	private static  Button moveCriterionDownButton = null;
	private VerticalPanel vp = null;
	private int index = 0;
	private boolean haveDownButton = false;
	private int firstPanelPosition = 1;
	private int id = 0;
	private String definition = null;

	public static native void console(String text)
	/*-{
    console.log(text);
	}-*/;
	public Criterion(String criterionId, String criterionDefinition, List<Map<String, String>> levels, int defaultNumLevels, boolean upButton, boolean downButton){

		if(criterionDefinition != null && criterionId != null){
				this.definition=criterionDefinition;
				this.id=Integer.parseInt(criterionId);
		}
		vp = new VerticalPanel();
		addLevelButton = new Button("Add Level");
		removeCriterionButton = new Button("Remove criterion");
		moveCriterionDownButton = new Button("Move down");
		moveCriterionUpButton = new Button("Move Up");
		setBorderWidth(1);
		addLevelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				CriterionLevel level = new CriterionLevel(null,null,null);
				insert(level, firstPanelPosition);
				
			}
		});
		removeCriterionButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				RubricEditor.removeCriterion(getCriterion());
			}
		});
		moveCriterionDownButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				RubricEditor.moveCriterionDown(getCriterion());
			}
		});
		moveCriterionUpButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				RubricEditor.moveCriterionUp(getCriterion());
			}
		});
		if(criterionDefinition != null){
			//vp.add(new Label(criterionDefinition));
		}
		vp.add(addLevelButton);
		vp.add(removeCriterionButton);
		if(upButton == false){
			moveCriterionUpButton.setStylePrimaryName("button-hide");
		}
		if(downButton == false){
			moveCriterionDownButton.setStylePrimaryName("button-hide");
		}
		vp.add(moveCriterionUpButton);
		vp.add(moveCriterionDownButton);
		add(vp);
		if (levels != null){
		for(Map<String, String> levelData : levels) {
			String levelId= levelData.get("id");
			String levelDefinition= levelData.get("definition");
			String levelScore= levelData.get("score");

			CriterionLevel level = new CriterionLevel(levelId,levelDefinition,levelScore);	
			add(level);
		}
		}else{
		for (int j = 0; j < defaultNumLevels; j++) {
			CriterionLevel level = new CriterionLevel(null,null,null);
			add(level);
		}
		}
	}
	
	public Criterion getCriterion(){
		return this;
	}
	public static void addDownButton(Criterion firstCriterion) {
		// TODO Auto-generated method stub
		Button btn = (Button) firstCriterion.vp.getWidget(3);
		btn.setStylePrimaryName("gwt-Button");
		
	}
	public static void addUpButton(Criterion firstCriterion) {
		// TODO Auto-generated method stub
		Button btn = (Button) firstCriterion.vp.getWidget(2);
		btn.setStylePrimaryName("gwt-Button");
		
	}
	public static void removeUpButton(Criterion firstCriterion) {
		// TODO Auto-generated method stub
		Button btn = (Button) firstCriterion.vp.getWidget(2);
		btn.setStylePrimaryName("button-hide");
	}
	public static void removeDownButton(Criterion firstCriterion) {
		// TODO Auto-generated method stub
		Button btn = (Button) firstCriterion.vp.getWidget(3);
		btn.setStylePrimaryName("button-hide");
	}

}
