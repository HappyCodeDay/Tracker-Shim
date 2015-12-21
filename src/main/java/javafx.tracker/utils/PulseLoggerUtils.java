package javafx.tracker.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import com.sun.javafx.logging.Logger;
import com.sun.javafx.logging.PulseLogger;

public class PulseLoggerUtils {

    //---------------------------------------------------------------------------------------------
    // CONSTANTS.
    //---------------------------------------------------------------------------------------------

    private static final String FIELD_LOGGING_ENABLED = "PULSE_LOGGING_ENABLED";
    private static final String FIELD_LOGGERS = "loggers";

    //---------------------------------------------------------------------------------------------
    // STATIC PRIVATE FIELDS.
    //---------------------------------------------------------------------------------------------

    private static Class<?> pulseLoggerClass = PulseLogger.class;

    //---------------------------------------------------------------------------------------------
    // STATIC METHODS.
    //---------------------------------------------------------------------------------------------

    public static void assignEnabled(boolean enabledFlag) {
        try {
            Field loggingEnabledField = pulseLoggerClass.getDeclaredField(FIELD_LOGGING_ENABLED);
            assignFinalStaticValue(loggingEnabledField, enabledFlag);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void assignLoggers(List<Logger> loggers) {
        Logger[] loggersArray = loggers.toArray(new Logger[loggers.size()]);
        try {
            Field loggersArrayField = pulseLoggerClass.getDeclaredField(FIELD_LOGGERS);
            assignFinalStaticValue(loggersArrayField, loggersArray);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

//    public static StackTraceElement findInvokerTrace(StackTraceElement[] stackTraceElements) {
//        int pulseLoggerIndex = indexOfStackTraceElementByClass(stackTraceElements, pulseLoggerClass);
//        return stackTraceElements[pulseLoggerIndex + 1];
//    }

    //---------------------------------------------------------------------------------------------
    // STATIC PRIVATE METHODS.
    //---------------------------------------------------------------------------------------------

//    private static int indexOfStackTraceElementByClass(StackTraceElement[] elementsArray,
//                                                       Class<?> classReference) {
//        List<StackTraceElement> elements = Lists.newArrayList(elementsArray);
//        return Iterables.indexOf(elements, (element) ->
//            Objects.equals(element.getClassName(), classReference.getCanonicalName())
//        );
//    }

    private static void assignFinalStaticValue(Field field,
                                               Object newValue)
                                        throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }

}
