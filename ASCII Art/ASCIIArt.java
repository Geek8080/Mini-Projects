import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import javax.imageio.ImageIO;

public class ASCIIArt {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("*************************************");
        System.out.println("*       Select an Option:           *");
        System.out.println("*                                   *");
        System.out.println("*          [1]. Artify Image        *");
        System.out.println("*          [2]. Artify text         *");
        System.out.println("*                                   *");
        System.out.println("*************************************");

        int i = Integer.parseInt(reader.readLine().trim());

        if (i == 1) {
            System.out.print("Enter File Name including the Extension: ");
            String name = reader.readLine().trim();
            System.out.print("Do you want high-accuracy output(y/n): ");
            boolean hd = false;
            hd = reader.readLine().trim().equalsIgnoreCase("y");
            asciiArtifyImage(name, hd);
        }

        if (i == 2) {
            System.out.print("Enter Text to be artified: ");
            asciiArtifyText(reader.readLine().trim());
        }
    }

    public static void asciiArtifyText(String text) throws FileNotFoundException {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 24);

        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        g2d.dispose();

        image = new BufferedImage(width + ((width) / text.length()), height, BufferedImage.TYPE_INT_ARGB);

        g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, 0, fm.getAscent());
        g2d.dispose();

        writeBWArt(image);
    }

    public static void asciiArtifyImage(String fileName, boolean hd) throws Exception {
        File im = new File(fileName);
        if (hd) {
            ImageResize.resImage(im, "image.jpg", 350, 350);
        } else {
            ImageResize.resImage(im, "image.jpg", 100, 100);
        }
        BufferedImage image = ImageIO.read(new File("image.jpg"));
        writeArt(image);
    }

    private static void writeArt(BufferedImage image) throws FileNotFoundException {
        int arr[][] = convertToArray(image);
        int width = (arr.length);
        int length = (arr[0].length);

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                int val = arr[i][j];
                if (val >= 14913088) {
                    sb.append("@@");
                } else if (val >= 13048952) {
                    sb.append("##");
                } else if (val >= 11184816) {
                    sb.append("$$");
                } else if (val >= 9320680) {
                    sb.append("&&");
                } else if (val >= 7456541) {
                    sb.append("**");
                } else if (val >= 5592406) {
                    sb.append("||");
                } else if (val >= 3728271) {
                    sb.append("??");
                } else if (val >= 1864136) {
                    sb.append("==");
                } else {
                    sb.append("  ");
                }
            }
            sb.append("\n");
        }

        System.setOut(new PrintStream(new File("image.txt")));
        System.out.println(sb.toString());
    }

    private static void writeReversedArt(BufferedImage image) throws FileNotFoundException {
        int arr[][] = convertToArray(image);
        int width = (arr.length);
        int length = (arr[0].length);

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                int val = arr[i][j];
                if (val >= 14913088) {
                    sb.append("  ");
                } else if (val >= 13048952) {
                    sb.append("==");
                } else if (val >= 11184816) {
                    sb.append("??");
                } else if (val >= 9320680) {
                    sb.append("||");
                } else if (val >= 7456541) {
                    sb.append("**");
                } else if (val >= 5592406) {
                    sb.append("&&");
                } else if (val >= 3728271) {
                    sb.append("$$");
                } else if (val >= 1864136) {
                    sb.append("##");
                } else {
                    sb.append("@@");
                }
            }
            sb.append("\n");
        }

        System.setOut(new PrintStream(new File("image.txt")));
        System.out.println(sb.toString());
    }

    private static void writeBWArt(BufferedImage image) throws FileNotFoundException {
        int arr[][] = convertToArray(image);
        int width = (arr.length);
        int length = (arr[0].length);

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                int val = arr[i][j];
                if (val < 9320676) {
                    sb.append("  ");
                } else {
                    sb.append("##");
                }
            }
            sb.append("\n");
        }

        System.setOut(new PrintStream(new File("image.txt")));
        System.out.println(sb.toString());
    }

    private static void writeReversedBWArt(BufferedImage image) throws FileNotFoundException {
        int arr[][] = convertToArray(image);
        int width = (arr.length);
        int length = (arr[0].length);

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                int val = arr[i][j];
                if (val > 9320676) {
                    sb.append("  ");
                } else {
                    sb.append("##");
                }
            }
            sb.append("\n");
        }

        System.setOut(new PrintStream(new File("image.txt")));
        System.out.println(sb.toString());
    }

    private static int[][] convertToArray(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] result = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[row][col] = image.getRGB(col, row) * (-1);
            }
        }

        return result;
    }
}