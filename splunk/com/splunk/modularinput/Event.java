package com.splunk.modularinput;

import sun.util.calendar.Gregorian;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: fross
 * Date: 6/25/13
 * Time: 10:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class Event {
    protected Date time = null;
    protected String data;
    protected String source = null;
    protected String sourceType = null;
    protected String index = null;
    protected String host = null;
    protected boolean done = true;
    protected boolean unbroken = true;
    protected String stanza = null;

    public Event() {}

    protected void writeFieldOn(XMLStreamWriter out, String name, String value) throws XMLStreamException {
        if (value != null) {
            out.writeStartElement(name);
            out.writeCharacters(value);
            out.writeEndElement();
        }
    }

    public void writeOn(XMLStreamWriter out) throws XMLStreamException, MalformedDataException {
        if (data == null) {
            throw new MalformedDataException("Events must have at least the data field set to be written to XML.");
        }

        out.writeStartElement("event");
        if (getStanza() != null) {
            out.writeAttribute("stanza", getStanza());
        }
        out.writeAttribute("unbroken", isUnbroken() ? "1" : "0");

        long epoch_time = time != null ? time.getTime() : System.currentTimeMillis();

        writeFieldOn(out, "time", Long.toString(epoch_time/1000));
        writeFieldOn(out, "source", getSource());
        writeFieldOn(out, "sourceType", getSourceType());
        writeFieldOn(out, "index", getIndex());
        writeFieldOn(out, "host", getHost());
        writeFieldOn(out, "data", getData());

        if (!isUnbroken() && isDone()) {
            out.writeStartElement("done");
            out.writeEndElement();
        }

        out.writeEndElement();
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getIndex() {
        return this.index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return this.done;
    }

    public void setUnbroken(boolean unbroken) {
        this.unbroken = unbroken;
    }

    public boolean isUnbroken() {
        return this.unbroken;
    }

    public String getStanza() {
        return this.stanza;
    }

    public void setStanza(String stanza) {
        this.stanza = stanza;
    }


}
