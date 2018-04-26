package com.excilys.formation.cdb.dao;

import javax.persistence.metamodel.SingularAttribute;

import com.excilys.formation.cdb.model.*;

public enum SortableComputerColumn {
    ID(Computer_.id), NAME(Computer_.name), INTRODUCED(Computer_.introduced), DISCONTINUED(Computer_.discontinued), COMPANY(Computer_.company);

    private SingularAttribute column;

    SortableComputerColumn(SingularAttribute column) {
        this.column = column;
    }

    public SingularAttribute getColumn() {
        return column;
    }
}
