package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.abdurrahman.db.IDao;
import org.abdurrahman.model.Yevmiye;
import org.abdurrahman.model.YevmiyeMaster;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import test.YevmiyeDetayList;

public class YevmiyeGiris extends WebPage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private IDao dao;

	private Yevmiye current = new Yevmiye();

	public YevmiyeGiris(final PageParameters parameters) {
		super(parameters);
		Form<Yevmiye> formyev = new Form<Yevmiye>("formyev", new CompoundPropertyModel<>(LoadableDetachableModel.of(() -> current))) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				if (current.getId() == null) {
					YevmiyeMaster yevmiyeMaster = new YevmiyeMaster();
					yevmiyeMaster = current.getYevmiyeMaster();
					if (yevmiyeMaster.getslipid() == null) {
						yevmiyeMaster.setyevno((int) (Math.random() * 10000000));
						dao.save(yevmiyeMaster);
						current.setYevmiyeMaster(yevmiyeMaster);
					}
					dao.save(current);
				} else {
					dao.update(current);
				}
				current = new Yevmiye();
			}
		};
		formyev.add(new TextField<>("yevmiyeMaster.ilgilininAdi"));
		formyev.add(new TextField<>("hesapKod"));
		formyev.add(new TextField<>("borc"));
		formyev.add(new TextField<>("alacak"));
		add(formyev);
		IModel<YevmiyeMaster> model = new CompoundPropertyModel<YevmiyeMaster>(new YevmiyeMaster());
		formyev.add(new YevmiyeDetayList("details", new CompoundPropertyModel<List<Yevmiye>>(new PropertyModel<List<Yevmiye>>(model, "yevmiyeList"))).setOutputMarkupId(true));

		List<IColumn<Yevmiye, String>> columns = new ArrayList<>();
		columns.add(new LambdaColumn<>(Model.of("ID"), Yevmiye::getId));
		columns.add(new LambdaColumn<>(Model.of("hesapKod"), Yevmiye::getHesapKod));
		columns.add(new LambdaColumn<>(Model.of("borc"), Yevmiye::getBorc));
		columns.add(new LambdaColumn<>(Model.of("alacak"), Yevmiye::getAlacak));
		
		columns.add(new AbstractColumn<Yevmiye, String>(Model.of("YevmiyeSil")) {

			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem(Item<ICellPopulator<Yevmiye>> cellItem, String componentId, IModel<Yevmiye> rowModel) {
				cellItem.add(new Link<Void>(componentId) {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						dao.delete(rowModel.getObject());
					}
				});
			}

		});
		columns.add(new AbstractColumn<Yevmiye, String>(Model.of("YevmiyeDuzelt")) {
			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem(Item<ICellPopulator<Yevmiye>> cellItem, String componentId, IModel<Yevmiye> rowModel) {
				cellItem.add(new Link<Void>(componentId) {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						current = rowModel.getObject();
					}
				});
			}
		});
		SortableDataProvider<Yevmiye, String> provider = new SortableDataProvider<Yevmiye, String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Iterator<Yevmiye> iterator(long first, long count) {
				return dao.findAll(Yevmiye.class).iterator();
			}

			@Override
			public long size() {
				return dao.countAll(Yevmiye.class);
			}

			@Override
			public IModel<Yevmiye> model(Yevmiye object) {
				return Model.of(object);
			}
		};

		DataTable<Yevmiye, String> tbl = new AjaxFallbackDefaultDataTable<Yevmiye, String>("table", columns, provider, 50);
		// DataTable<Yevmiye, String> tbl = new DataTable<Yevmiye, String>("table",
		// columns, provider, 50);
		// tbl.addTopToolbar(new HeadersToolbar<String>(tbl, provider));
		// tbl.addTopToolbar(new NavigationToolbar(tbl));
		// tbl.addBottomToolbar(new NoRecordsToolbar(tbl));
		add(tbl);
		add(new FeedbackPanel("feed"));
	}
}
