package com.eltonkola;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class PixyPhoto {

    public PixyPhoto(){

    }

    public boolean createPixyPhoto(final String parentImage, final ArrayList<String> pixels, final int finalDim, final int pixelDim, final int alfaVal , final String outputFile){

        if(parentImage == null || pixels ==null|| pixels.size() == 0){
            return false;
        }
        ArrayList<BufferedImage> inputImages = new ArrayList<>();
        BufferedImage  originalImage = getBufferedImageFromFile(parentImage);
        for(String s: pixels){
            BufferedImage img = getBufferedImageFromFile(s);
            if(img != null) inputImages.add(img);
        }
        if(originalImage == null || inputImages ==null|| inputImages.size() == 0){
            return false;
        }

        BufferedImage created = renderPixy(finalDim, pixelDim, alfaVal, inputImages, originalImage);
        if(created !=null){
            try{
                File outputfile = new File(outputFile);
                ImageIO.write(created, "png", outputfile);
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return false;
    }


    private BufferedImage renderPixy(final int finalDim, final int pixelDim, final int alfaVal , final ArrayList<BufferedImage> inputPics, BufferedImage imputImg){
        log(">>>>>>finalDim:" + finalDim);
        log(">>>>>>pixelDim:" + pixelDim);
        log(">>>>>>alfaVal:" + alfaVal);
        //resize input
        int new_x=finalDim/pixelDim ;

        BufferedImage workingInput = getResizedBitmap(imputImg, new_x );

        log(">>>>>>workingInput x:" + workingInput.getWidth());
        log(">>>>>>workingInput y:" + workingInput.getHeight());


        //kopjo tere imazhet
        ArrayList<BufferedImage> workingPics = new ArrayList<BufferedImage>();
        for(BufferedImage bit: inputPics){
            workingPics.add(getResizedBitmap(bit, pixelDim,pixelDim));
        }

        int gjeresiaMadhe=workingInput.getWidth()*pixelDim;
        int lartesiaMadhe=workingInput.getHeight()*pixelDim;
        log(">>>>>>gjeresiaMadhe:" + gjeresiaMadhe);
        log(">>>>>>lartesiaMadhe:" + lartesiaMadhe);


        BufferedImage res = new BufferedImage(gjeresiaMadhe, lartesiaMadhe, BufferedImage.TYPE_INT_ARGB);

//        Canvas vizato = new Canvas(res);
//        Paint pixelPaint= new Paint();
        Random rand = new Random();

        BufferedImage bg = getResizedBitmap(workingInput,lartesiaMadhe, gjeresiaMadhe);

        Graphics g = res.getGraphics();
//        g.drawImage(bg, 0, 0, null);

        for(int i=0;i<workingInput.getWidth();i++){
            for(int j=0;j<workingInput.getHeight();j++){
                log("render pixel i:" + i + " j:" + j);
                final int pixelNgjyra = workingInput.getRGB(i,j);
                final int pos = rand.nextInt(workingPics.size());
                BufferedImage pixx = deepCopy(workingPics.get(pos));
                g.drawImage(getPreparedPixel(pixx,pixelNgjyra, alfaVal), i * pixelDim, j * pixelDim, null);
            }
        }

        return res;
    }

    private Image getPreparedPixel(BufferedImage im ,final int pixelNgjyra, int alfaVal){
        log("getResizedBitmap alfaVal:" + alfaVal);
        Graphics g = im.getGraphics();
        Color c = new Color(pixelNgjyra);
        g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), alfaVal));
        g.fillRect(0, 0, im.getWidth(), im.getHeight());
        g.dispose();

        return im;

    }

    private BufferedImage getResizedBitmap(BufferedImage bm, int newHeight, int newWidth) {
        log("getResizedBitmap newHeight:" + newHeight + " - newWidth:" + newWidth);

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        //TODO - something is wrong here
        if((int) scaleWidth == 0){
            scaleWidth = newWidth;
        }

        if((int) scaleHeight == 0){
            scaleHeight = newHeight;
        }

        log("getResizedBitmap width:" + width +" - height:" + height +" - scaleWidth:" + scaleWidth  +" - scaleHeight:" + scaleHeight);

        Image resized =  bm.getScaledInstance((int)scaleWidth, (int)scaleHeight, Image.SCALE_SMOOTH);

        BufferedImage buffered = new BufferedImage((int)scaleWidth, (int)scaleHeight, Image.SCALE_REPLICATE);

        buffered.getGraphics().drawImage(resized, 0, 0 , null);

        return buffered;
    }

    private BufferedImage getResizedBitmap(BufferedImage bm, int newWidth) {
        log("resize bm:" + bm + " to:" + newWidth);
        int width = bm.getWidth();
        int height = bm.getHeight();

        log("newWidth:" + newWidth+"/("+width+"/"+height+")");

        double pc = (width*1.0)/height;
        log("pc:" + pc);
        int newHeight = (int)(newWidth/pc);

        return getResizedBitmap(bm,newHeight, newWidth);
    }

    private void log(String toLog){
        System.out.print("PixyPhoto_" + toLog + " \n");
    }

    private BufferedImage getBufferedImageFromFile(final String fileName){
        log("opne fimage file:" + fileName);
        try {
            return ImageIO.read(new File(fileName));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

}
