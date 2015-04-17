package test;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.junit.Test;

public class JodaBib {

    private String[] collectionOfPossibelFormats = { "yyyy-MM-dd HH:mm:ss", "HH:mm:ss",
        "yyyy-MM-dd", "hh:mm:ss:S", "MM/dd/yyyy",
        "MM/dd/yyyy HH:mm:ss", "HH:mm:ss",
        "yyyy-MM-dd HH:mm:ss", "dd-MM-yyyy","yyyy-MM-dd HH:mm:ss.S",
        "yyyy-MM-dd HH:mm:ss.S"};

    @Test
    public void test() {

            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .append(DateTimeFormat.forPattern("yyyy.mm.dd").getParser())
            .toFormatter()
            .withZone(DateTimeZone.UTC);

            DateTimeFormatter formatter2 = new DateTimeFormatterBuilder()
            .append(DateTimeFormat.forPattern("S").getParser())
            .toFormatter()
            .withZone(DateTimeZone.UTC);
            DateTime dateTest = formatter2.parseDateTime("1000");
            System.out.println(dateTest.getMillis()+"");
            
            
            DateTime date = formatter.parseDateTime("1970.01.01");
            System.out.println(date.getMillis()+"");
            System.out.println(date.toString(formatter));
            //To test before 1970
            DateTime testDate;
            long before = 0;
            long year = 1970;
            for (int i = 1; i < 100*365*24*60; i++) {
                //One Minute
                long minutes = (-1L)*60L*1000L*(long)i;
                testDate = new DateTime(minutes);
                assertTrue(before>testDate.getMillis());
                before = testDate.getMillis();
                if(i%(365*24*60)==0){
                    assertTrue(testDate.getYear() < year);
                    year = testDate.getYear();
                }
            }

            //To test after 1970
            for (int i = 1; i < 100*365*24*60; i++) {
                //One Minute
                long minutes = 60L*1000L*(long)i;
                testDate = new DateTime(minutes);
                assertTrue(before<testDate.getMillis());
                before = testDate.getMillis();
                if(i%(365*24*60)==0){
                    assertTrue(testDate.getYear() >= year);
                    year = testDate.getYear();
                }
            }




    }

}
