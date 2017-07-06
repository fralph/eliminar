package online.escribiendo.rubricEditor.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RubricEditor implements EntryPoint {

	public final static VerticalPanel vp = new VerticalPanel();
	private int count = 0;
	private int defaultNumLevels = 3;
	private Criterion criterion = null;


	public static native void console(int text)
	/*-{
    console.log(text);
	}-*/;
	
	public void onModuleLoad() {
		final Button addCriteriaButton = new Button("Add Criteria");
		addCriteriaButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
				criterion = new Criterion(defaultNumLevels,needUpButton(),false);
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
				vp.add(criterion);
				console(vp.getWidgetIndex(criterion));
			}
		});
		vp.add(addCriteriaButton);
		RootPanel.get().add(vp);
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
			console(232323);
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
	
	public static int getTotalChilds(){
		return vp.getWidgetCount();
	}
	
	public static int getChildIndex(Criterion cr){
		return vp.getWidgetIndex(cr);
	}
	
	private boolean needUpButton(){
		return (vp.getWidgetCount() > 1);
	}
	
	private boolean needDownButton(){
		return (vp.getWidgetCount() > 1 );
	}

}
