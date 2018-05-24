package com.excilys.formation.cdb.controllers.web;

import java.beans.PropertyEditorSupport;

import com.excilys.formation.cdb.dao.SortableCompanyColumn;

class SortableCompanyColumnEnumConverter extends PropertyEditorSupport {
    @Override
    public void setAsText(final String text) {
        setValue(SortableCompanyColumn.valueOf(text.trim().toUpperCase()));
    }
}