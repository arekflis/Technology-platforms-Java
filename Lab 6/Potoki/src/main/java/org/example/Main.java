package org.example;

import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static String input;

    private static String output;


    public static void main(String[] args) {

        for (int i =1; i<10; i++) {


            ExecutorService executorService = Executors.newFixedThreadPool(i);

            long time = System.currentTimeMillis();

            if (args.length != 2) {
                System.out.println("Too few arguments");
                System.exit(-1);
            }
            input = args[0];
            output = args[1];


            List<Path> files = null;
            Path source = Path.of(input);

            try {
                Future<List<Path>> futureFiles = executorService.submit(() -> {
                    try (Stream<Path> stream = Files.list(source)) {
                        return stream.collect(Collectors.toList());
                    }
                });
                files = futureFiles.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                System.exit(-1);
            }


            Stream<Pair<String, BufferedImage>> images = files.stream().map(path -> executorService.submit(() -> {
                try {
                    BufferedImage image = ImageIO.read(path.toFile());
                    String name = String.valueOf(path.getFileName());
                    //System.out.println("Thread " + Thread.currentThread().getName() + "create image " + name);
                    return Pair.of(name, image);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    return null;
                }
            })).map(future -> {
                try {
                    return future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return null;
                }
            });


            Stream<Pair<String, BufferedImage>> new_images = images.map(pair -> executorService.submit(() -> {
                BufferedImage newImage = transformImage(pair.getRight());
                //System.out.println("Thread " + Thread.currentThread().getName() + " transform image " + pair.getLeft());
                return Pair.of(pair.getLeft(), newImage);
            })).map(future -> {
                try {
                    //System.out.println("Thread " + Thread.currentThread().getName() + " get");
                    return future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return null;
                }
            });

            new_images.forEach(pair -> executorService.submit(() -> {
                try {
                    File out = new File(output + File.separator + pair.getLeft());
                    if (pair.getLeft().endsWith(".jpg")) ImageIO.write(pair.getRight(), "jpg", out);
                    else ImageIO.write(pair.getRight(), "png", out);
                    //System.out.println(pair.getLeft() + " saved");
                    //System.out.println("Thread " + Thread.currentThread().getName() + "save image " + pair.getLeft());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }));

            System.out.println("Threads : " + i);
            System.out.println(System.currentTimeMillis() - time);

            executorService.shutdown();
        }

    }

    private static BufferedImage transformImage(BufferedImage image){
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for(int i=0; i<image.getWidth(); i++){
            for(int j=0; j< image.getHeight(); j++){
                int rgb = image.getRGB(i, j);
                Color color = new Color(rgb);
                int red = color.getRed();
                int blue = color.getBlue();
                int green = color.getGreen();
                Color outcolor = new Color(red, blue, green);
                int outRgb = outcolor.getRGB();
                newImage.setRGB(i, j, outRgb);
            }
        }
        return newImage;
    }

}