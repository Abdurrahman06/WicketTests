package org.abdurrahman.Deneme;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.abdurrahman.db.IDao;
import org.abdurrahman.model.HesapPlan;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class HesapPlanProces extends WebPage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private IDao dao;

	private HesapPlan current = new HesapPlan();

	public HesapPlanProces(final PageParameters parameters) {
		super(parameters);
		Form<HesapPlan> formhp = new Form<HesapPlan>("formhp", new CompoundPropertyModel<>(LoadableDetachableModel.of(() -> current))) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				if (current.getId() == null) {
					current.setId((long) (Math.random() * 10000000L));
					dao.save(current);
				} else {
					dao.update(current);
				}
				current = new HesapPlan();
			}
		};
		
		formhp.add(new TextField<>("hesapKod"));
		formhp.add(new TextField<>("hesapAdi"));
		add(formhp);
		List<IColumn<HesapPlan, String>> columns = new ArrayList<>();
		columns.add(new LambdaColumn<>(Model.of("ID"), HesapPlan::getId));
		columns.add(new LambdaColumn<>(Model.of("hesapKod"), HesapPlan::getHesapKod));
		columns.add(new LambdaColumn<>(Model.of("hesapAdi"), HesapPlan::getHesapAdi));
		columns.add(new AbstractColumn<HesapPlan, String>(Model.of("DELETE")) {
			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem(Item<ICellPopulator<HesapPlan>> cellItem, String componentId, IModel<HesapPlan> rowModel) {
				cellItem.add(new Link<Void>(componentId) {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						dao.delete(rowModel.getObject());
					}
				});
			}
		});
		columns.add(new AbstractColumn<HesapPlan, String>(Model.of("GÜNCELLE")) {
			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem(Item<ICellPopulator<HesapPlan>> cellItem, String componentId, IModel<HesapPlan> rowModel) {
				cellItem.add(new Link<Void>(componentId) {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						current = rowModel.getObject();
					}
				});
			}
		});
		SortableDataProvider<HesapPlan, String> provider = new SortableDataProvider<HesapPlan, String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Iterator<HesapPlan> iterator(long first, long count) {
				return dao.findAll(HesapPlan.class).iterator();
			}

			@Override
			public long size() {
				return dao.countAll(HesapPlan.class);
			}

			@Override
			public IModel<HesapPlan> model(HesapPlan object) {
				return Model.of(object);
			}
		};

		DataTable<HesapPlan, String> tbl = new DataTable<HesapPlan, String>("table", columns, provider, 50);
		tbl.addTopToolbar(new HeadersToolbar<String>(tbl, provider));
		tbl.addTopToolbar(new NavigationToolbar(tbl));
		tbl.addBottomToolbar(new NoRecordsToolbar(tbl));
		add(tbl);
	}
}