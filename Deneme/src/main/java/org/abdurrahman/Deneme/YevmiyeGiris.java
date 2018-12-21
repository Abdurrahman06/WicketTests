package org.abdurrahman.Deneme;

import org.abdurrahman.db.IDao;
import org.abdurrahman.model.Yevmiye;
import org.abdurrahman.model.YevmiyeMaster;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class YevmiyeGiris extends WebPage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private IDao dao;

	private YevmiyeMaster current = new YevmiyeMaster();

	public YevmiyeGiris(final PageParameters parameters) {
		super(parameters);
		Form<YevmiyeMaster> anaform = new Form<YevmiyeMaster>("anaform", new CompoundPropertyModel<>(LoadableDetachableModel.of(() -> current))) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				dao.save(current);
				current = new YevmiyeMaster();
				for (Yevmiye y : current.getYevmiyeList()) {
					dao.save(y);
				}
			}
		};

		anaform.add(new TextField<>("ilgilininAdi"));
		anaform.add(new TextField<>("hazirlayaninAdi"));
		anaform.add(new TextArea<>("aciklama"));
		WebMarkupContainer container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		anaform.add(new AjaxLink<Void>("yeniSatir") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				Yevmiye yevmiye = new Yevmiye();
				current.getYevmiyeList().add(yevmiye);
				yevmiye.setYevmiyeMaster(current);
				target.add(container);
			}

		});
		container.add(new PropertyListView<Yevmiye>("yevmiyeList") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Yevmiye> item) {
				item.add(new TextField<>("hesapKod"));
				item.add(new TextField<>("borc"));
				item.add(new TextField<>("alacak"));
			}
		});
		anaform.add(container);
		add(anaform);
		add(new FeedbackPanel("feed"));
	}
}
