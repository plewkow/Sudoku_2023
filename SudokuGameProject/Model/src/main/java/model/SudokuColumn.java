package model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

public class SudokuColumn extends SudokuGroup {

    public SudokuColumn(List<SudokuField> fields) {
        super(fields);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 21)
                .append(fields)
                .toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuColumn that = (SudokuColumn) o;

        return new EqualsBuilder().append(fields, that.fields).isEquals();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        List<SudokuField> fields = new ArrayList<>(getSudokuFieldList());
        return new SudokuColumn(fields);
    }
}