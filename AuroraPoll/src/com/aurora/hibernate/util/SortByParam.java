package com.aurora.hibernate.util;

public class SortByParam {
    private String sortPropertyName;
    private String order;
    public static final String SORT_ASC = "asc";
    public static final String SORT_DESC = "desc";

    /**
     * Convenience constructor
     *
     * @param property
     *            the property to sort by in the query
     * @param ascend
     *            should we sort ascending? If false, sorting will desecend.
     */
    public SortByParam(String property, boolean ascend) {
        this.sortPropertyName = property;
        this.order = ascend ? SORT_ASC : SORT_DESC;
    }

    /**
     * Standard get method.
     * @return Returns the sortPropertyName.
     */
    public String getSortPropertyName() {
        return sortPropertyName;
    }

    /**
     * Standard set method.
     * @param sortPropertyName The sortPropertyName to set.
     */
    public void setSortPropertyName(String sortPropertyName) {
        this.sortPropertyName = sortPropertyName;
    }

    /**
     * Standard get method.
     * @return Returns the order.
     */
    public String getOrder() {
        return order;
    }

    /**
     * Standard set method.
     * @param order The order to set.
     */
    public void setOrder(String order) {
        this.order = order;
    }
}
