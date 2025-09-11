/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.utils;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class SVGUtils {

    private SVGUtils() {
    } // utility class → no instantiation

    public static FlatSVGIcon loadIcon(String path, int size, String colorKey) {
        FlatSVGIcon icon = new FlatSVGIcon(path, size, size);
        icon.setColorFilter(createColorFilter(colorKey));
        return icon;
    }

    public static FlatSVGIcon.ColorFilter createColorFilter(String uiKey) {
        return new FlatSVGIcon.ColorFilter(c -> UIManager.getColor(uiKey));
    }

    public static FlatSVGIcon loadCard(String resourcePath, int size,
            String name, String expiry, String number) {
        try (InputStream input = SVGUtils.class.getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }

            // Parse SVG
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(input);

            // ⚠ getElementById only works if we tell DOM which attr is an ID
            doc.getDocumentElement().setIdAttribute("id", true);

            // Update fields
            setText(doc, "nameField", name);
            setText(doc, "expireField", expiry);       // matches your SVG
            setText(doc, "cardNumberField", number);

            // Convert DOM → String
            StringWriter writer = new StringWriter();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String svgContent = writer.toString();

            // ✅ Pass as input stream, not data URI (more reliable)
            byte[] bytes = svgContent.getBytes(StandardCharsets.UTF_8);
            ByteArrayInputStream updatedSvgStream = new ByteArrayInputStream(bytes);

//            return new FlatSVGIcon(updatedSvgStream, size, size);
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void setText(Document doc, String id, String text) {
        Node node = doc.getElementById(id);
        if (node != null && node.getFirstChild() != null) {
            node.getFirstChild().setNodeValue(text);
        }
    }

}
