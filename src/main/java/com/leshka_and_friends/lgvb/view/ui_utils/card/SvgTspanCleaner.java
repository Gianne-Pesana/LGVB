package com.leshka_and_friends.lgvb.view.ui_utils.card;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class SvgTspanCleaner {

    private static final List<String> DYNAMIC_FIELDS = Arrays.asList(
            "card_holder_field", "c_type", "card_number_field", "exp_field"
    );

    public static void removeTspansAndMoveXY(String resourcePath, File outputSvg) throws Exception {
        // Load SVG from resources
        InputStream svgStream = SvgTspanCleaner.class.getResourceAsStream(resourcePath);
        if (svgStream == null) {
            throw new FileNotFoundException("Resource not found: " + resourcePath);
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(svgStream);

        for (String fieldId : DYNAMIC_FIELDS) {
            NodeList texts = doc.getElementsByTagName("text");
            for (int i = 0; i < texts.getLength(); i++) {
                Element text = (Element) texts.item(i);
                if (fieldId.equals(text.getAttribute("id"))) {
                    NodeList tspans = text.getElementsByTagName("tspan");
                    if (tspans.getLength() > 0) {
                        Element tspan = (Element) tspans.item(0);

                        // Move x and y to <text> if they exist
                        if (tspan.hasAttribute("x")) {
                            text.setAttribute("x", tspan.getAttribute("x"));
                        }
                        if (tspan.hasAttribute("y")) {
                            text.setAttribute("y", tspan.getAttribute("y"));
                        }

                        // Replace <text> content with the <tspan> content
                        text.setTextContent(tspan.getTextContent());

                        // ✅ Do NOT call text.removeChild(tspan)
                    }
                }
            }
        }

        // Save the cleaned SVG to the specified output file
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(outputSvg));

        System.out.println("SVG processed and saved to " + outputSvg.getAbsolutePath());
    }

}