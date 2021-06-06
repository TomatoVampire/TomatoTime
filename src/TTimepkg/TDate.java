package TTimepkg;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class TDate extends TTime implements Serializable {
    @Override
    public int hashCode(){
        int code;
        code = time.get(GregorianCalendar.YEAR);
        code =  code*31+time.get(GregorianCalendar.MONTH);
        code =  code*31+time.get(GregorianCalendar.DAY_OF_MONTH);
        return code;
    }
}
