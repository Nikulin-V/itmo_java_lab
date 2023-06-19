package classes.movie;

import exceptions.*;

public class FieldHandler {
    protected Object value;
    protected FieldProperty[] properties;

    public FieldHandler(Object value, FieldProperty... properties) {
        this.value = value;
        this.properties = properties;
    }

    public Object handleObject() throws NullValueException {
        for (FieldProperty property : properties) {
            if (property == FieldProperty.NOT_NULL && value == null)
                throw new NullValueException();
        }
        return value;
    }

    public String handleString() throws NullValueException, BlankValueException, BadValueLengthException, NotUniqueException {
        for (FieldProperty property : properties) {
            if (property == FieldProperty.NOT_NULL && value == null)
                throw new NullValueException();
            if (value != null && property == FieldProperty.NOT_BLANK && ((String) value).isBlank())
                throw new BlankValueException();
            if (value != null && property == FieldProperty.LENGTH && ((int) property.getArgs()[0]) > ((String) value).length())
                throw new BadValueLengthException((Integer) property.getArgs()[0]);
        }
        return (String) value;
    }

    public Long handleLong() throws NullValueException, NotGreatThanException, GreatThanException {
        for (FieldProperty property : properties) {
            if (property == FieldProperty.NOT_NULL && value == null)
                throw new NullValueException();
            if (value != null && property == FieldProperty.GREAT_THAN_ZERO && (long) value <= 0)
                throw new NotGreatThanException(0);
            if (value != null && property == FieldProperty.MAX_VALUE && (long) value > FieldProperty.MAX_VALUE.getArgs()[0].longValue())
                throw new GreatThanException(FieldProperty.MAX_VALUE.getArgs()[0].longValue());
        }
        return (Long) value;
    }

    public Integer handleInt() throws NullValueException, NotGreatThanException, GreatThanException {
        for (FieldProperty property : properties) {
            if (property == FieldProperty.NOT_NULL && value == null)
                throw new NullValueException();
            if (value != null && property == FieldProperty.GREAT_THAN_ZERO && (int) value <= (int) FieldProperty.GREAT_THAN_ZERO.getArgs()[0])
                throw new NotGreatThanException(0);
            if (value != null && property == FieldProperty.GREAT_THAN_X && (int) value <= (int) FieldProperty.GREAT_THAN_X.getArgs()[0])
                throw new NotGreatThanException(FieldProperty.GREAT_THAN_X.getArgs()[0].longValue());
            if (value != null && property == FieldProperty.MAX_VALUE && (int) value > FieldProperty.MAX_VALUE.getArgs()[0].intValue())
                throw new GreatThanException(FieldProperty.MAX_VALUE.getArgs()[0].longValue());
        }
        return (Integer) value;
    }

    public Float handleFloat() throws NullValueException, NotGreatThanException, GreatThanException {
        for (FieldProperty property : properties) {
            if (property == FieldProperty.NOT_NULL && value == null)
                throw new NullValueException();
            if (value != null && (property == FieldProperty.GREAT_THAN_ZERO && (Float) value <= FieldProperty.GREAT_THAN_ZERO.getArgs()[0].floatValue()))
                throw new NotGreatThanException(0);
            if (value != null && property == FieldProperty.GREAT_THAN_X && (Float) value <= FieldProperty.GREAT_THAN_X.getArgs()[0].floatValue())
                throw new NotGreatThanException((Long) FieldProperty.GREAT_THAN_X.getArgs()[0]);
            if (value != null && property == FieldProperty.MAX_VALUE && (float) value > FieldProperty.MAX_VALUE.getArgs()[0].floatValue())
                throw new GreatThanException(FieldProperty.MAX_VALUE.getArgs()[0].longValue());
        }
        return (Float) value;
    }

    public Double handleDouble() throws NullValueException, NotGreatThanException, GreatThanException {
        for (FieldProperty property : properties) {
            if (property == FieldProperty.NOT_NULL && value == null)
                throw new NullValueException();
            if (value != null && property == FieldProperty.GREAT_THAN_ZERO && (Double) value <= FieldProperty.GREAT_THAN_ZERO.getArgs()[0].doubleValue())
                throw new NotGreatThanException(0);
            if (value != null && property == FieldProperty.GREAT_THAN_X && (Double) value <= FieldProperty.GREAT_THAN_X.getArgs()[0].doubleValue())
                throw new NotGreatThanException((Long) FieldProperty.GREAT_THAN_X.getArgs()[0]);
            if (value != null && property == FieldProperty.MAX_VALUE && (double) value > FieldProperty.MAX_VALUE.getArgs()[0].doubleValue())
                throw new GreatThanException(FieldProperty.MAX_VALUE.getArgs()[0].longValue());
        }
        return (Double) value;
    }
}

