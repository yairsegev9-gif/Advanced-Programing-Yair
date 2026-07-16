package test;

import java.util.Date;

public class Message {

    public final byte[] data;
    public final String asText;
    public final double asDouble;
    public final Date date;


    public Message(String text) {
        this.asText = text;
        this.data = text.getBytes();


        double tempDouble;
        try {
            tempDouble = Double.parseDouble(text);
        } catch (NumberFormatException e) {

            tempDouble = Double.NaN;
        }
        this.asDouble = tempDouble;

        this.date = new Date();
    }


    public Message(double number) {
        this(String.valueOf(number));
    }


    public Message(byte[] bytes) {
        this(new String(bytes));
    }
}