module ModelProject {
    requires org.apache.commons.lang3;
    requires java.desktop;
    requires org.slf4j;
    requires java.sql;
    requires java.logging;

    exports model;
    exports model.exception;
}