package online.escribiendo.rubricEditor.client;


import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RubricEditor implements EntryPoint {

	private static Logger logger = Logger.getLogger("");
	
	public final static VerticalPanel vp = new VerticalPanel();
	private int count = 0;
	private int defaultNumLevels = 3;
	private Criterion criterion = null;
	private String params = null;
	private String criterionId = null;
	private String criterionDefinition = null;

	
	/** Ajax url **/
	private String moodleurl = "http://localhost:80/mod/emarking/activities/ajax/data.php";


	public static native void console(String text)
	/*-{
    console.log(text);
	}-*/;
	
	public void onModuleLoad() {
		final Button addCriteriaButton = new Button("Add Criteria");
		AjaxRequest.moodleUrl = moodleurl;
		params="action=getRubric&rubricid=1";
		AjaxRequest.ajaxRequest(params, new AsyncCallback<AjaxData>() {
			@Override
			public void onFailure(Throwable caught) {
				console(caught.toString());
				logger.warning("Failure on heartbeat");			
			}
			
			@Override
			public void onSuccess(AjaxData result) {
				vp.add(addCriteriaButton);
				List<Map<String, String>> values = AjaxRequest.getValuesFromResult(result);
				for(Map<String, String> criteria : values) {
					List<Map<String, String>> levels = AjaxRequest.getValuesFromResultString(criteria.get("levels"));
					criterionId=criteria.get("id");
					criterionDefinition=criteria.get("definition");
					criterion = new Criterion(criterionId,criterionDefinition,levels,0,needUpButton());
					checkVpIndex();
					vp.add(criterion);
				
				}		
			}			
		});
		
		addCriteriaButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
				criterion = new Criterion(null,null,null ,defaultNumLevels,needUpButton());
				checkVpIndex();
				vp.add(criterion);
				
			}
		});
		
		RootPanel.get().add(vp);
	}
	private void checkVpIndex(){

		if(getTotalChilds() < 3){
			switch (getTotalChilds()) {
			case 2:
				Criterion firstCriterion = (Criterion) vp.getWidget(1);
				Criterion.addDownButton(firstCriterion);

				break;
			case 3:
				Criterion secondCriterion = (Criterion) vp.getWidget(2);
				 Criterion.addDownButton(secondCriterion);
				break;
				}
			}
			else{

				int beforeIndex =getTotalChilds() - 1;
				Criterion beforeCriterion = (Criterion) vp.getWidget(beforeIndex);
				Criterion.addDownButton(beforeCriterion);
			}
		
	}
	public static void moveCriterionDown(Criterion cr) {
		// TODO Auto-generated method stub
		int criterionIndex=vp.getWidgetIndex(cr);
		int totalChilds=getTotalChilds() - 1 ;
		int newCriterionIndex = criterionIndex + 1;
		
		vp.remove(cr);
		vp.insert(cr,newCriterionIndex);
		
		if(criterionIndex == 1){
			Criterion beforeCriterion = (Criterion) vp.getWidget(criterionIndex);
			Criterion.addUpButton(cr);
			Criterion.removeUpButton(beforeCriterion);
		}
		if(newCriterionIndex == totalChilds){
			Criterion.removeDownButton(cr);
			Criterion beforeCriterion = (Criterion) vp.getWidget(criterionIndex);
			Criterion.addDownButton(beforeCriterion);
		}
		
	}
	
	public static void moveCriterionUp(Criterion cr) {
		// TODO Auto-generated method stub
		int criterionIndex=vp.getWidgetIndex(cr);
		int totalChilds=getTotalChilds() - 1;
		int newCriterionIndex = criterionIndex - 1;
		
		vp.remove(cr);
		vp.insert(cr,newCriterionIndex);
		
		if(newCriterionIndex == 1){
			Criterion.removeUpButton(cr);
			Criterion afterCriterion = (Criterion) vp.getWidget(criterionIndex);
			Criterion.addUpButton(afterCriterion);
		}
		if(criterionIndex == totalChilds){
			Criterion.addDownButton(cr);
			Criterion afterCriterion = (Criterion) vp.getWidget(criterionIndex);
			Criterion.removeDownButton(afterCriterion);
		}
		
	}
	public static void removeCriterion(Criterion cr) {
		cr.removeFromParent();
		
		Criterion firstCriterion = (Criterion) vp.getWidget(1);
		
		int lastVPIndex= getTotalChilds() - 1;
		Criterion lastCriterion = (Criterion) vp.getWidget(lastVPIndex);
		
		Criterion.removeUpButton(firstCriterion);
		Criterion.removeDownButton(lastCriterion);
	}
	
	public static int getTotalChilds(){
		return vp.getWidgetCount();
	}
	
	public static int getChildIndex(Criterion cr){
		return vp.getWidgetIndex(cr);
	}
	
	private boolean needUpButton(){
		return (vp.getWidgetCount() > 1);
	}
	

}
