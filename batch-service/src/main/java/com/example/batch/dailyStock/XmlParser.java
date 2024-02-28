package com.example.batch.dailyStock;

import com.example.batch.model.DailyStock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class XmlParser {

    public List<DailyStock> parseXml(String xmlResponse, String ticker) {
        List<DailyStock> dailyStockList = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            InputStream inputStream = new ByteArrayInputStream(xmlResponse.getBytes("EUC-KR"));

            Document document = builder.parse(inputStream);

            NodeList itemNodes = document.getElementsByTagName("item");

            for (int i = 0; i < itemNodes.getLength(); i++) {
                Element itemElement = (Element) itemNodes.item(i);

                String dataAttribute = itemElement.getAttribute("data");

                String[] values = dataAttribute.split("\\|");

                LocalDate date = convertStringToLocalDate(values[0]);
                long open = Long.parseLong(values[1]);
                long high = Long.parseLong(values[2]);
                long low = Long.parseLong(values[3]);
                long close = Long.parseLong(values[4]);
                long volume = Long.parseLong(values[5]);

                DailyStock dailyStock = new DailyStock(ticker, open, close, high, low, volume, date);
                dailyStockList.add(dailyStock);
            }

        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return dailyStockList;
    }

    private LocalDate convertStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }

    private String readInputStream(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString(StandardCharsets.UTF_8.name());
        }
    }
}
