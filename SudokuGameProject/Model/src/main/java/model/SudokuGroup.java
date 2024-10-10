package model;

import model.exception.WrongGroupSizeException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public abstract class SudokuGroup implements Cloneable {
    static final Logger logger = LoggerFactory.getLogger(SudokuGroup.class);
    transient ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());
    protected List<SudokuField> fields;

    public SudokuGroup(List<SudokuField> fields) {
        if (fields.size() == SudokuBoard.SIZE) {
            this.fields = fields;
        } else {
            logger.error(resourceBundle.getString("WrongGroupSize"));
            throw new WrongGroupSizeException("WrongGroupSize");
        }
    }

    public List<SudokuField> getSudokuFieldList() {
        return Collections.unmodifiableList(fields);
    }

    public boolean verify() {
        Set<Integer> seenValues = new HashSet<>();

        for (SudokuField field : fields) {
            int value = field.getFieldValue();

            if (value != 0 && !seenValues.add(value)) {

                return false;

            }
        }

        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("grupa", fields)
                .toString();
    }
}