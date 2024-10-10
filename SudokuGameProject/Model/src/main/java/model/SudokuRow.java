package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

public class SudokuRow extends SudokuGroup {

    public SudokuRow(List<SudokuField> fields) {

        super(fields);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 19)
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

        SudokuRow that = (SudokuRow) o;

        return new EqualsBuilder().append(fields, that.fields).isEquals();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        List<SudokuField> fields = new ArrayList<>(getSudokuFieldList());
        return new SudokuRow(fields);
    }
}

