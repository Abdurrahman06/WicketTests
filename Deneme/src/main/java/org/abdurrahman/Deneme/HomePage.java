package org.abdurrahman.Deneme;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.abdurrahman.db.Filter;
import org.abdurrahman.db.IDao;
import org.abdurrahman.model.Mizan;
import org.abdurrahman.model.Person;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
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
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private IDao dao;
	private Person current = new Person();
	private Long limit = 25L;

	public HomePage(final PageParameters parameters) {
		super(parameters);
		add(new TextField<Long>("limit", PropertyModel.of(this, "limit")).add(new AjaxFormComponentUpdatingBehavior("change") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(HomePage.this.get("table"));
			}
		}));
			Form<Person> form = new Form<Person>("form", new CompoundPropertyModel<>(LoadableDetachableModel.of(() -> current))) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {

				if (current.getId() == null) {
					current.setId((long) (Math.random() * 10000000L));
					dao.save(current);
				} else {
					dao.save(current);
				}
				current = new Person();
			}
		};
		form.add(new TextField<>("name"));
		form.add(new TextField<>("surname"));
		form.add(new TextField<>("yas"));
		add(form);
		List<IColumn<Person, String>> columns = new ArrayList<>();
		columns.add(new LambdaColumn<>(Model.of("ID"), Person::getId));
		columns.add(new LambdaColumn<>(Model.of("NAME"), Person::getName));
		columns.add(new LambdaColumn<>(Model.of("SURNAME"), Person::getSurname));
		columns.add(new LambdaColumn<>(Model.of("YAS"), Person::getYas));
		// alttaki satırlar silme komutu için.
		columns.add(new AbstractColumn<Person, String>(Model.of("SİL")) {
			private static final long serialVersionUID = 1L;
			@Override
			public void populateItem(Item<ICellPopulator<Person>> cellItem, String componentId, IModel<Person> rowModel) {
				cellItem.add(new Link<Void>(componentId) {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						dao.delete(rowModel.getObject());
					}
				});
			}
		});
		// alttaki satırlar güncelleme komutu için.
		columns.add(new AbstractColumn<Person, String>(Model.of("GÜNCELLE")) {
			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem(Item<ICellPopulator<Person>> cellItem, String componentId, IModel<Person> rowModel) {
				cellItem.add(new Link<Void>(componentId) {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						current = rowModel.getObject();
					}
				});
			}
		});
		SortableDataProvider<Person, String> provider = new SortableDataProvider<Person, String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Iterator<Person> iterator(long first, long count) {
				return dao.find(Person.class, getFilter()).iterator();
			}

			@Override
			public long size() {
				return dao.count(Person.class, getFilter());
			}

			@Override
			public IModel<Person> model(Person object) {
				return Model.of(object);
			}

			public Filter getFilter() {
				return Filter.greaterOrEqual("yas", limit);
			}
		};

		DataTable<Person, String> tbl = new AjaxFallbackDefaultDataTable<Person, String>("table", columns, provider, 50);
//		DataTable<Person, String> tbl = new DataTable<Person, String>("table", columns, provider, 50);
//		tbl.addTopToolbar(new HeadersToolbar<String>(tbl, provider));
//		tbl.addTopToolbar(new NavigationToolbar(tbl));
//		tbl.addBottomToolbar(new NoRecordsToolbar(tbl));
		add(tbl);
//		List<Object[]> result = (List<Object[]>) dao.runSql("SELECT hesapKod, sum(borc), sum(alacak)\r\n" + "FROM Yevmiye\r\n" + "GROUP BY hesapKod");
//		for (Object[] os : result) {
//			for (Object o : os) {
//				System.out.print(o);
//			}
//			System.out.println();
//		}
		List<Mizan> miz = dao.findAll(Mizan.class);
		for (Mizan m : miz) {
			System.out.println(m.getHesapKod() + "\t" + m.getBorcTop() + "\t" + m.getAlacakTop());
		}
	}

}