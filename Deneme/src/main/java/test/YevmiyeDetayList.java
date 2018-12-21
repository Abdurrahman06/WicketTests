package test;

import java.math.BigDecimal;
import java.util.List;

import org.abdurrahman.model.Yevmiye;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class YevmiyeDetayList extends Panel {
	private static final long serialVersionUID = 1L;

	public YevmiyeDetayList(String id, IModel<List<Yevmiye>> model) {
		super(id, model);
		Form<Yevmiye> form = new Form<Yevmiye>("form");
		WebMarkupContainer container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		container.add(new PropertyListView<Yevmiye>("list", model) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Yevmiye> item) {
				final TextField<String> hesapKod = new TextField<String>("hesapKod");
				final TextField<BigDecimal> borc = new TextField<BigDecimal>("borc");
				final TextField<BigDecimal> alacak = new TextField<BigDecimal>("alacak");
			}
		});
		form.add(container);
		add(form);
	}
}