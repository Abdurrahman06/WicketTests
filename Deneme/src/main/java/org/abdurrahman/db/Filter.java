package org.abdurrahman.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

/**
 * Basic wrapper class for Hibernate {@link Restrictions} which is used for
 * creating {@link Criterion}. Filter class is specified in {@link IDao}'s find
 * methods. For example, <code>Filter.equal("age",27)</code> is equal to SELECT
 * ... FROM ... WHERE <b>age=27</b>. You can also use nested filters like:<br>
 * <code>Filter.and(Filter.greaterThan("age",30),Filter.lessThan("age",40))</code>
 * <p>
 * Or even check existence of child entities like:<br>
 * <tt>Filter.isNotEmpty("parent.children")</tt>
 * 
 * @author Yasir
 * @see org.hibernate.criterion.Criterion
 * @see org.hibernate.criterion.Restrictions
 * @see org.hibernate.Criteria
 */
public class Filter implements Serializable {
	private static final long serialVersionUID = 1L;
	private Criterion criterion;

	protected Filter(Criterion criterion) {
		setCriterion(criterion);
	}

	/**
	 * Field == Value
	 * <p>
	 * if value is null filter is ignored
	 * 
	 * @see Restrictions#eq(String, Object)
	 */
	public static Filter equal(String property, Object value) {
		if (value == null)
			return null;
		return new Filter(Restrictions.eq(property, value));
	}

	/**
	 * Field == Value
	 * 
	 * @see Restrictions#eqOrIsNull(String, Object)
	 */
	public static Filter equalOrIsNull(String property, Object value) {
		return new Filter(Restrictions.eqOrIsNull(property, value));
	}

	/**
	 * Field == Another Field
	 * 
	 * @see Restrictions#eqProperty(String, String)
	 */
	public static Filter equalProperty(String property, String otherProperty) {
		return new Filter(Restrictions.eqProperty(property, otherProperty));
	}

	/**
	 * Field != Another Field
	 * 
	 * @see Restrictions#eqProperty(String, String)
	 */
	public static Filter notEqualProperty(String property, String otherProperty) {
		return new Filter(Restrictions.neProperty(property, otherProperty));
	}

	/**
	 * Field < Value
	 * 
	 * @see Restrictions#lt(String, Object)
	 */
	public static Filter lessThan(String property, Object value) {
		if (value == null)
			return null;
		return new Filter(Restrictions.lt(property, value));
	}

	/**
	 * Field < Another Field
	 * 
	 * @see Restrictions#ltProperty(String, String)
	 */
	public static Filter lessThanProperty(String property, String otherProperty) {
		return new Filter(Restrictions.ltProperty(property, otherProperty));
	}

	/**
	 * Field > Value
	 * 
	 * @see Restrictions#gt(String, Object)
	 */
	public static Filter greaterThan(String property, Object value) {
		if (value == null)
			return null;
		return new Filter(Restrictions.gt(property, value));
	}

	/**
	 * Field > Another Field
	 * 
	 * @see Restrictions#gtProperty(String, String)
	 */
	public static Filter greaterThanProperty(String property, String otherProperty) {
		return new Filter(Restrictions.gtProperty(property, otherProperty));
	}

	/**
	 * Field <= Value
	 * 
	 * @see Restrictions#le(String, Object)
	 */
	public static Filter lessOrEqual(String property, Object value) {
		if (value == null)
			return null;
		return new Filter(Restrictions.le(property, value));
	}

	/**
	 * Field <= Another Field
	 * 
	 * @see Restrictions#leProperty(String, String)
	 */
	public static Filter lessOrEqualProperty(String property, String otherProperty) {
		return new Filter(Restrictions.leProperty(property, otherProperty));
	}

	/**
	 * Field >= Value
	 * 
	 * @see Restrictions#ge(String, Object)
	 */
	public static Filter greaterOrEqual(String property, Object value) {
		if (value == null)
			return null;
		return new Filter(Restrictions.ge(property, value));
	}

	/**
	 * Field >= Another Field
	 * 
	 * @see Restrictions#geProperty(String, String)
	 */
	public static Filter greaterOrEqualProperty(String property, String otherProperty) {
		return new Filter(Restrictions.geProperty(property, otherProperty));
	}

	/**
	 * @see Restrictions#in(String, Collection)
	 */
	public static Filter in(String property, Collection<?> value) {
		if (value.size() == 0)
			return null;
		return new Filter(Restrictions.in(property, value));
	}

	/**
	 * @see Restrictions#in(String, Object[])
	 */
	public static Filter in(String property, Object... value) {
		if (value == null)
			return null;
		return new Filter(Restrictions.in(property, value));
	}

	/**
	 * @see Restrictions#not(Criterion)
	 * @see Restrictions#in(String, Collection)
	 */
	public static Filter notIn(String property, Collection<?> value) {
		return new Filter(Restrictions.not(Restrictions.in(property, value)));
	}

	/**
	 * @see Restrictions#not(Criterion)
	 * @see Restrictions#in(String, Object[])
	 */
	public static Filter notIn(String property, Object... value) {
		return new Filter(Restrictions.not(Restrictions.in(property, value)));
	}

	/**
	 * @see Restrictions#like(String, Object)
	 */
	public static Filter like(String property, String value) {
		if (value == null || value.contains("null"))
			return null;
		return new Filter(Restrictions.like(property, value));
	}

	/**
	 * @see Restrictions#ilike(String, Object)
	 */
	public static Filter ilike(String property, String value) {
		if (value == null || value.contains("null"))
			return null;
		return new Filter(Restrictions.ilike(property, value));
	}

	/**
	 * @see Restrictions#ne(String, Object)
	 */
	public static Filter notEqual(String property, Object value) {
		if (value == null)
			return null;
		return new Filter(Restrictions.ne(property, value));
	}

	/**
	 * @see Restrictions#isNull(String)
	 */
	public static Filter isNull(String property) {
		return new Filter(Restrictions.isNull(property));
	}

	/**
	 * @see Restrictions#isNotNull(String)
	 */
	public static Filter isNotNull(String property) {
		return new Filter(Restrictions.isNotNull(property));
	}

	/**
	 * @see Restrictions#isEmpty(String)
	 */
	public static Filter isEmpty(String property) {
		return new Filter(Restrictions.isEmpty(property));
	}

	/**
	 * @see Restrictions#isNotEmpty(String)
	 */
	public static Filter isNotEmpty(String property) {
		return new Filter(Restrictions.isNotEmpty(property));
	}

	/**
	 * @see Restrictions#conjunction()
	 */
	public static Filter and(Filter... filters) {
		Conjunction c = Restrictions.conjunction();
		for (Filter f : filters) {
			if (f != null) {
				c.add(f.getCriterion());
			}
		}
		return new Filter(c);
	}

	/**
	 * @see Restrictions#conjunction()
	 */
	public static Filter and(Collection<Filter> filters) {
		Conjunction c = Restrictions.conjunction();
		for (Filter f : filters) {
			if (f != null) {
				c.add(f.getCriterion());
			}
		}
		return new Filter(c);
	}

	public static Filter and(Map<String, Filter> filters) {
		Conjunction c = Restrictions.conjunction();
		for (Entry<String, Filter> e : filters.entrySet()) {
			if (e != null) {
				c.add(e.getValue().getCriterion());
			}
		}
		return new Filter(c);
	}

	/**
	 * @see Restrictions#disjunction()
	 */
	public static Filter or(Filter... filters) {
		Disjunction d = Restrictions.disjunction();
		for (Filter f : filters) {
			if (f != null) {
				d.add(f.getCriterion());
			}
		}
		return new Filter(d);
	}

	public static Filter or(Collection<Filter> filters) {
		Disjunction d = Restrictions.disjunction();
		for (Filter f : filters) {
			if (f != null) {
				d.add(f.getCriterion());
			}
		}
		return new Filter(d);
	}

	/**
	 * @see Restrictions#not(Criterion)
	 */
	public static Filter not(Filter filter) {
		return new Filter(Restrictions.not(filter.getCriterion()));
	}

	/**
	 * <i><b>Example:</b></i><br>
	 * <tt>{@link String} alias = "detail";<br>
	 * {@link Class}&lt;?&gt; clazz = AccSlipDetail.class;<br>
	 * {@link Map}&lt;String,String&gt; aliases = new {@link HashMap}&lt;String, String&gt;();<br>
	 * aliases.put("detail.accPlan", "plan");<br>
	 * {@link Filter} sub = {@link Filter#and}({@link Filter#equalProperty}("this.id", "detail.accSlip.id"),{@link Filter#equal}("plan.accountCode","120"));<br>
	 * {@link Filter#exists}(clazz,alias,sub,aliases);
	 * </tt>
	 * 
	 * @param type    Type of the entity to check existence.
	 * @param alias   Alias for the entity
	 * @param filter  <b>WHERE</b> part of the sub-query
	 * @param aliases this is the tricky part... if you are to make joins inside
	 *                sub-query you have to give aliases for each of the extra
	 *                entities.
	 * @return this (for method chaining)
	 * @see DetachedCriteria
	 * @see Criteria#createAlias(String, String)
	 */
	public static Filter exists(Class<?> type, String alias, Filter filter, Map<String, String> aliases) {
		DetachedCriteria dc = DetachedCriteria.forClass(type, alias);
		dc.add(filter.getCriterion());
		dc.setProjection(Projections.id());
		if (aliases != null) {
			for (Entry<String, String> e : aliases.entrySet()) {
				dc.createAlias(e.getKey(), e.getValue());
			}
		}
		return new Filter(Subqueries.exists(dc));
	}

	/**
	 * <i><b>Example:</b></i><br>
	 * <tt>{@link String} alias = "detail";<br>
	 * {@link Class}<?> clazz = AccSlipDetail.class;<br>
	 * {@link Map}<String,String> aliases = new {@link HashMap}<String, String>();<br>
	 * aliases.put("detail.accPlan", "plan");<br>
	 * {@link Filter} sub = {@link Filter#and}({@link Filter#equalProperty}("this.id", "detail.accSlip.id"),{@link Filter#equal}("plan.accountCode","120"));<br>
	 * {@link Filter#notExists}(clazz,alias,sub,aliases);
	 * </tt>
	 * 
	 * @param type    Type of the entity to check existence. - one-many olan nesne
	 * @param alias   Alias for the entity - Bu asıl sorgudaki Entity içindeki
	 *                one-many olan set in kısa adıdır. INCACCRUAL için
	 *                incCollections
	 * @param filter  <b>WHERE</b> part of the sub-query - ilgili kriter
	 * @param aliases this is the tricky part... if you are to make joins inside
	 *                sub-query you have to give aliases for each of the extra
	 *                entities. - gerek yok subquery de join yoksa..
	 * @return this (for method chaining)
	 * @see DetachedCriteria
	 * @see Criteria#createAlias(String, String)
	 */
	public static Filter notExists(Class<?> type, String alias, Filter filter, Map<String, String> aliases) {
		DetachedCriteria dc = DetachedCriteria.forClass(type, alias);
		dc.add(filter.getCriterion());
		dc.setProjection(Projections.id());
		if (aliases != null) {
			for (Entry<String, String> e : aliases.entrySet()) {
				dc.createAlias(e.getKey(), e.getValue());
			}
		}
		return new Filter(Subqueries.notExists(dc));
	}

	/**
	 * Apply a "between" constraint to the named property
	 * 
	 * @param property Name
	 * @param low      Value
	 * @param high     Value
	 * @return new {@link Filter}
	 * @see Restrictions#between(String, Object, Object)
	 */
	public static Filter between(String propertyName, Object lo, Object hi) {
		if (lo == null && hi == null)
			return null;
		if (lo == null && hi != null)
			return new Filter(Restrictions.le(propertyName, hi));
		if (lo != null && hi == null)
			return new Filter(Restrictions.ge(propertyName, lo));
		return new Filter(Restrictions.between(propertyName, lo, hi));
	}

	/**
	 * Custom Filter to bypass property conversion alias matching etc...
	 * <p>
	 * Example: Filter.sqlRestriction("name like 'S%'")
	 * 
	 * @param sql
	 * @return
	 */
	public static Filter sqlRestriction(String sql) {
		return new Filter(Restrictions.sqlRestriction(sql));
	}

	public Criterion getCriterion() {
		return criterion;
	}

	private void setCriterion(Criterion criterion) {
		this.criterion = criterion;
	}

	@Override
	public String toString() {
		return "Filter [criterion=" + criterion + "]";
	}

}
