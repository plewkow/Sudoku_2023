package model;

import model.exception.WrongFieldValueException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {
    private static final Logger logger = LoggerFactory.getLogger(SudokuField.class);
    private transient ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());
    private int value;


    public SudokuField(int givenValue) {
        value = givenValue;
    }

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int givenValue) {
        if (givenValue > SudokuBoard.SIZE || givenValue < 0) {
            logger.error(resourceBundle.getString("WrongFieldValue"));
            throw new WrongFieldValueException(resourceBundle.getString("WrongFieldValue"));
        }
        value = givenValue;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("pole", value).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value)
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

        SudokuField that = (SudokuField) o;

        return new EqualsBuilder().append(value, that.value).isEquals();
    }

    @Override
    public int compareTo(SudokuField o) {
        if (o == null) {
            logger.error("Comparing to null object");
            throw new NullPointerException("Comparing to null object");
        }

        int thisValue = this.getFieldValue();
        int otherValue = o.getFieldValue();

        if (thisValue < otherValue) {
            return -1;
        } else if (thisValue > otherValue) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            logger.error("Cloning not supported", e);
            throw new CloneNotSupportedException("Cloning not supported");
        }
    }


}
