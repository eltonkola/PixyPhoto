# PixyPhoto

This class will create a pixel version of an image, using other images as pixels.

By running the Main class, the art_images folder will be populated with tp artists and songs from spotify (using WWArtDownloader utility class), after that PixyPhoto will be used to generate the output

# Example
![input](input.jpg?raw=true, "Input")

![output](final_3840_20_200.png?raw=true, "Output")


# How to use it

    PixyPhoto mPixyPhoto = new PixyPhoto();
    boolean created = mPixyPhoto.createPixyPhoto(new File("input.jpg").getAbsolutePath(), listOfImagesToUseAsPixels, resolutions, pixel_sizes, alpha, "output_file_name.png");

License
--------

    Copyright 2013 Elton Kola.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.