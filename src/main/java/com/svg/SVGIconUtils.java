package com.svg;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SVGIconUtils {

    public static void setButtonSVGGraphic(Button button, String svgResourcePath, float width, float height) {
        try {
            // Cargar el archivo SVG desde el classpath
            InputStream svgInputStream = SVGIconUtils.class.getResourceAsStream(svgResourcePath);
            if (svgInputStream == null) {
                throw new FileNotFoundException("No se encontró el archivo SVG en la ruta: " + svgResourcePath);
            }

            // Convertir el archivo SVG en una imagen para el botón
            Image image = convertSVGToImage(svgInputStream, width, height);  // Ajusta el tamaño aquí
            if (image != null) {
                button.setGraphic(new ImageView(image));
                button.setText(null);
                //button.setStyle("-fx-background-color: transparent;");
                // Cambiar el color de fondo del botón cuando el cursor está sobre él
                /*button.styleProperty().bind(
                        Bindings.when(button.hoverProperty())
                                .then("-fx-background-color: #d0d3d4;") //ffcccc Color cuando el cursor está sobre el botón
                                .otherwise("-fx-background-color: transparent;")//ffffff // Color cuando el cursor no está sobre el botón
                );*/

            } else {
                System.err.println("No se pudo cargar el gráfico SVG para el botón.");
            }
        } catch (Exception e) {
            System.err.println("Error al establecer gráfico SVG en botón: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para establecer una imagen SVG en un ImageView
    public static void setImageViewSVGGraphic(ImageView imageView, String svgResourcePath, float width, float height) {
        try {
            // Cargar el archivo SVG desde el classpath
            InputStream svgInputStream = SVGIconUtils.class.getResourceAsStream(svgResourcePath);
            if (svgInputStream == null) {
                throw new FileNotFoundException("No se encontró el archivo SVG en la ruta: " + svgResourcePath);
            }

            // Convertir el archivo SVG en una imagen para el ImageView
            Image image = convertSVGToImage(svgInputStream, width, height);  // <<-Ajusta el tamaño
            if (image != null) {
                imageView.setImage(image);
                imageView.setFitWidth(width); // Ajustar el ancho del ImageView
                imageView.setFitHeight(height); // Ajustar la altura del ImageView
                imageView.setPreserveRatio(true); // Mantener la relación de aspecto

                // Crear un rectángulo con esquinas redondeadas para usar como Clip
                Rectangle clip = new Rectangle(width, height);
                clip.setArcWidth(10);
                clip.setArcHeight(10);
                imageView.setClip(clip);

            } else {
                System.err.println("No se pudo cargar el gráfico SVG para el ImageView.");
            }
        } catch (Exception e) {
            System.err.println("Error al establecer gráfico SVG en ImageView: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Image convertSVGToImage(InputStream svgInputStream, float width, float height) {
        try {
            // Configurar el transcodificador de SVG a PNG
            PNGTranscoder transcoder = new PNGTranscoder();
            transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, width);
            transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, height);

            // Leer el SVG y transcodificarlo a un ByteArrayOutputStream
            TranscoderInput input = new TranscoderInput(svgInputStream);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outputStream);
            transcoder.transcode(input, output);

            // Convertir el PNG resultante en un Image de JavaFX
            ByteArrayInputStream bis = new ByteArrayInputStream(outputStream.toByteArray());
            return new Image(bis);
        } catch (Exception e) {
            System.err.println("Error al convertir SVG a Image: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
