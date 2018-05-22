package com.excilys.formation.cdb.dao;

import javax.persistence.metamodel.SingularAttribute;

import com.excilys.formation.cdb.model.Company_;

public enum SortableCompanyColumn {
    ID(Company_.ca_id), NAME(Company_.ca_name);

    private SingularAttribute column;

    SortableCompanyColumn(SingularAttribute column) {
        this.column = column;
    }

    public SingularAttribute getColumn() {
        return column;
    }
}
