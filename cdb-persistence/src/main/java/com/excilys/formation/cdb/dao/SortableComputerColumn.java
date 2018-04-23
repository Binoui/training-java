package com.excilys.formation.cdb.dao;

public enum SortableComputerColumn {
    ID("cu_id"), NAME("cu_name"), INTRODUCED("cu_introduced"), DISCONTINUED("cu_discontinued"), COMPANY("ca_name");

    private String column;

    SortableComputerColumn(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
