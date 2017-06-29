package online.escribiendo.rubricEditor.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class Criterion extends HorizontalPanel {

	private  Button addLevelButton = null;


	public Criterion(int defaultNumLevels){
		
		addLevelButton = new Button("Add Level");
		setBorderWidth(1);
		addLevelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				CriterionLevel level = new CriterionLevel();
				add(level);
			}
		});
		add(addLevelButton);
		for (int j = 0; j < defaultNumLevels; j++) {
			CriterionLevel level = new CriterionLevel();
			add(level);

		}

	}

}
