package com.example.qatar2022.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import java.io.IOException;
import java.nio.file.FileSystems;

public class MethodUtils {


    public MethodUtils() {
    }

    //qrCode in byeArray Format


    public static byte [] generateByteQRCode(String text, int width, int height, String path)
    {
            ByteArrayOutputStream outputStream = null;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
// nn lo so ma mi da null
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            //here we are going to create a new variable to keep the output stream outside
            outputStream = new ByteArrayOutputStream();
            //black and white color
            MatrixToImageConfig config = new MatrixToImageConfig( 0xFF000000 , 0xFFFFFFFF);

            //we are going to use write to stream
            MatrixToImageWriter.writeToStream(bitMatrix,"png", outputStream, config);
            return outputStream.toByteArray();



        } catch (Exception e) {
            return null ;
        }


    }


    //this methode is enough to create the qrcode image
    //we are going to use class and method form zinx library
    /*
    public static void generateImageQRCode(String text, int width, int height, String path)
    {
        // creating an object for QRcodeWriterClass
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
            //qr code has a methode who accept 4 arguments, this is why i will add text, width ,height, path
        //text is the content of the qrcode, wich qr code contain
        //second parametre is the barcodeformat and we will pass the QrCode
        //we have to handle the exception
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            //we have to write now QRcode to image
           //we use Matrix2 to image writer
            //second parametre is the image extension
            //the third parametre is the path, but direct String we can not use so we need to convert that path in path Object
            MatrixToImageWriter.writeToPath(bitMatrix,"PNG", FileSystems.getDefault().getPath(path));


        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

     */

}
