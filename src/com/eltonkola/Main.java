package com.eltonkola;
import java.io.File;
import java.util.ArrayList;

public class Main {

    private ArrayList<String> imgPixel = new ArrayList<>();

    private ArrayList<String> getPixels(){
        return imgPixel;
    }

    private void loadPixelImages(){
        listFolder();
        if(imgPixel.size() == 0){
            WWArtDownloader mArtDownloader = new WWArtDownloader();
            mArtDownloader.loadPics();
            listFolder();
        }
    }

    private void listFolder(){
        imgPixel.clear();

        File folder =  new File("art_images");
        if(folder.isDirectory() && folder.exists()){
            for (final File fileEntry : folder.listFiles()) {
                if (!fileEntry.isDirectory()) {
                    imgPixel.add(fileEntry.getAbsolutePath());
                }
            }
        }

    }

    public static void main(String[] args) {
	// write your code here

        Main mMain =  new Main();
        mMain.loadPixelImages();

        PixyPhoto mPixyPhoto = new PixyPhoto();

        //int[] alphas = {100, 110, 120, 150, 160, 170, 180, 190, 200, 210, 220, 230, 240};
        //int[] pixe_sizes = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        //int[] resolutions = {3840, 2560, 1920};

        int[] alphas = {200};
        int[] pixel_sizes = {20};
        int[] resolutions = {3840};

        for(int k = 0 ; k <resolutions.length; k ++) {
            for(int j = 0 ; j <pixel_sizes.length; j ++) {
                for (int i = 0; i < alphas.length; i++) {
                    boolean created = mPixyPhoto.createPixyPhoto(new File("input.jpg").getAbsolutePath(), mMain.getPixels(), resolutions[k], pixel_sizes[j], alphas[i], "final_" + resolutions[k] +"_" + pixel_sizes[j] + "_" + alphas[i] + ".png"); // alpha 0 -> 255
                    System.out.println("created:" + created);
                }
            }
        }

    }

}
