package online.escribiendo.rubricEditor.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RubricEditor implements EntryPoint {

	public final VerticalPanel vp = new VerticalPanel();
	private int count = 0;
	private int defaultNumLevels = 3;
	
	public static native void console(String text)
	/*-{
    console.log(text);
	}-*/;
	public void onModuleLoad() {
		final Button addCriteriaButton = new Button("Add Criteria");
		addCriteriaButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
				Criterion cr = new Criterion(defaultNumLevels);
				vp.add(cr);
			}
		});
		vp.add(addCriteriaButton);
		RootPanel.get().add(vp);
	}
	
}
