package lt.viko.eif.mjurevicius.ndassignment1xmlserver.utility;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    // Define your custom date format
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Unmarshal (String to LocalDateTime)
    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        return LocalDateTime.parse(v, formatter);
    }

    // Marshal (LocalDateTime to String)
    @Override
    public String marshal(LocalDateTime v) throws Exception {
        return v.format(formatter);
    }
}