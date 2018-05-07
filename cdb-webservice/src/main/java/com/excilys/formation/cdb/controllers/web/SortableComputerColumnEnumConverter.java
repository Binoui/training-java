package com.excilys.formation.cdb.controllers.web;

import java.beans.PropertyEditorSupport;

import com.excilys.formation.cdb.dao.SortableComputerColumn;

class SortableComputerColumnEnumConverter extends PropertyEditorSupport {
    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        setValue(SortableComputerColumn.valueOf(text.trim().toUpperCase()));
    }
}