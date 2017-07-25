package online.escribiendo.rubricEditor.client;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Criterion extends HorizontalPanel {
	private static Logger logger = Logger.getLogger("");

	private static  Button addLevelButton = null;
	private static Button removeCriterionButton = null;
	private static Button moveCriterionUpButton = null;
	private static  Button moveCriterionDownButton = null;
	private VerticalPanel actionButtonsPanel = null;
	private static Criterion lastCriterion = null;
	private int index = 0;
	private boolean haveDownButton = false;
	private int firstPanelPosition = 2;
	public int id = 0;
	private String definition = "Criterio";
	private int levelCount = 1;

	public static native void console(String text)
	/*-{
    console.log(text);
	}-*/;
	public Criterion(String criterionId, String criterionDefinition, List<Map<String, String>> levels, int defaultNumLevels, boolean upButton){

		
		actionButtonsPanel = new VerticalPanel();
		addLevelButton = new Button("Add Level");
		removeCriterionButton = new Button("Remove criterion");
		moveCriterionDownButton = new Button("Move down");
		moveCriterionUpButton = new Button("Move Up");
		setBorderWidth(1);
		addLevelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				AjaxRequest.moodleUrl =RubricEditor.moodleurl;
				String params="action=createLevel&criterionid=" + id;
				AjaxRequest.ajaxRequest(params, new AsyncCallback<AjaxData>() {
					@Override
					public void onFailure(Throwable caught) {
						console(caught.toString());
						logger.warning("Failure on heartbeat");			
					}
					
					@Override
					public void onSuccess(AjaxData result) {
						Map<String, String> value = AjaxRequest.getValueFromResult(result);
						
						CriterionLevel level = new CriterionLevel(Integer.parseInt(value.get("id")),value.get("definition"),levelCount);
						levelCount++;
						insert(level, firstPanelPosition);
					}			
				});
				
				
				
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
		actionButtonsPanel.add(addLevelButton);
		actionButtonsPanel.add(removeCriterionButton);
		if(upButton == false){
			moveCriterionUpButton.setStylePrimaryName("button-hide");
		}

		moveCriterionDownButton.setStylePrimaryName("button-hide");
		actionButtonsPanel.add(moveCriterionUpButton);
		actionButtonsPanel.add(moveCriterionDownButton);
		add(actionButtonsPanel);
		if(criterionDefinition != null && criterionId != null){
			this.definition=criterionDefinition;
			this.id=Integer.parseInt(criterionId);
		}
			VerticalPanel vpc = new VerticalPanel();
			TextEditor te = new TextEditor(this.definition,"text");
			te.isCriterion = true;
			TextEditor ted = new TextEditor("Descripción (opcional)","description");
			vpc.add(te);
			vpc.add(ted);
			insert(vpc, 1);
	
		for(Map<String, String> levelData : levels) {
			String levelId= levelData.get("id");
			String levelDefinition= levelData.get("definition");
			String levelScore= levelData.get("score");

			CriterionLevel level = new CriterionLevel(Integer.parseInt(levelId),levelDefinition,Integer.parseInt(levelScore));	
			add(level);
			levelCount++;
		}
		

		lastCriterion=Criterion.this;
	}
	

	public Criterion getCriterion(){
		return this;
	}
	public static void addDownButton(Criterion firstCriterion) {
		// TODO Auto-generated method stub
		Button btn = (Button) firstCriterion.actionButtonsPanel.getWidget(3);
		btn.setStylePrimaryName("gwt-Button");
		
	}
	public static void addUpButton(Criterion firstCriterion) {
		// TODO Auto-generated method stub
		Button btn = (Button) firstCriterion.actionButtonsPanel.getWidget(2);
		btn.setStylePrimaryName("gwt-Button");
		
	}
	public static void removeUpButton(Criterion firstCriterion) {
		// TODO Auto-generated method stub
		Button btn = (Button) firstCriterion.actionButtonsPanel.getWidget(2);
		btn.setStylePrimaryName("button-hide");
	}
	public static void removeDownButton(Criterion firstCriterion) {
		// TODO Auto-generated method stub
		Button btn = (Button) firstCriterion.actionButtonsPanel.getWidget(3);
		btn.setStylePrimaryName("button-hide");
	}

	public static void removeLevelFromCriterion(final CriterionLevel criterionLevel){
	
		
		AjaxRequest.moodleUrl =RubricEditor.moodleurl;
		String params="action=removeLevel&levelid=" + criterionLevel.id;
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
				Criterion cr = (Criterion) criterionLevel.getParent();
				int removedIndex = cr.getWidgetIndex(criterionLevel);
				criterionLevel.removeFromParent();
				cr.levelCount--;
				
				for (int i = removedIndex -1; i > 1; i--) {
					CriterionLevel cl =(CriterionLevel) cr.getWidget(i);
					int newIndex= cl.score - 1;
					cl.updateLevelScore(newIndex);
				}
				
			}			
		});
		
	}
	public static void updateCriterionText(String text, TextEditor textEditor) {
		// TODO Auto-generated method stub
				Criterion cr = (Criterion) textEditor.getParent().getParent();
				AjaxRequest.moodleUrl =RubricEditor.moodleurl;
				String params="action=updateCriterionText&criterionid=" + cr.id + "&criteriontext=" + text;
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
